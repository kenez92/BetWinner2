package com.kenez92.betwinner.sheduler.football.data;

import com.kenez92.betwinner.domain.table.CompetitionDto;
import com.kenez92.betwinner.domain.table.CompetitionSeasonDto;
import com.kenez92.betwinner.service.competition.CurrentMatchDayService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
class SaveCompetitionSeasonTest {

    @Autowired
    private CurrentMatchDayService currentMatchDayService;

    @Test
    public void testTest() {

        CompetitionSeasonDto competitionSeasonDto = CompetitionSeasonDto.builder()
                .id(5L)
                .footballId(481L)
                .startDate(LocalDate.of(2019, 8, 9))
                .endDate(LocalDate.of(2020, 3, 8))
                .winner("Season still in progress")
                .competition(CompetitionDto.builder()
                        .id(43L)
                        .footballId(20403L)
                        .name("Eredivisie")
                        .competitionSeasonList(null)
                        .build())
                .currentMatchDayList(null)
                .build();

    }
}