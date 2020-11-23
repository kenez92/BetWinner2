package com.kenez92.betwinner.persistence.entity.matches;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
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
public class MatchDay {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DATE", unique = true, nullable = false)
    private LocalDate localDate;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY,
            targetEntity = Match.class,
            mappedBy = "matchDay")
    private List<Match> matchesList = new ArrayList<>();

    public boolean equals(MatchDay matchDay) {
        if (matchDay == null) {
            return false;
        }
        return localDate.equals(matchDay.getLocalDate());
    }
}
