package com.kenez92.betwinner.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Component
public class CompetitionDto {
    private Long id;
    private Long competitionId;
    private String name;
    private Long seasonId;
    private LocalDate seasonStart;
    private LocalDate seasonEnd;
    private Integer currentMatchDay;
    private String winner;
    List<CompetitionTableDto> competitionTableList;
}
