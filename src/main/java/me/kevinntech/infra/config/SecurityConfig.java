package me.kevinntech.infra.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 권한 확인 없이 접근 가능해야 함
                .mvcMatchers("/", "/login", "/users/**", "/products/**", "/api/v1/**").permitAll()
                // 나머지는 로그인을 해야 사용 할 수 있다.
                .anyRequest().authenticated();
    }

    // static 리소스는 스프링 시큐리티 필터를 적용하지 않도록 한다.
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .mvcMatchers("/frontend/**", "/images/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());

        web.ignoring().antMatchers("/favicon.ico", "/resources/**", "/error");
    }

}