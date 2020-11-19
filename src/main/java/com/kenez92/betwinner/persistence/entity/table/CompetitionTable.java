package com.kenez92.betwinner.persistence.entity.table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table
@Entity
public class CompetitionTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "STAGE")
    private String stage;

    @Column(name = "TYPE")
    private String type;

    @ManyToOne
    private CurrentMatchDay currentMatchDay;

    @Builder.Default
    @OneToMany(targetEntity = CompetitionTableElement.class,
            fetch = FetchType.LAZY,
            mappedBy = "competitionTable")
    private List<CompetitionTableElement> competitionTableElements = new ArrayList<>();

}
