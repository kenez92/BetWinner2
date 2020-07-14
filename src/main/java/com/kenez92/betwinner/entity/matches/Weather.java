package com.kenez92.betwinner.entity.matches;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "DATE")
    private Date date;

    @Column(name = "TEMP_FELT")
    private Double tempFelt;

    @Column(name = "TEMP_MIN")
    private Double tempMin;

    @Column(name = "TEMP_MAX")
    private Double tempMax;

    @Column(name = "PRESSURE")
    private Integer pressure;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY,
            targetEntity = Match.class,
            mappedBy = "weather")
    private List<Match> matchList = new ArrayList<>();

}
