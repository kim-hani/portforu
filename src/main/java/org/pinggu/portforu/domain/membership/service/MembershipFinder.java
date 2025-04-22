package org.pinggu.portforu.domain.membership.service;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.domain.membership.entity.Membership;
import org.pinggu.portforu.domain.membership.repository.MembershipRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MembershipFinder {

    private final MembershipRepository membershipRepository;

    public Membership findById(Long id) {
        return membershipRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "멤버십이 존재하지 않습니다."));
    }

}
