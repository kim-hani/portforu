package org.pinggu.portforu.domain.jobposting.elastic.repository;

import org.pinggu.portforu.domain.jobposting.elastic.document.JobPostingDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface JobPostingSearchRepository extends ElasticsearchRepository<JobPostingDocument, String> {
    List<JobPostingDocument> findByTitleContaining(String keyword);
}

