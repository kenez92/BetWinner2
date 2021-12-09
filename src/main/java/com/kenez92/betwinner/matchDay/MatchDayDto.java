package com.kenez92.betwinner.matchDay;

import com.kenez92.betwinner.match.MatchDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MatchDayDto {
    private Long id;
    private LocalDate localDate;
    private List<MatchDto> matchesList;
}
