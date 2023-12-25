package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class CricketMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Team team1;

    @ManyToOne
    private Team team2;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @OneToMany
    private List<Player> team1Players;

    @OneToMany
    private List<Player> team2Players;

}
