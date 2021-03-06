package me.kevinntech.modules.users;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithUserSecurityContextFactory.class) // 시큐리티 컨텍스트를 만들어 줄 팩토리를 지정
public @interface WithUser {

    String value();

}