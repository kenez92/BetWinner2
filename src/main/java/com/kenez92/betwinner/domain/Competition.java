package com.kenez92.betwinner.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Competition {
    @Id
    @Column(unique = true)
    private Long id;
    private String name;
    @OneToMany(
            targetEntity = CompetitionSeason.class,
            fetch = FetchType.LAZY,
            mappedBy = "competition"
    )
    private List<CompetitionSeason> competitionSeasonList = new ArrayList<>();
}
