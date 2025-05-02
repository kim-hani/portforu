package org.pinggu.portforu.domain.jobposting.elastic.service;

import org.pinggu.portforu.domain.jobposting.elastic.document.JobPostingDocument;
import org.pinggu.portforu.domain.jobposting.entity.JobPosting;

import java.util.List;

public interface JobPostingSearchService {
    void index(JobPosting jobPosting);
    void deleteById(String id);
    List<JobPostingDocument> search(String keyword);
}
