package org.pinggu.portforu.domain.jobposting.elastic.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pinggu.portforu.domain.jobposting.elastic.document.JobPostingDocument;
import org.pinggu.portforu.domain.jobposting.elastic.repository.JobPostingSearchRepository;
import org.pinggu.portforu.domain.jobposting.entity.JobPosting;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobPostingSearchServiceImpl implements JobPostingSearchService {

    private final JobPostingSearchRepository repository;

    @Override
    public void index(JobPosting jobPosting) {
        JobPostingDocument document = JobPostingDocument.from(jobPosting);
        repository.save(document);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public List<JobPostingDocument> search(String keyword) {
        log.info("Elasticsearch 검색어: {}", keyword);
        return repository.findByTitleContaining(keyword);
    }
}
