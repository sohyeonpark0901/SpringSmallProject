package secondMarket.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import secondMarket.demo.domain.Member;
import secondMarket.demo.model.member.MemberForm;
import secondMarket.demo.model.member.MemberLoginDto;
import secondMarket.demo.service.MemberService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
public class MemberController {

    private final MemberService memberService;

    //Todo : 핸들러 인터셉터 공부하기

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/login")
    public String loginForm(){
        return "members/loginForm";
    }

    @PostMapping("/members/login")
    public String login(MemberLoginDto memberLoginDto,HttpSession session){
        /**
         * 1. email,password 파라미터로 들어온다 -> 그에 대한 DTO를 만든다
         * 2. email로 DB에서 조회 없으면 에러
         * 3. 조회해온 회원과 입력받은 회원정보의 비밀번호 일치여부 확인
         */
        Member findMember = memberService.LoginMember(memberLoginDto);

        if(!findMember.getPassword().equals(memberLoginDto.getPassword())){
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        session.setAttribute("memberEmail",findMember);
        return "redirect:/";
    }

    @GetMapping ("/members/logout")
    public String logout(HttpSession session){

        Member memberId = (Member) session.getAttribute("memberEmail");

        if(memberId == null){
            throw new IllegalStateException("로그인한 사용자가 아닙니다.");
        }

        log.info("memberId = {}",memberId.getMemberId());
        System.out.println(memberId.getMemberId());

        session.removeAttribute("memberEmail");

        return "redirect:/";
    }
    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){

            Member member = new Member();
            member.setName(form.getName());
            member.setEmail(form.getEmail());
            member.setPassword(form.getPassword());
            member.setAddress(form.getAddress());
            member.setPhone(form.getPhone());
            memberService.join(member);

        return "redirect:/";
        }



    @GetMapping("/members")
    public String list(Model model,HttpSession session){

        Member loginMember = (Member) session.getAttribute("memberEmail");

        if(loginMember == null){
            throw new IllegalStateException("로그인한 사용자가 아닙니다.");
        }

        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        
        return "members/memberList";
    }
}
