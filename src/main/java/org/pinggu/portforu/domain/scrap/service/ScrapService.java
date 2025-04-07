package org.pinggu.portforu.domain.scrap.service;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.domain.Pagecond;
import org.pinggu.portforu.common.dto.AuthMember;
import org.pinggu.portforu.domain.jobposting.entity.JobPosting;
import org.pinggu.portforu.domain.jobposting.service.JobPostingService;
import org.pinggu.portforu.domain.scrap.dto.response.ScrapDetailResponseDto;
import org.pinggu.portforu.domain.scrap.dto.response.ScrapResponseDto;
import org.pinggu.portforu.domain.scrap.entity.Scrap;
import org.pinggu.portforu.domain.scrap.repository.ScrapRepository;
import org.pinggu.portforu.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScrapService {

    private final JobPostingService jobPostingService;
    private final ScrapRepository scrapRepository;

    @Transactional
    public ScrapResponseDto toggleScrap(AuthMember authMember, Long jobPostingId) {
        Member member = Member.fromAuthMember(authMember);
        JobPosting jobPosting = jobPostingService.findJobPostingById(jobPostingId);

        Scrap scrap = scrapRepository.findByMemberAndJobPosting(member, jobPosting)
                .map(existingScrap -> {
                    if (existingScrap.getDeletedAt() == null) {
                        existingScrap.delete();
                    } else {
                        existingScrap.restore();
                    }
                    return existingScrap;
                })
                .orElseGet(() -> {
                    Scrap newScrap = new Scrap(member, jobPosting);
                    scrapRepository.save(newScrap);
                    return newScrap;
                });

        return ScrapResponseDto.from(scrap);
    }

    @Transactional(readOnly = true)
    public Page<ScrapDetailResponseDto> findScraps(AuthMember authMember, Pagecond pagecond) {
        Member member = Member.fromAuthMember(authMember);

        Pageable pageable = PageRequest.of(pagecond.getPageNum() - 1, pagecond.getPageSize(), Sort.by(Sort.Order.desc("updatedAt")));
        Page<Scrap> scraps = scrapRepository.findAllByMemberIdAndDeletedAtIsNull(member.getId(), pageable);

        return scraps.map(ScrapDetailResponseDto::from);
    }

}
