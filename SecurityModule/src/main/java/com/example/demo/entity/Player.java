package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer points;

    private PlayerType playerType;

    @ManyToOne
    private Team team;

    enum PlayerType{
        BATSMAN, BOWLER, WICKET_KEEPER, ALL_ROUNDER
    }
}