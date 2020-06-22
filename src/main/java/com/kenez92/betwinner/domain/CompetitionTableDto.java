package com.kenez92.betwinner.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Component
public class CompetitionTableDto {
    private Long Id;

    private String stage;
    private String type;
    private CompetitionDto competition;
    private List<CompetitionTableElementDto> competitionTableElements;
}
