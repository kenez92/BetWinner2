package com.kenez92.betwinner.domain.matches;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "\"MATCH\"")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FOOTBALL_ID")
    private Long footballId;

    @Column(name = "HOME_TEAM")
    private String homeTeam;

    @Column(name = "AWAY_TEAM")
    private String awayTeam;

    @Column(name = "COMPETITION_ID")
    private Long competitionId;

    @Column(name = "SEASON_ID")
    private Long seasonId;

    @Column(name = "DATE")
    private Date date;

    @Column(name = "HOME_TEAM_POSITION_IN_TABLE")
    private Integer homeTeamPositionInTable;

    @Column(name = "AWAY_TEAM_POSITION_IN_TABLE")
    private Integer awayTeamPositionInTable;

    @Column(name = "HOME_TEAM_CHANCE")
    private Double homeTeamChance;

    @Column(name = "AWAY_TEAM_CHANCE")
    private Double awayTeamChance;

    @Column(name = "ROUND")
    private Integer round;

    @ManyToOne
    private MatchDay matchDay;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "MATCH_SCORE_ID")
    private MatchScore matchScore;

    @ManyToOne
    private Weather weather;
}
