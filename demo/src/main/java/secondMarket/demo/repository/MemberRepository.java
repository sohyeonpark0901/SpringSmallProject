package secondMarket.demo.repository;

import secondMarket.demo.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByEmail(String Email);
    List<Member> findAll();

}
