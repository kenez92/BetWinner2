package com.kenez92.betwinner.persistence.entity.table;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
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

    public boolean equals(CompetitionTable competitionTable) {
        if (competitionTable == null) {
            return false;
        }
        boolean result;
        result = this.stage.equals(competitionTable.getStage());
        result &= this.type.equals(competitionTable.getType());
        result &= this.currentMatchDay.equals(competitionTable.getCurrentMatchDay());
        return result;
    }

}
