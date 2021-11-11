package com.example.taskworklife.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class FileAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private String name;

    private String fileType;

    private Kamer kamer;
}
