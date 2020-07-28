package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @since 2020-07-28
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
