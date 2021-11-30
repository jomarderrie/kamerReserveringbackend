package com.example.taskworklife.models.attachment;

import com.example.taskworklife.models.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProfileFileAttachment extends FileAttachment {
    @OneToOne
    @JsonBackReference
    private User user;
}
