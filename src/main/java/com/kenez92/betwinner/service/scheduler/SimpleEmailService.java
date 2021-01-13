package com.kenez92.betwinner.service.scheduler;

import com.kenez92.betwinner.common.enums.UserStrategy;
import com.kenez92.betwinner.domain.matches.MatchDto;
import com.kenez92.betwinner.domain.scheduler.Mail;
import com.kenez92.betwinner.persistence.entity.User;
import com.kenez92.betwinner.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SimpleEmailService {
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;

    public void send(final Mail mail) {
        try {
            SimpleMailMessage mailMessage = createMailMessage(mail);
            javaMailSender.send(mailMessage);
            log.info("Email has been sent.");
        } catch (MailException e) {
            log.error("Failed to process email sending: {}{}", e.getMessage(), e);
        }
    }

    private SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        return mailMessage;
    }

    public List<Mail> prepareMails() {
        List<User> users = userRepository.usersForSubscription();
        List<Mail> mails = new ArrayList<>();
        List<MatchDto> normalStrategy = preparePredictMatches(UserStrategy.EVERYTHING_STRATEGY);
        List<MatchDto> defensiveStrategy = preparePredictMatches(UserStrategy.DEFENSIVE_STRATEGY);
        List<MatchDto> aggressiveStrategy = preparePredictMatches(UserStrategy.AGGRESSIVE_STRATEGY);
        List<MatchDto> everythingStrategy = preparePredictMatches(UserStrategy.EVERYTHING_STRATEGY);
        for (User user : users) {
            List<MatchDto> predictMatches = new ArrayList<>();
            switch (user.getUserStrategy()) {
                case NORMAL_STRATEGY:
                    predictMatches = new ArrayList<>(normalStrategy);
                    break;
                case DEFENSIVE_STRATEGY:
                    predictMatches = new ArrayList<>(defensiveStrategy);
                    break;
                case AGGRESSIVE_STRATEGY:
                    predictMatches = new ArrayList<>(aggressiveStrategy);
                    break;
                case EVERYTHING_STRATEGY:
                    predictMatches = new ArrayList<>(everythingStrategy);
            }
            String message = "";
            for (MatchDto matchDto : predictMatches) {
                message = message + matchDto.getHomeTeam() + " vs " + matchDto.getAwayTeam() + " courses / chance to win : "
                        + matchDto.getMatchStats().getHomeTeamCourse() + " - " + matchDto.getMatchStats().getHomeTeamChance() + "% | "
                        + matchDto.getMatchStats().getAwayTeamCourse() + " - " + matchDto.getMatchStats().getAwayTeamChance() + "%  date: "
                        + matchDto.getDate() + "\n";
            }
            mails.add(new Mail(user.getEmail(), "Your matches",
                    "Your matches for today : \n" + message));
        }
        return mails;
    }

    private List<MatchDto> preparePredictMatches(UserStrategy userStrategy) {
        return new ArrayList<>();
    }
}
