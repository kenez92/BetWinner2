package com.kenez92.betwinner.domain;

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
    private Long id;

    @Column(unique = true)
    private Long footballId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String winner;

    @ManyToOne
    private Competition competition;

    @Builder.Default
    @OneToMany(targetEntity = CurrentMatchDay.class,
            fetch = FetchType.LAZY,
            mappedBy = "competitionSeason")
    private List<CurrentMatchDay> currentMatchDayList = new ArrayList<>();
}
