package com.kenez92.betwinner.entity.table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table
public class CompetitionSeason {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FOOTBALL_ID", unique = true)
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
}
