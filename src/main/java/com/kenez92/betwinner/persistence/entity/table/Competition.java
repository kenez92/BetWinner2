package com.kenez92.betwinner.persistence.entity.table;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FOOTBALL_ID", unique = true, nullable = false)
    private Long footballId;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "LAST_SAVED_ROUND")
    private Integer lastSavedRound;

    @Builder.Default
    @OneToMany(targetEntity = CompetitionSeason.class,
            fetch = FetchType.LAZY,
            mappedBy = "competition")
    private List<CompetitionSeason> competitionSeasonList = new ArrayList<>();

    public boolean equals(Competition competition) {
        if (competition == null) {
            return false;
        }
        boolean result;
        result = this.footballId.equals(competition.getFootballId());
        result &= this.name.equals(competition.getName());
        result &= this.lastSavedRound.equals(competition.getLastSavedRound());
        return result;
    }
}
