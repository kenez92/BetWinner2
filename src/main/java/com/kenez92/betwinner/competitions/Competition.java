package com.kenez92.betwinner.competitions;

import com.kenez92.betwinner.competitionSeason.CompetitionSeason;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
