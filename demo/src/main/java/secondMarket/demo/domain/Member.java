package secondMarket.demo.domain;

import lombok.*;
import secondMarket.demo.model.member.MemberForm;

@Getter
@Setter
public class Member {

    private Long memberId;
    private String password;
    private String name;
    private String address;
    private String email;
    private String phone;
    private String role; //권한

    public static Member createMember(MemberForm memberForm){
        Member member = new Member();
        member.password = member.getPassword();
        member.name = member.getName();
        member.address = member.getAddress();
        member.email = member.getEmail();
        member.phone = member.getPhone();
        member.role = "ROLE_USER";
        return member;
    }

}
