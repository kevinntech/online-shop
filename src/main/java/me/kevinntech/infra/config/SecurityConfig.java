package me.kevinntech.infra.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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

}