package com.kenez92.betwinner.entity.table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table
public class CompetitionTableElement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    private CompetitionTable competitionTable;

    @Column(name = "NAME")
    private String name;

    @Column(name = "POSITION")
    private Integer position;

    @Column(name = "PLAYED_GAMES")
    private Integer playedGames;

    @Column(name = "WON")
    private Integer won;

    @Column(name = "DRAW")
    private Integer draw;

    @Column(name = "LOST")
    private Integer lost;

    @Column(name = "POINTS")
    private Integer points;

    @Column(name = "GOALS_SCORED")
    private Integer goalsFor;

    @Column(name = "GOALS_LOST")
    private Integer goalsAgainst;

    @Column(name = "GOALS_DIFFERENCE")
    private Integer goalDifference;
}
