package com.kenez92.betwinner.matchDay;

import com.kenez92.betwinner.match.Match;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
