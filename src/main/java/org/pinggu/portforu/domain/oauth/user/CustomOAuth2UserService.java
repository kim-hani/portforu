package org.pinggu.portforu.domain.oauth.user;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.member.enums.UserRole;
import org.pinggu.portforu.domain.member.repository.MemberRepository;
import org.pinggu.portforu.domain.oauth.provider.google.GoogleUserInfo;
import org.pinggu.portforu.domain.oauth.provider.kakao.KakaoUserInfo;
import org.pinggu.portforu.domain.oauth.provider.naver.NaverUserInfo;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo userInfo;

        if (provider.equals("naver")) {
            userInfo = new NaverUserInfo(oAuth2User.getAttributes());
        } else if (provider.equals("google")) {
            userInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (provider.equals("kakao")) {
            userInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "지원하지 않는 로그인 서비스입니다.");
        }

        String providerEmail = provider + "_" + userInfo.getEmail();

        Member member = memberRepository.findByEmail(providerEmail)
                .orElseGet(() -> registerNewMember(userInfo, providerEmail));

        return new CustomOAuth2User(member, userInfo.getAttributes());
    }

    private Member registerNewMember(OAuth2UserInfo userInfo, String providerEmail) {
        Member member = Member.builder()
                .email(providerEmail)
                .password(encodedPassword())
                .name(userInfo.getName())
                .phoneNumber(userInfo.getPhone())
                .address("")
                .userRole(UserRole.ROLE_USER)
                .build();

        return memberRepository.save(member);
    }

    private String encodedPassword() {
        String randomPassword = UUID.randomUUID().toString();
        return passwordEncoder.encode(randomPassword);
    }

}
