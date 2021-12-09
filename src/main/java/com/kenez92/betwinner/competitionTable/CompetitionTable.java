package com.kenez92.betwinner.competitionTable;

import com.kenez92.betwinner.competitionTableElement.CompetitionTableElement;
import com.kenez92.betwinner.currentMatchDay.CurrentMatchDay;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
