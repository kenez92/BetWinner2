package com.kenez92.betwinner.competitionTableElement;

import com.kenez92.betwinner.competitionTable.CompetitionTableDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Component
public class CompetitionTableElementDto {
    private Long id;
    private CompetitionTableDto competitionTable;
    private String name;
    private Integer position;
    private Integer playedGames;
    private Integer won;
    private Integer draw;
    private Integer lost;
    private Integer points;
    private Integer goalsFor;
    private Integer goalsAgainst;
    private Integer goalDifference;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompetitionTableElementDto that = (CompetitionTableElementDto) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(position, that.position) &&
                Objects.equals(playedGames, that.playedGames) &&
                Objects.equals(won, that.won) &&
                Objects.equals(draw, that.draw) &&
                Objects.equals(lost, that.lost) &&
                Objects.equals(points, that.points) &&
                Objects.equals(goalsFor, that.goalsFor) &&
                Objects.equals(goalsAgainst, that.goalsAgainst) &&
                Objects.equals(goalDifference, that.goalDifference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, position, playedGames, won, draw, lost, points, goalsFor, goalsAgainst, goalDifference);
    }
}
