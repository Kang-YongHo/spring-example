package io.github.kyago.domain.user.dao;

import io.github.kyago.domain.user.entity.Member;
import io.github.kyago.domain.user.model.Email;
import io.github.kyago.domain.user.model.ReferralCode;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member> {

    Optional<Member> findByEmail(Email email);

    boolean existsByEmail(Email email);

    boolean existsByReferralCode(ReferralCode referralCode);

}
