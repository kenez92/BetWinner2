package com.kenez92.betwinner.entity.table;

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
public class CurrentMatchDay {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "MATCH_DAY_ID")
    private Integer matchDay;

    @ManyToOne
    private CompetitionSeason competitionSeason;

    @Builder.Default
    @OneToMany(targetEntity = CompetitionTable.class,
            fetch = FetchType.LAZY,
            mappedBy = "currentMatchDay")
    private List<CompetitionTable> competitionTableList = new ArrayList<>();
}
