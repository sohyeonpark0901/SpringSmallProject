package secondMarket.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import secondMarket.demo.repository.JdbcTemplateMemberRepository;
import secondMarket.demo.repository.MemberRepository;
import secondMarket.demo.repository.MemoryMemberRepository;
import secondMarket.demo.service.MemberService;

import javax.sql.DataSource;
@EnableWebSecurity
@Configuration
public class SpringConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers("/css/**","/js/**","img/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests().and().formLogin().loginPage("/login")
                .failureUrl("/?error").permitAll() // default
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .defaultSuccessUrl("/home");
    }
    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }
    @Bean
    public MemberRepository memberRepository(){
        //return new MemoryMemberRepository();
        return new JdbcTemplateMemberRepository(dataSource);
    }
}
