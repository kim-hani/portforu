package org.pinggu.portforu.domain.membership.service;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.domain.Pagecond;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.domain.membership.dto.request.MembershipSaveRequestDto;
import org.pinggu.portforu.domain.membership.dto.request.MembershipUpdateRequestDto;
import org.pinggu.portforu.domain.membership.dto.response.MembershipResponseDto;
import org.pinggu.portforu.domain.membership.entity.Membership;
import org.pinggu.portforu.domain.membership.repository.MembershipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MembershipService {
    private static final Logger logger = LoggerFactory.getLogger(MembershipService.class);

    private final MembershipRepository membershipRepository;

    @Transactional
    public MembershipResponseDto saveMembership(
            MembershipSaveRequestDto request
    ) {
        logger.info("MembershipService :: saveMembership ~~");

        Membership membership = Membership.builder()
                .name(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .year(request.getYear())
                .build();

        Membership savedMembership = membershipRepository.save(membership);

        return MembershipResponseDto.from(savedMembership);
    }

    @Transactional(readOnly = true)
    public Page<MembershipResponseDto> findAllMemberships(Pagecond pagecond) {
        Pageable pageable = PageRequest.of(pagecond.getPageNum() - 1, pagecond.getPageSize(), Sort.by(Sort.Order.desc("createdAt")));

        return membershipRepository.findAllActiveMemberships(pageable).map(MembershipResponseDto::from);
    }

    @Transactional(readOnly = true)
    public MembershipResponseDto findMembershipById(Long membershipId) {
        Membership membership = membershipRepository.findByIdAndDeletedAtIsNull(membershipId)
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "아이디가 없거나 삭제된 멤버십입니다."));

        return MembershipResponseDto.from(membership);
    }

    @Transactional
    public MembershipResponseDto updateMembership(
            Long membershipId,
            MembershipUpdateRequestDto request
    ) {
        Membership membership = membershipRepository.findByIdAndDeletedAtIsNull(membershipId)
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "아이디가 없거나 삭제된 멤버십입니다."));

        membership.update(request.getName(), request.getPrice(), request.getQuantity(), request.getYear());

        Membership updatedMembership = membershipRepository.save(membership);

        return MembershipResponseDto.from(updatedMembership);
    }

    @Transactional
    public Long deleteMembership(Long membershipId) {
        Membership membership = findMembership(membershipId);

        return membership.delete();
    }

    public Membership findMembership(Long id) {
        Membership membership = membershipRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "멤버쉽이 존재하지 않습니다."));

        if (membership.isDeleted()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "이미 삭제된 멤버쉽입니다.");
        }

        return membership;
    }
}
