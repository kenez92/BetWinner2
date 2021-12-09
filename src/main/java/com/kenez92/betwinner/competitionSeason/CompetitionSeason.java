package com.kenez92.betwinner.competitionSeason;

import com.kenez92.betwinner.competitions.Competition;
import com.kenez92.betwinner.currentMatchDay.CurrentMatchDay;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
