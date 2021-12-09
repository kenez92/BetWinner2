package com.kenez92.betwinner.mail;

import com.kenez92.betwinner.match.MatchDto;
import com.kenez92.betwinner.matchDay.MatchDayService;
import com.kenez92.betwinner.users.User;
import com.kenez92.betwinner.users.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.kenez92.betwinner.config.StrategyConfiguration.AGGRESSIVE_STRATEGY_FROM;
import static com.kenez92.betwinner.config.StrategyConfiguration.AGGRESSIVE_STRATEGY_TO;
import static com.kenez92.betwinner.config.StrategyConfiguration.DEFENSIVE_STRATEGY_FROM;
import static com.kenez92.betwinner.config.StrategyConfiguration.DEFENSIVE_STRATEGY_TO;
import static com.kenez92.betwinner.config.StrategyConfiguration.EVERYTHING_STRATEGY_FROM;
import static com.kenez92.betwinner.config.StrategyConfiguration.EVERYTHING_STRATEGY_TO;
import static com.kenez92.betwinner.config.StrategyConfiguration.NORMAL_STRATEGY_FROM;
import static com.kenez92.betwinner.config.StrategyConfiguration.NORMAL_STRATEGY_TO;
import static com.kenez92.betwinner.config.StrategyConfiguration.PERCENT_70_STRATEGY_FROM;
import static com.kenez92.betwinner.config.StrategyConfiguration.PERCENT_70_STRATEGY_TO;

@Service
@Slf4j
@RequiredArgsConstructor
public class SimpleEmailService {
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final MatchDayService matchDayService;


    public void send(final Mail mail) {
        try {
            SimpleMailMessage mailMessage = createMailMessage(mail);
            javaMailSender.send(mailMessage);
            log.info("Email has been sent.");
        } catch (MailException e) {
            log.error("Failed to process email sending: {}{}", e.getMessage(), e);
        }
    }

    public List<Mail> prepareMails() {
        List<User> users = userRepository.usersForSubscription();
        List<Mail> mails = new ArrayList<>();
        List<MatchDto> normalStrategy = new ArrayList<>();
        List<MatchDto> defensiveStrategy = new ArrayList<>();
        List<MatchDto> aggressiveStrategy = new ArrayList<>();
        List<MatchDto> percent70Strategy = new ArrayList<>();
        List<MatchDto> everythingStrategy = new ArrayList<>();
        List<MatchDto> matchDtoList = matchDayService.getByLocalDate(LocalDate.now()).getMatchesList();
        for (MatchDto matchDto : matchDtoList) {
            try {
                double homeTeamChance = matchDto.getMatchStats().getHomeTeamChance();
                double awayTeamChance = matchDto.getMatchStats().getAwayTeamChance();
                if ((homeTeamChance >= EVERYTHING_STRATEGY_FROM && homeTeamChance <= EVERYTHING_STRATEGY_TO) ||
                        (awayTeamChance >= EVERYTHING_STRATEGY_FROM && awayTeamChance <= EVERYTHING_STRATEGY_TO)) {
                    everythingStrategy.add(matchDto);
                }
                if ((homeTeamChance >= DEFENSIVE_STRATEGY_FROM && homeTeamChance <= DEFENSIVE_STRATEGY_TO) ||
                        (awayTeamChance >= DEFENSIVE_STRATEGY_FROM && awayTeamChance <= DEFENSIVE_STRATEGY_TO)) {
                    defensiveStrategy.add(matchDto);
                }
                if ((homeTeamChance >= NORMAL_STRATEGY_FROM && homeTeamChance <= NORMAL_STRATEGY_TO) ||
                        (awayTeamChance >= NORMAL_STRATEGY_FROM && awayTeamChance <= NORMAL_STRATEGY_TO)) {
                    normalStrategy.add(matchDto);
                }
                if ((homeTeamChance >= AGGRESSIVE_STRATEGY_FROM && homeTeamChance <= AGGRESSIVE_STRATEGY_TO) ||
                        (awayTeamChance >= AGGRESSIVE_STRATEGY_FROM && awayTeamChance <= AGGRESSIVE_STRATEGY_TO)) {
                    aggressiveStrategy.add(matchDto);
                }
                if ((homeTeamChance >= PERCENT_70_STRATEGY_FROM && homeTeamChance <= PERCENT_70_STRATEGY_TO) ||
                        (awayTeamChance >= PERCENT_70_STRATEGY_FROM && awayTeamChance <= PERCENT_70_STRATEGY_TO)) {
                    percent70Strategy.add(matchDto);
                }
            } catch (Exception ex) {
                log.error("Error while getting team chances: " + matchDto);
            }
        }
        for (User user : users) {
            List<MatchDto> predictMatches = new ArrayList<>();
            switch (user.getUserStrategy()) {
                case NORMAL_STRATEGY:
                    predictMatches = normalStrategy;
                    break;
                case DEFENSIVE_STRATEGY:
                    predictMatches = defensiveStrategy;
                    break;
                case AGGRESSIVE_STRATEGY:
                    predictMatches = aggressiveStrategy;
                    break;
                case EVERYTHING_STRATEGY:
                    predictMatches = everythingStrategy;
                    break;
                case PERCENT_70_STRATEGY:
                    predictMatches = percent70Strategy;
            }
            String message = "";
            for (MatchDto matchDto : predictMatches) {
                message = message + matchDto.getHomeTeam() + " vs " + matchDto.getAwayTeam() + " courses / chance to win : "
                        + matchDto.getMatchStats().getHomeTeamCourse() + " - " + matchDto.getMatchStats().getHomeTeamChance() + "% | "
                        + matchDto.getMatchStats().getAwayTeamCourse() + " - " + matchDto.getMatchStats().getAwayTeamChance() + "%  date: "
                        + matchDto.getDate() + "\n";
            }
            mails.add(new Mail(user.getEmail(), "Your matches",
                    "Your matches for today with " + user.getUserStrategy().toString() + "\n" + message));
        }
        return mails;
    }

    private SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        return mailMessage;
    }
}
