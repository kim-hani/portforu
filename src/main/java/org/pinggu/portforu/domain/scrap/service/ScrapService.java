package org.pinggu.portforu.domain.scrap.service;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.domain.Pagecond;
import org.pinggu.portforu.common.dto.AuthMember;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.domain.jobposting.entity.JobPosting;
import org.pinggu.portforu.domain.jobposting.service.JobPostingFinder;
import org.pinggu.portforu.domain.scrap.dto.response.ScrapDetailResponseDto;
import org.pinggu.portforu.domain.scrap.dto.response.ScrapResponseDto;
import org.pinggu.portforu.domain.scrap.entity.Scrap;
import org.pinggu.portforu.domain.scrap.repository.ScrapRepository;
import org.pinggu.portforu.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScrapService {

    private final JobPostingFinder jobPostingFinder;
    private final ScrapRepository scrapRepository;

    @Transactional
    public ScrapResponseDto toggleScrap(AuthMember authMember, Long jobPostingId) {
        Member member = Member.fromAuthMember(authMember);
        JobPosting jobPosting = jobPostingFinder.findJobPostingById(jobPostingId);

        Scrap scrap = scrapRepository.findByMemberIdAndJobPostingId(member.getId(), jobPosting.getId())
                .map(existingScrap -> {
                    if (!existingScrap.getIsDeleted()) {
                        existingScrap.softDelete();
                    } else {
                        existingScrap.restore();
                    }
                    return existingScrap;
                })
                .orElseGet(() -> {
                    Scrap newScrap = Scrap.builder()
                            .member(member)
                            .jobPosting(jobPosting)
                            .build();

                    scrapRepository.save(newScrap);
                    return newScrap;
                });

        return ScrapResponseDto.from(scrap);
    }

    @Transactional(readOnly = true)
    public Page<ScrapDetailResponseDto> findAllScraps(AuthMember authMember, Long memberId, Pagecond pagecond) {
        if (!authMember.getId().equals(memberId)) {
            throw new CustomException(HttpStatus.FORBIDDEN, "다른 회원의 스크랩 목록에 접근할 수 없습니다.");
        }

        Pageable pageable = PageRequest.of(pagecond.getPageNum() - 1, pagecond.getPageSize(), Sort.by(Sort.Order.desc("updatedAt")));
        Page<Scrap> scraps = scrapRepository.findAllByMemberIdAndIsDeletedFalse(memberId, pageable);

        return scraps.map(ScrapDetailResponseDto::from);
    }

}
