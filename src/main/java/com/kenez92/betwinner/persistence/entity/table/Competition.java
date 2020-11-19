package com.kenez92.betwinner.persistence.entity.table;

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
@Builder
@Entity
@Table
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FOOTBALL_ID", unique = true)
    private Long footballId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LAST_SAVED_ROUND")
    private Integer lastSavedRound;

    @Builder.Default
    @OneToMany(targetEntity = CompetitionSeason.class,
            fetch = FetchType.LAZY,
            mappedBy = "competition")
    private List<CompetitionSeason> competitionSeasonList = new ArrayList<>();
}
