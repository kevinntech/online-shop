package me.kevinntech.modules.users.dto;

import lombok.Getter;
import me.kevinntech.modules.users.domain.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Getter
public class UserCustom extends org.springframework.security.core.userdetails.User {

    private User user;

    public UserCustom(User user) {
        super(user.getNickname(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.user = user;
    }

}