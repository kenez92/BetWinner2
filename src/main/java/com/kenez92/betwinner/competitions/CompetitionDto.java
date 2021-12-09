package com.kenez92.betwinner.competitions;

import com.kenez92.betwinner.competitionSeason.CompetitionSeasonDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CompetitionDto {
    private Long id;
    private Long footballId;
    private String name;
    private Integer lastSavedRound;
    private List<CompetitionSeasonDto> competitionSeasonList;
}
