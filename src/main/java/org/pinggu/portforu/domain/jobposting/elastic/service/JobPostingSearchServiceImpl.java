package org.pinggu.portforu.domain.jobposting.elastic.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.indices.AnalyzeRequest;
import co.elastic.clients.elasticsearch.indices.AnalyzeResponse;
import co.elastic.clients.elasticsearch.indices.analyze.AnalyzeToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pinggu.portforu.domain.jobposting.elastic.document.JobPostingDocument;
import org.pinggu.portforu.domain.jobposting.entity.JobPosting;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobPostingSearchServiceImpl implements JobPostingSearchService {

    private final ElasticsearchClient elasticsearchClient;

    private static final String INDEX_NAME = "job_postings";

    @Override
    public void index(JobPosting jobPosting) {
        JobPostingDocument document = JobPostingDocument.from(jobPosting);
        try {
            elasticsearchClient.index(i -> i
                    .index(INDEX_NAME)
                    .id(String.valueOf(document.getId()))
                    .document(document)
            );
        } catch (IOException e) {
            log.error("Indexing failed", e);
        }
    }

    @Override
    public void deleteById(String id) {
        try {
            elasticsearchClient.delete(d -> d
                    .index(INDEX_NAME)
                    .id(String.valueOf(id))
            );
        } catch (IOException e) {
            log.error("Delete failed", e);
        }
    }

    @Override
    public List<JobPostingDocument> search(String keyword, int page, int size) {
        log.info("Elasticsearch 검색어: {}", keyword);

        try {
            int from = page * size;

            // 1. Analyze API로 키워드 분석 (Nori 사용 전제)
            AnalyzeRequest analyzeRequest = AnalyzeRequest.of(a -> a
                    .index(INDEX_NAME)
                    .analyzer("korean_custom")  // 인덱스 생성 시 적용된 analyzer 이름
                    .text(keyword)
            );

            AnalyzeResponse analyzeResponse = elasticsearchClient.indices().analyze(analyzeRequest);

            List<String> tokens = analyzeResponse.tokens().stream()
                    .map(AnalyzeToken::token)
                    .filter(token -> token.length() >= 2)  // 너무 짧은 건 제외
                    .distinct()                            // 중복 제거
                    .collect(Collectors.toList());

            if (tokens.isEmpty()) {
                log.warn("분석된 토큰 없음. 검색 중단: {}", keyword);
                return Collections.emptyList();
            }

            // 2. 추출된 키워드로 multi_match must 조건 구성
            SearchResponse<JobPostingDocument> response = elasticsearchClient.search(s -> s
                            .index(INDEX_NAME)
                            .from(from)
                            .size(size)
                            .query(q -> q
                                    .bool(b -> b
                                            .must(tokens.stream()
                                                    .map(token -> Query.of(mq -> mq
                                                            .multiMatch(m -> m
                                                                    .query(token)
                                                                    .fields("title", "company", "location", "salary", "duty",
                                                                            "employmentType", "experienceYears", "keyAbilities", "skills")
                                                                    .type(TextQueryType.PhrasePrefix)
                                                            )
                                                    ))
                                                    .collect(Collectors.toList())
                                            )
                                    )
                            )
                            .sort(sort -> sort
                                    .field(f -> f
                                            .field("id")
                                            .order(SortOrder.Desc)
                                    )
                            ),
                    JobPostingDocument.class
            );

            return response.hits().hits().stream()
                    .map(Hit::source)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            log.error("Search failed", e);
            return Collections.emptyList();
        }
    }
}