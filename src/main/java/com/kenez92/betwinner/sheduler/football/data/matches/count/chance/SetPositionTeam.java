package com.kenez92.betwinner.sheduler.football.data.matches.count.chance;

import com.kenez92.betwinner.domain.matches.MatchDto;
import com.kenez92.betwinner.domain.table.CompetitionTableElementDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.service.competition.CompetitionTableElementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class SetPositionTeam {
    private final static String TYPE_TOTAL = "TOTAL";
    private final static String STAGE_SEASON_REGULAR = "REGULAR_SEASON";
    private final CompetitionTableElementService competitionTableElementService;

    public void setPosition(final MatchDto matchDto) {
        Integer round = matchDto.getRound();
        CompetitionTableElementDto homeTeam = getTeam(matchDto.getHomeTeam(), round);
        CompetitionTableElementDto awayTeam = getTeam(matchDto.getAwayTeam(), round);
        matchDto.setHomeTeamPositionInTable(homeTeam.getPosition());
        matchDto.setAwayTeamPositionInTable(awayTeam.getPosition());
    }

    private CompetitionTableElementDto getTeam(String teamName, Integer round) {
        List<CompetitionTableElementDto> teamDtoList = competitionTableElementService.findByName(teamName);
        List<CompetitionTableElementDto> filteredTeamDtoList = teamDtoList.stream()
                .filter(element -> element.getCompetitionTable().getType().equals(TYPE_TOTAL))
                .filter(element -> element.getCompetitionTable().getStage().equals(STAGE_SEASON_REGULAR))
                .filter(element -> element.getCompetitionTable().getCurrentMatchDay().getMatchDay().equals(round))
                .collect(Collectors.toList());
        if (filteredTeamDtoList.size() == 1) {
            return filteredTeamDtoList.get(0);
        } else {
            throw new BetWinnerException(BetWinnerException.ERR_FILTERED_LIST_EXCEPTION);
        }
    }
}
