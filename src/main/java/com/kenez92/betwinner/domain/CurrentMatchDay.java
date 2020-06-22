package com.kenez92.betwinner.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class CurrentMatchDay {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(
            targetEntity = CompetitionTable.class,
            fetch = FetchType.LAZY,
            mappedBy = "currentMatchDay"
    )
    private List<CompetitionTable> competitionTableList = new ArrayList<>();
}
