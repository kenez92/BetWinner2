package com.kenez92.betwinner.persistence.entity.weather;

import com.kenez92.betwinner.persistence.entity.matches.Match;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
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

    public boolean equals(Weather weather) {
        if (weather == null) return false;
        if (!Objects.equals(this.country, weather.getCountry())) return false;
        if (!Objects.equals(this.date.getTime(), weather.getDate().getTime())) return false;
        if (!Objects.equals(this.tempFelt, weather.getTempFelt())) return false;
        if (!Objects.equals(this.tempMin, weather.getTempMin())) return false;
        if (!Objects.equals(this.tempMax, weather.getTempMax())) return false;
        return Objects.equals(this.pressure, weather.getPressure());
    }
}
