package com.kenez92.betwinner.domain.fotballdata.matches;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FootballMatchList {
    private int count;
    private FootballFilters filters;
    private FootballMatch[] matches;
}
