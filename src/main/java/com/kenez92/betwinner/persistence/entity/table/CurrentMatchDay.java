package com.kenez92.betwinner.persistence.entity.table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
@Data
@Builder
@Entity
@Table
public class CurrentMatchDay {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
}
