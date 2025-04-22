package org.pinggu.portforu.domain.oauth.user;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.member.enums.UserRole;
import org.pinggu.portforu.domain.member.repository.MemberRepository;
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

    private final OAuth2UserInfoFactory oAuth2UserInfoFactory;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2UserInfo userInfo = oAuth2UserInfoFactory.from(
                userRequest.getClientRegistration().getRegistrationId(),
                oAuth2User.getAttributes()
        );

        String providerEmail = userInfo.getProvider() + "_" + userInfo.getEmail();

        Member member = memberRepository.findByEmail(providerEmail)
                .orElseGet(() -> registerNewMember(userInfo, providerEmail));

        return new CustomOAuth2User(member, userInfo.getAttributes());
    }

    private Member registerNewMember(OAuth2UserInfo userInfo, String providerEmail) {
        return memberRepository.save(
                Member.builder()
                        .email(providerEmail)
                        .password(encodedPassword())
                        .name(userInfo.getName())
                        .phoneNumber(userInfo.getPhone())
                        .address("")
                        .userRole(UserRole.ROLE_USER)
                        .provider(userInfo.getProvider())
                        .build()
        );
    }

    private String encodedPassword() {
        return passwordEncoder.encode(UUID.randomUUID().toString());
    }

}
