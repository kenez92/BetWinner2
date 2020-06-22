package com.kenez92.betwinner.repository;

import com.kenez92.betwinner.domain.Competition;
import com.kenez92.betwinner.domain.CompetitionTable;
import com.kenez92.betwinner.domain.CompetitionTableElement;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompetitionElementRepositoryTestSuite {
    @Autowired
    private CompetitionTableRepository competitionTableRepository;
    @Autowired
    private CompetitionTableElementRepository competitionTableElementRepository;
    @Autowired
    private CompetitionRepository competitionRepository;

    @Test
    public void testSaveCompetitionTable() {
        //Given
        CompetitionTableElement competitionTableElement = CompetitionTableElement.builder()
                .name("Bayern")
                .position(1)
                .build();


        List<CompetitionTableElement> competitionTableElements = new ArrayList<>();
        competitionTableElements.add(competitionTableElement);

        CompetitionTable competitionTable = CompetitionTable.builder()
                .type("Regular")
                .build();
        competitionTable.setCompetitionTableElements(competitionTableElements);

        competitionTableElement.setCompetitionTable(competitionTable);

        //when
        competitionTableRepository.save(competitionTable);
        competitionTableElementRepository.save(competitionTableElement);
    }
}
