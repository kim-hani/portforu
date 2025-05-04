package org.pinggu.portforu.emailing.service;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.member.service.MemberFinder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnsubscribeService {

    private final MemberFinder memberFinder;

    public void unsubscribe(String token) {
        Member member = memberFinder.findMemberByUnsubscribeToken(token);
        member.updateEmailSubscription(false);
    }
}
