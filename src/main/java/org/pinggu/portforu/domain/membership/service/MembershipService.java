package org.pinggu.portforu.domain.membership.service;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.domain.membership.dto.request.MembershipSaveRequestDto;
import org.pinggu.portforu.domain.membership.dto.request.MembershipUpdateRequestDto;
import org.pinggu.portforu.domain.membership.dto.response.MembershipResponseDto;
import org.pinggu.portforu.domain.membership.entity.Membership;
import org.pinggu.portforu.domain.membership.repository.MembershipRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MembershipService {

    private final MembershipRepository membershipRepository;
    private final MembershipFinder membershipFinder;

    @Transactional
    public MembershipResponseDto saveMembership(MembershipSaveRequestDto requestDto) {
        Membership membership = Membership.builder()
                .name(requestDto.getName())
                .price(requestDto.getPrice())
                .quantity(requestDto.getQuantity())
                .year(requestDto.getYear())
                .build();

        Membership savedMembership = membershipRepository.save(membership);

        return MembershipResponseDto.from(savedMembership);
    }

    @Transactional(readOnly = true)
    public List<MembershipResponseDto> findAllMemberships() {
        return membershipRepository.findAllActiveMemberships().stream()
                .map(MembershipResponseDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public MembershipResponseDto findMembershipById(Long membershipId) {
        return MembershipResponseDto.from(membershipFinder.findById(membershipId));
    }

    @Transactional
    public void updateMembership(Long membershipId, MembershipUpdateRequestDto requestDto) {
        Membership membership = membershipFinder.findById(membershipId);

        membership.update(requestDto.getName(), requestDto.getPrice(), requestDto.getQuantity(), requestDto.getYear());
    }

    @Transactional
    public Long deleteMembership(Long membershipId) {
        Membership membership = membershipFinder.findById(membershipId);

        membershipRepository.delete(membership);

        return membership.getId();
    }

}
