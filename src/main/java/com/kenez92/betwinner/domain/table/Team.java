package com.kenez92.betwinner.domain.table;

import javax.persistence.*;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FOOTBALL_ID", unique = true)
    private Long footballId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "COMPETITI0N_ID")
    private Long competitionId;

    @Column(name = "POSITION_IN_TOTAL")
    private Integer positionInTotalTable;

    @Column(name = "POSITION_IN_HOME")
    private Integer positionInHomeTable;

    @Column(name = "POSITION_IN_AWAY")
    private Integer positionInAwayTable;
}
