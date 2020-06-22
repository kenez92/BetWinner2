package com.kenez92.betwinner.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Competition {
    @Id
    private Long id;
    private Long competitionId;
    private String name;
    //@Column(unique = true)
    private Long seasonId;
    private LocalDate seasonStart;
    private LocalDate seasonEnd;
    private Integer currentMatchDay;
    private String winner;

    @Builder.Default
    @OneToMany(
            fetch = FetchType.LAZY,
            targetEntity = CompetitionSeason.class,
            mappedBy = "competition"
    )
    List<CompetitionSeason> competitionSeasonList = new ArrayList<>();
}
