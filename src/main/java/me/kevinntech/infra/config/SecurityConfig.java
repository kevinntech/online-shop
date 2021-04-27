package me.kevinntech.infra.config;

import lombok.RequiredArgsConstructor;
import me.kevinntech.modules.users.service.UserService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService; // userDetailsService를 구현한 클래스 타입의 빈을 주입 받음

    private final DataSource dataSource; // JPA를 사용하고 있기 때문에 DataSource가 빈으로 등록되어 있다.

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 권한 확인 없이 접근 가능해야 함
                .mvcMatchers("/", "/login", "/users/**").permitAll()
                .mvcMatchers("/api/v1/**").permitAll()
                // 나머지는 로그인을 해야 사용 할 수 있다.
                .anyRequest().authenticated();

        // 로그인, 로그아웃 관련 코드
        http.formLogin()
                .loginPage("/login").permitAll();

        http.logout()
                .logoutSuccessUrl("/");

        // 로그인 기억하기 (RememberMe)
        http.rememberMe()
                .userDetailsService(userService)  // userDetailsService를 지정
                .tokenRepository(tokenRepository());
    }

    // static 리소스는 스프링 시큐리티 필터를 적용하지 않도록 한다.
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .mvcMatchers("/frontend/**", "/images/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());

        web.ignoring().antMatchers("/favicon.ico", "/resources/**", "/error");
    }

    // PersistentTokenRepository의 구현체인 JdbcTokenRepositoryImpl를 빈으로 등록한다.
    @Bean
    public PersistentTokenRepository tokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource); // DataSource를 주입
        return jdbcTokenRepository;
    }

}