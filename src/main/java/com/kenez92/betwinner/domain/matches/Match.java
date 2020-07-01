package com.kenez92.betwinner.domain.matches;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "\"MATCH\"")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long footballId;
    private String homeTeam;
    private String awayTeam;
    private Long competitionId;
    private Long seasonId;
    private Integer homeTeamPositionInTable;
    private Integer awayTeamPositionInTable;
    private Double homeTeamChance;
    private Double awayTeamChance;
    private Integer round;
    @ManyToOne
    private MatchDay matchDay;
}
