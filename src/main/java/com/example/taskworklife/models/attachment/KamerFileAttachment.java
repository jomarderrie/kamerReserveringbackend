package com.example.taskworklife.models.attachment;


import com.example.taskworklife.models.Kamer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class KamerFileAttachment extends FileAttachment {
    @ManyToOne
    @JsonBackReference
    private Kamer kamer;
}
