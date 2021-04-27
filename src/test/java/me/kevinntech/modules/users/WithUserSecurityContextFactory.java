package me.kevinntech.modules.users;


import me.kevinntech.modules.users.dto.UserSaveRequestDto;
import me.kevinntech.modules.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

@RequiredArgsConstructor
public class WithUserSecurityContextFactory implements WithSecurityContextFactory<WithUser> {

    private final UserService userService;

    @Override
    public SecurityContext createSecurityContext(WithUser withAccount) {
        String nickname = withAccount.value();

        UserSaveRequestDto requestDto = new UserSaveRequestDto();
        requestDto.setNickname(nickname);
        requestDto.setEmail(nickname + "@email.com");
        requestDto.setPassword("12345678");

        userService.saveNewUser(requestDto);

        UserDetails principal = userService.loadUserByUsername(nickname);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        return context;
    }
}
