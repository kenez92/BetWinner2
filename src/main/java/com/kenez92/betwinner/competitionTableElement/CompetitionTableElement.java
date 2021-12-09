package com.kenez92.betwinner.competitionTableElement;

import com.kenez92.betwinner.competitionTable.CompetitionTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table
public class CompetitionTableElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public boolean equals(CompetitionTableElement competitionTableElement) {
        if (competitionTableElement == null) {
            return false;
        }
        boolean result;
        result = this.competitionTable.equals(competitionTableElement.getCompetitionTable());
        result &= this.name.equals(competitionTableElement.getName());
        result &= this.position.equals(competitionTableElement.getPosition());
        result &= this.playedGames.equals(competitionTableElement.getPlayedGames());
        result &= this.won.equals(competitionTableElement.getWon());
        result &= this.draw.equals(competitionTableElement.getDraw());
        result &= this.points.equals(competitionTableElement.getPoints());
        result &= this.goalsFor.equals(competitionTableElement.getGoalsFor());
        result &= this.goalsAgainst.equals(competitionTableElement.getGoalsAgainst());
        result &= this.goalDifference.equals(competitionTableElement.getGoalDifference());
        return result;
    }
}
