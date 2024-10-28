package capstone.viewIt.member.repository;

import capstone.viewIt.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberId(String memberId);
    Optional<Member> findById(Long id);
    Optional<Member> findByNickname(String nickname);
    boolean existsByMemberId(String memberId);
    boolean existsByNickname(String nickname);
}