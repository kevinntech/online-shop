package me.kevinntech.modules.users;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 현재 사용자가 anonymousUser이면 핸들러 파라미터에 null로 설정하고 인증된 사용자이면 user를 설정한다.
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : user")
public @interface CurrentUser {
}
