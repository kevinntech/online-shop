package me.kevinntech.modules.users.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.kevinntech.modules.users.User;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class UserSaveRequestDto {

    @NotBlank
    @Length(min = 3, max = 20)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$")
    private String nickname;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Length(min = 8, max = 50)
    private String password;

    public UserSaveRequestDto(User user) {
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public User toEntity() {
        return User.builder()
                .nickname(nickname)
                .email(email)
                .password(password)
                .build();
    }

}
