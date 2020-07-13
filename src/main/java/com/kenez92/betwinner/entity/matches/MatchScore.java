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
}
