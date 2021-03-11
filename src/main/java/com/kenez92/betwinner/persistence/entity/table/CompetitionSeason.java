package com.kenez92.betwinner.persistence.entity.table;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
@Table
public class CompetitionSeason {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FOOTBALL_ID", unique = true, nullable = false)
    private Long footballId;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Column(name = "WINNER")
    private String winner;

    @ManyToOne
    private Competition competition;

    @Builder.Default
    @OneToMany(targetEntity = CurrentMatchDay.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "competitionSeason")
    private List<CurrentMatchDay> currentMatchDayList = new ArrayList<>();

    public boolean equals(CompetitionSeason competitionSeason) {
        if (competitionSeason == null) {
            return false;
        }
        boolean result;
        result = this.footballId.equals(competitionSeason.getFootballId());
        result &= this.startDate.equals(competitionSeason.getStartDate());
        result &= this.endDate.equals(competitionSeason.getEndDate());
        result &= this.winner.equals(competitionSeason.getWinner());
        return result;
    }
}
