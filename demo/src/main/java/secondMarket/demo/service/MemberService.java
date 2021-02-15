package secondMarket.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import secondMarket.demo.domain.Member;
import secondMarket.demo.repository.MemberRepository;
import secondMarket.demo.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;


public class MemberService {
    private final MemberRepository memberRepository;
    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public Long join(Member member){
        validateDuplicateMember (member);
        memberRepository.save(member);
        return member.getId();
    }
//    public Optional<Member> login(String email,String password){
//        return
//    }
    private void validateDuplicateMember(Member member){
        memberRepository.findByEmail(member.getEmail())
                .ifPresent(m->{
                    throw new IllegalStateException("이미 존재하는 회원 입니다.");
                });
    }
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

}
