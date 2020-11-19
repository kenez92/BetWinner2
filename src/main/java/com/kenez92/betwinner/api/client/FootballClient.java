package com.kenez92.betwinner.api.client;

import com.kenez92.betwinner.domain.fotballdata.match.FootballMatchById;
import com.kenez92.betwinner.domain.fotballdata.matches.FootballMatchList;
import com.kenez92.betwinner.domain.fotballdata.standings.FootballTable;
import com.kenez92.betwinner.exception.BetWinnerException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@RequiredArgsConstructor
@Component
public class FootballClient {
    private final RestTemplate restTemplate;
    @Value("${football.data.org.token}")
    private String token;
    @Value("${football.data.org.token.name}")
    private String tokenName;

    public FootballMatchList getMatches(LocalDate localDate) {
        String url = "https://api.football-data.org/v2/matches?dateFrom=" + localDate + "&dateTo=" + localDate;
        HttpEntity entity = createEntity();
        ResponseEntity<FootballMatchList> response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, entity, FootballMatchList.class);
        } catch (HttpClientErrorException e) {
            if (e.getRawStatusCode() == 403) {
                throw new BetWinnerException(BetWinnerException.ERR_FOOTBALL_MATCHES_WE_DONT_HAVE_ACCESS_EXCEPTION);
            } else {
                throw new BetWinnerException(BetWinnerException.ERR_FOOTBALL_SOMETHING_WENT_WRONG_EXCEPTION);
            }
        }
        return response.getBody();
    }

    public FootballMatchById getMatch(long footballMatchId) {
        String url = "https://api.football-data.org/v2/matches/" + footballMatchId;
        HttpEntity entity = createEntity();
        ResponseEntity<FootballMatchById> response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, entity, FootballMatchById.class);
        } catch (HttpClientErrorException e) {
            if (e.getRawStatusCode() == 400) {
                throw new BetWinnerException(BetWinnerException.ERR_FOOTBALL_MATCH_NOT_FOUND_EXCEPTION);
            } else if (e.getRawStatusCode() == 403) {
                throw new BetWinnerException(BetWinnerException.ERR_FOOTBALL_MATCH_WE_DONT_HAVE_ACCESS_EXCEPTION);
            } else {
                throw new BetWinnerException(BetWinnerException.ERR_FOOTBALL_SOMETHING_WENT_WRONG_EXCEPTION);
            }
        }
        return response.getBody();
    }

    public FootballTable getTable(Long competitionId) {
        String url = "https://api.football-data.org/v2/competitions/" + competitionId + "/standings";
        HttpEntity entity = createEntity();
        ResponseEntity<FootballTable> response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, entity, FootballTable.class);
        } catch (HttpClientErrorException e) {
            if (e.getRawStatusCode() == 403) {
                throw new BetWinnerException(BetWinnerException.ERR_FOOTBALL_MATCH_WE_DONT_HAVE_ACCESS_EXCEPTION);
            } else {
                throw new BetWinnerException(BetWinnerException.ERR_FOOTBALL_SOMETHING_WENT_WRONG_EXCEPTION);
            }
        }
        return response.getBody();
    }

    public FootballMatchList getMatchesInRound(Integer round, Long competitionId) {
        String url = "https://api.football-data.org/v2/competitions/" + competitionId + "/matches?matchday=" + round;
        HttpEntity entity = createEntity();
        ResponseEntity<FootballMatchList> response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, entity, FootballMatchList.class);
        } catch (HttpClientErrorException e) {
            if (e.getRawStatusCode() == 400) {
                throw new BetWinnerException(BetWinnerException.ERR_FOOTBALL_MATCH_NOT_FOUND_EXCEPTION);
            } else if (e.getRawStatusCode() == 403) {
                throw new BetWinnerException(BetWinnerException.ERR_FOOTBALL_MATCH_WE_DONT_HAVE_ACCESS_EXCEPTION);
            } else {
                throw new BetWinnerException(BetWinnerException.ERR_FOOTBALL_SOMETHING_WENT_WRONG_EXCEPTION);
            }
        }
        return response.getBody();
    }

    private HttpEntity createEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(tokenName, token);
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }
}
