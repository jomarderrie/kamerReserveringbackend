package com.example.taskworklife.models;

import com.example.taskworklife.models.attachment.KamerFileAttachment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SNAKE_CASE;

@Entity
@Getter
@Setter
@Table(name = "kamer")
@RequiredArgsConstructor
public class Kamer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;


    private String naam;

    private LocalDateTime sluitTijd;
    private LocalDateTime startTijd;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Reservering> reservering = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kamer")
    @JsonManagedReference
    private List<KamerFileAttachment> attachments = new ArrayList<>();

    public Kamer addFileAttachment(KamerFileAttachment fileAttachment) {
        fileAttachment.setKamer(this);
        this.attachments.add(fileAttachment);
        return this;
    }

    public Kamer addReservering(Reservering reservering) {
        reservering.setKamer(this);
        this.reservering.add(reservering);
        return this;
    }
}
