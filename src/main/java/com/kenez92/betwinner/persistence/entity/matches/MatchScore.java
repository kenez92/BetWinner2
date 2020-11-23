package com.kenez92.betwinner.persistence.entity.matches;

import lombok.*;

import javax.persistence.*;

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

    @Column(name = "MATCH_ID", unique = true)
    private Long matchId;

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
        if (matchScore == null) {
            return false;
        }
        boolean result;
        result = this.matchId.equals(matchScore.getMatchId());
        if (matchScore.getWinner() != null) {
            result &= this.winner.equals(matchScore.getWinner());
        }
        if (matchScore.getStatus() != null) {
            result &= this.status.equals(matchScore.getStatus());
        }
        if (matchScore.getDuration() != null) {
            result &= this.duration.equals(matchScore.getDuration());
        }
        if (matchScore.getFullTimeHomeTeam() != null) {
            result &= this.fullTimeHomeTeam.equals(matchScore.getFullTimeHomeTeam());
        }
        if (matchScore.getFullTimeAwayTeam() != null) {
            result &= this.fullTimeAwayTeam.equals(matchScore.getFullTimeAwayTeam());
        }
        if (matchScore.getHalfTimeHomeTeam() != null) {
            result &= this.halfTimeHomeTeam.equals(matchScore.getHalfTimeHomeTeam());
        }
        if (matchScore.getHalfTimeAwayTeam() != null) {
            result &= this.halfTimeAwayTeam.equals(matchScore.getHalfTimeAwayTeam());
        }
        if (matchScore.getExtraTimeHomeTeam() != null) {
            result &= this.extraTimeHomeTeam.equals(matchScore.getExtraTimeHomeTeam());
        }
        if (matchScore.getExtraTimeHomeTeam() != null) {
            result &= this.extraTimeAwayTeam.equals(matchScore.getExtraTimeAwayTeam());
        }
        if (matchScore.getPenaltiesHomeTeam() != null) {
            result &= this.penaltiesHomeTeam.equals(matchScore.getPenaltiesHomeTeam());
        }
        if (matchScore.getPenaltiesAwayTeam() != null) {
            result &= this.penaltiesAwayTeam.equals(matchScore.getPenaltiesAwayTeam());
        }
        return result;
    }
}