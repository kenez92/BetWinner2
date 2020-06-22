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
public class CurrentMatchDay {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private CompetitionSeason competitionSeason;

    @OneToMany(
            targetEntity = CompetitionTable.class,
            fetch = FetchType.LAZY,
            mappedBy = "currentMatchDay"
    )
    private List<CompetitionTable> competitionTableList = new ArrayList<>();
}
