package secondMarket.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import secondMarket.demo.domain.Member;
import secondMarket.demo.model.member.MemberLoginDto;
import secondMarket.demo.repository.MemberRepository;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public Long join(Member member){
        validateDuplicateMember(member);
        if((String.valueOf(member.getPassword().length()).equals("0"))){

            throw new IllegalStateException("비밀번호 없습니다.");
        }
        memberRepository.save(member);
        return member.getMemberId();
    }

    private void validateDuplicateMember(Member member){
        memberRepository.findByEmail(member.getEmail())
                .ifPresent(m->{
                    System.out.println(member.getEmail());
                    throw new IllegalStateException("이미 존재하는 회원 입니다.");
                });
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }


    public Member LoginMember(MemberLoginDto memberLoginDto){
        Member findMember = memberRepository.findByEmail(memberLoginDto.getEmail())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));
        return findMember;
    }

}
