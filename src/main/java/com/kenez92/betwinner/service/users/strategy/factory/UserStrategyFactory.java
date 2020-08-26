package com.kenez92.betwinner.service.users.strategy.factory;

import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.service.users.strategy.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserStrategyFactory {
    public static final String EVERYTHING_STRATEGY = "EVERYTHINGSTRATEGY";
    public static final String AGGRESSIVE_STRATEGY = "AGGRESSIVESTRATEGY";
    public static final String DEFENSIVE_STRATEGY = "DEFENSIVESTRATEGY";
    public static final String NORMAL_STRATEGY = "NORMALSTRATEGY";
    private final AggressiveStrategy aggressiveStrategy;
    private final DefensiveStrategy defensiveStrategy;
    private final NormalStrategy normalStrategy;
    private final EverythingStrategy everythingStrategy;

    public UserStrategy factory(String strategy) {
        if (strategy == null) {
            return everythingStrategy;
        }
        switch (strategy) {
            case EVERYTHING_STRATEGY:
                return everythingStrategy;
            case AGGRESSIVE_STRATEGY:
                return aggressiveStrategy;
            case DEFENSIVE_STRATEGY:
                return defensiveStrategy;
            case NORMAL_STRATEGY:
                return normalStrategy;
            default:
                throw new BetWinnerException(BetWinnerException.ERR_SOMETHING_WENT_WRONG_EXCEPTION);
        }
    }
}
