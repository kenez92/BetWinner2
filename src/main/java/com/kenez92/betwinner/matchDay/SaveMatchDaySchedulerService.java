package com.kenez92.betwinner.matchDay;

import com.kenez92.betwinner.exception.BetWinnerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class SaveMatchDaySchedulerService {
    private final MatchDayRepository matchDayRepository;

    public MatchDay process(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (matchDayRepository.existsByLocalDate(localDate)) {
            return matchDayRepository.findByLocalDate(localDate).orElseThrow(()
                    -> new BetWinnerException(BetWinnerException.ERR_MATCH_DAY_NOT_FOUND_EXCEPTION));
        } else {
            return matchDayRepository.save(MatchDay.builder().localDate(localDate).build());
        }
    }
}
