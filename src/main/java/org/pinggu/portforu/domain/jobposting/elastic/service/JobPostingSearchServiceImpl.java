package org.pinggu.portforu.domain.jobposting.elastic.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
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

            SearchResponse<JobPostingDocument> response = elasticsearchClient.search(s -> s
                    .index(INDEX_NAME)
                    .from(from)
                    .size(size)
                    .query(q -> q
                            .bool(b -> b
                                    .should(s1 -> s1
                                            .multiMatch(m -> m
                                                    .query(keyword)
                                                    .fields("title", "company", "location", "salary", "duty",
                                                            "employmentType", "experienceYears", "keyAbilities")
                                                    .type(TextQueryType.PhrasePrefix)
                                            )
                                    )
                                    .should(s2 -> s2
                                            .term(t -> t
                                                    .field("skills")
                                                    .value(keyword)
                                            )
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