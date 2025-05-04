package org.pinggu.portforu.emailing.service;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.scrap.entity.Scrap;
import org.pinggu.portforu.domain.scrap.repository.ScrapRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobCloseNotificationService {

    private final ScrapRepository scrapRepository;
    private final MailService mailService;

    @Transactional
    public void notifyClosingSoon(Long jobPostingId, String jobTitle) {
        List<Scrap> scraps = scrapRepository.findMemberIdsByJobPostingId(jobPostingId);

        for (Scrap scrap : scraps) {
            Member member = scrap.getMember();
            String link = scrap.getJobPosting().getLink();

            mailService.sendClosingSoonNotification(member, jobTitle, link);
        }
    }
}
