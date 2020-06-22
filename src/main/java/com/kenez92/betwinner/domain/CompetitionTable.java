package com.kenez92.betwinner.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table
@Entity
public class CompetitionTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String stage;
    private String type;

    @ManyToOne
    private Competition competition;


    @Builder.Default
    @OneToMany(
            targetEntity = CompetitionTableElement.class,
            fetch = FetchType.LAZY,
            mappedBy = "competitionTable"
    )
    private List<CompetitionTableElement> competitionTableElements = new ArrayList<>();

}
