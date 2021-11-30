package com.example.taskworklife.dto.user;

import com.example.taskworklife.models.attachment.ProfileFileAttachment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponseDto {
    private String naam;
    @NotNull
    private String achternaam;
    private String email;
    private ProfileFileAttachment profileFileAttachment;
    private Date laatstIngelodgeDatumDisplay;
    private String role; //ROLE_USER{ read, edit }, ROLE_ADMIN {delete}


}
