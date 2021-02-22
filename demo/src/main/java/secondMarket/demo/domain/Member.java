package secondMarket.demo.domain;

import lombok.*;
import secondMarket.demo.model.member.MemberForm;

@Data
public class Member {

    private Long memberId;
    private String password;
    private String name;
    private String address;
    private String email;
    private String phone;
    private String role="user"; //권한




}
