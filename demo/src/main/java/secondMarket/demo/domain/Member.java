package secondMarket.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Member {
    private Long id;
    private String password;
    private String name;
    private String address;
    private String email;
    private String phone;

}
