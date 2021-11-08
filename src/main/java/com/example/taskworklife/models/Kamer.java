package com.example.taskworklife.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private List<Reservering> reserveringList = new ArrayList<>();



}
