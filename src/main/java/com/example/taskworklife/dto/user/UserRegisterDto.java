package com.example.taskworklife.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserRegisterDto {
    @NotNull(message = "{reservering.constraints.username.NotNull.message}")
    private String naam;
    @NotNull
    private String achterNaam;

    @NotNull
    @UniekeMail
    @Pattern(regexp = "\\w+@\\w+\\.\\w+(,\\s*\\w+@\\w+\\.\\w+)*", message = "not valid email")
    private String email;

    @NotNull
    @Size(min = 8, max=255)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message="{reservering.constraints.password.Pattern.message}")
    private String wachtwoord;

    @NotNull(message = "Terms moeten geaccepteerd worden")
    @TermsTrue
    private boolean terms;
}
