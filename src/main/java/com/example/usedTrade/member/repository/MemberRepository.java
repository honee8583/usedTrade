package com.example.usedTrade.member.repository;

import com.example.usedTrade.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByEmailAuthKey(String uuid);
    Optional<Member> findByEmailAndName(String email, String name);
    Optional<Member> findByResetPasswordKey(String resetPasswordKey);
}
