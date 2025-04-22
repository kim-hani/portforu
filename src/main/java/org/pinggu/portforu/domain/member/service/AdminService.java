package org.pinggu.portforu.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.domain.Pagecond;
import org.pinggu.portforu.domain.member.dto.response.MemberResponseDto;
import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final MemberRepository memberRepository;
    private final MemberFinder memberFinder;

    @Transactional(readOnly = true)
    public MemberResponseDto findMember(Long memberId) {
        Member member = memberFinder.findMemberById(memberId);

        return MemberResponseDto.from(member);
    }

    @Transactional(readOnly = true)
    public Page<MemberResponseDto> findAllMembers(Pagecond pagecond) {
        Pageable pageable = PageRequest.of(pagecond.getPageNum() - 1, pagecond.getPageSize(), Sort.by(Sort.Order.desc("id")));
        Page<Member> members = memberRepository.findAll(pageable);

        return members.map(MemberResponseDto::from);
    }

    @Transactional
    public Long deleteMember(Long memberId) {
        Member member = memberFinder.findMemberById(memberId);

        memberRepository.delete(member);

        return member.getId();
    }

}
