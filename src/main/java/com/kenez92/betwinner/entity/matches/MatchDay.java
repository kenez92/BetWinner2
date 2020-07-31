package com.kenez92.betwinner.entity.matches;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table
public class MatchDay {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DATE", unique = true)
    private LocalDate localDate;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY,
            targetEntity = Match.class,
            mappedBy = "matchDay")
    private List<Match> matchesList = new ArrayList<>();
}
