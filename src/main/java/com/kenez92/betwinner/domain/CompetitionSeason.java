package com.kenez92.betwinner.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class CompetitionSeason {
    @Id
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String winner;
    @ManyToOne
    private Competition competition;

    @OneToMany
            (targetEntity = CurrentMatchDay.class,
                    fetch = FetchType.LAZY,
                    mappedBy = "competitionSeason"
            )
    List<CurrentMatchDay> currentMatchDayList = new ArrayList<>();
}
