package org.pinggu.portforu.member.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.member.enums.UserRole;
import org.pinggu.portforu.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    private static final String EMAIL = "yumi@example.com";
    private static final String NON_EXISTING_EMAIL = "nonYumi@test.com";
    private static final String PASSWORD = "Password1!";
    private static final String NAME = "yumi";
    private static final String PHONE = "010-1234-0410";
    private static final String ADDRESS = "고양시 야옹동";

    private Member member;

    @BeforeEach
    void setUp() {
        member = new Member(EMAIL, PASSWORD, NAME, PHONE, ADDRESS, UserRole.ROLE_USER);
        member = memberRepository.save(member);
    }

    @Nested
    class ExistsByEmail {
        @Test
        void 존재하는_이메일_조회_시_true_반환() {
            // when
            boolean actual = memberRepository.existsByEmail(EMAIL);

            // then
            assertThat(actual).isTrue();
        }

        @Test
        void 존재하지_않는_이메일_조회_시_false_반환() {
            // when
            boolean actual = memberRepository.existsByEmail(NON_EXISTING_EMAIL);

            // then
            assertThat(actual).isFalse();
        }
    }

    @Nested
    class FindByEmail {
        @Test
        void 존재하는_이메일로_조회_성공() {
            // when
            Optional<Member> actual = memberRepository.findByEmail(EMAIL);

            // then
            assertThat(actual)
                    .isPresent()
                    .get()
                    .usingRecursiveComparison()
                    .ignoringFields("id", "createdAt", "updatedAt")
                    .isEqualTo(member);
        }

        @Test
        void 존재하지_않는_이메일_조회_시_빈_Optional_반환() {
            // when
            Optional<Member> actual = memberRepository.findByEmail(NON_EXISTING_EMAIL);

            // then
            assertThat(actual).isEmpty();
        }
    }

    @Test
    void 중복_이메일_등록_시도_시_에러_발생() {
        // given & when
        Member newMember = new Member(EMAIL, "newPassword", "newName", "phone", "address", UserRole.ROLE_USER);

        Throwable actualException = catchThrowable(() -> memberRepository.saveAndFlush(newMember));

        // then
        assertThat(actualException)
                .isInstanceOf(DataIntegrityViolationException.class);
    }

}

// @DataJpaTest는 기본적으로 트랜잭션 롤백을 하지만, save()후 flush()가 없어 즉시 반영되지 않을 수 있음