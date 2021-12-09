package com.kenez92.betwinner.currentMatchDay;

import com.kenez92.betwinner.competitionSeason.CompetitionSeason;
import com.kenez92.betwinner.competitionTable.CompetitionTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@NamedNativeQuery(
        name = "CurrentMatchDay.getActualCurrentMatchDay",
        query = "SELECT cmd.match_day FROM current_match_day cmd JOIN competition_season cs " +
                "ON cs.id = cmd.competition_season_id WHERE cs.competition_id = :COMPETITION_ID"
        // resultClass = Integer.class
)
@NamedQuery(
        name = "CurrentMatchDay.findBySeasonId",
        query = "FROM CurrentMatchDay WHERE CompetitionSeason.getId() = COMPETITION_SEASON_ID"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table
public class CurrentMatchDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "MATCH_DAY")
    private Integer matchDay;

    @ManyToOne
    private CompetitionSeason competitionSeason;

    @Builder.Default
    @OneToMany(targetEntity = CompetitionTable.class,
            fetch = FetchType.LAZY,
            mappedBy = "currentMatchDay")
    private List<CompetitionTable> competitionTableList = new ArrayList<>();

    public boolean equals(CurrentMatchDay currentMatchDay) {
        if (currentMatchDay == null) {
            return false;
        }
        boolean result;
        result = this.matchDay.equals(currentMatchDay.matchDay);
        result &= this.competitionSeason.equals(competitionSeason);
        return result;
    }
}
