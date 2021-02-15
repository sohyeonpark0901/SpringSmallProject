package secondMarket.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import secondMarket.demo.domain.Member;
import secondMarket.demo.model.member.MemberForm;
import secondMarket.demo.model.member.MemberLoginDto;
import secondMarket.demo.repository.MemberRepository;
import secondMarket.demo.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    @GetMapping("/members/login")
    public String loginForm(){
        return "members/login";
    }

    @PostMapping("/members/login")
    public String login(HttpServletRequest request,
                        MemberLoginDto memberLoginDto, HttpSession session){

        /**
         * 1. email, password 파라미터로 들어온다 ->그에 대한 DTO를 만든다.
         * 2. email로 DB에서 조회 (없으면 error 처리)
         * 3. 조회해온 회원과 입력받은 회원정보의 비밀번호 일치여부 확인
         */

        Member findMember = memberService.LoginMember(memberLoginDto);

        if(!findMember.getPassword().equals(memberLoginDto.getPassword())){
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        session.setAttribute("loginMember",findMember);

        return "redirect:/";
    }

    @GetMapping("/members/logout")
    public String logout(HttpServletRequest request,HttpSession session){

        Member loginMember = (Member) session.getAttribute("loginMember");
        if(loginMember == null){
            throw new IllegalStateException("로그인한 사용자가 아닙니다.");
        }

        session.removeAttribute("loginMember");

        return "redirect:/";
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

        Member loginMember = (Member) session.getAttribute("loginMember");

        if(loginMember == null){
            throw new IllegalStateException("로그인한 사용자가 아닙니다.");
        }

        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";

    }
}
