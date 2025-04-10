package org.pinggu.portforu.domain.member.repository;

import org.pinggu.portforu.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);

    Page<Member> findAllByDeletedAtIsNull(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET "
            + "m.name = COALESCE(:name, m.name), "
            + "m.phoneNumber = COALESCE(:phoneNumber, m.phoneNumber), "
            + "m.address = COALESCE(:address, m.address) "
            + "WHERE m.id = :id")
    Integer updateMemberInfo(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("phoneNumber") String phoneNumber,
            @Param("address") String address
    );

    @Modifying
    @Query("UPDATE Member m SET m.password = :newPassword WHERE m.id = :id")
    Integer updatePassword(
            @Param("id") Long id,
            @Param("newPassword") String newPassword
    );

}
