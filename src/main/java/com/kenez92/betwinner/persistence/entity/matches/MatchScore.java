package com.kenez92.betwinner.persistence.entity.matches;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
@Entity
@Table
public class MatchScore {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FOOTBALL_MATCH_ID", unique = true)
    private Long footballMatchId;

    @Column(name = "WINNER")
    private String winner;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "DURATION")
    private String duration;

    @Column(name = "FULL_TIME_HOME_TEAM")
    private Integer fullTimeHomeTeam;

    @Column(name = "FULL_TIME_AWAY_TEAM")
    private Integer fullTimeAwayTeam;

    @Column(name = "HALF_TIME_HOME_TEAM")
    private Integer halfTimeHomeTeam;

    @Column(name = "HALF_TIME_AWAY_TEAM")
    private Integer halfTimeAwayTeam;

    @Column(name = "EXTRA_TIME_HOME_TEAM")
    private Integer extraTimeHomeTeam;

    @Column(name = "EXTRA_TIME_AWAY_TEAM")
    private Integer extraTimeAwayTeam;

    @Column(name = "PENALTIES_HOME_TEAM")
    private Integer penaltiesHomeTeam;

    @Column(name = "PENALTIES_AWAY_TEAM")
    private Integer penaltiesAwayTeam;

    public boolean equals(MatchScore matchScore) {
        if (!Objects.equals(this.footballMatchId, matchScore.getFootballMatchId())) return false;
        if (!Objects.equals(this.winner, matchScore.getWinner())) return false;
        if (!Objects.equals(this.status, matchScore.getStatus())) return false;
        if (!Objects.equals(this.duration, matchScore.getDuration())) return false;
        if (!Objects.equals(this.fullTimeHomeTeam, matchScore.getFullTimeHomeTeam())) return false;
        if (!Objects.equals(this.fullTimeAwayTeam, matchScore.getFullTimeAwayTeam())) return false;
        if (!Objects.equals(this.halfTimeHomeTeam, matchScore.getHalfTimeHomeTeam())) return false;
        if (!Objects.equals(this.halfTimeAwayTeam, matchScore.halfTimeAwayTeam)) return false;
        if (!Objects.equals(this.extraTimeHomeTeam, matchScore.extraTimeHomeTeam)) return false;
        if (!Objects.equals(this.extraTimeAwayTeam, matchScore.extraTimeAwayTeam)) return false;
        if (!Objects.equals(this.penaltiesHomeTeam, matchScore.penaltiesHomeTeam)) return false;
        return Objects.equals(this.penaltiesAwayTeam, matchScore.penaltiesAwayTeam);
    }
}