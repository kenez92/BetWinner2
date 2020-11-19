package com.kenez92.betwinner.service.scheduler;

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
        for (User user : users) {
            List<MatchDto> matchDtoList = user.getUserStrategy().predictMatches();
            String message = "";
            for (MatchDto matchDto : matchDtoList) {
                message = message + matchDto.getHomeTeam() + " vs " + matchDto.getAwayTeam() + " courses / chance to win : "
                        + matchDto.getHomeTeamCourse() + " - " + matchDto.getHomeTeamChance() + "% | "
                        + matchDto.getAwayTeamCourse() + " - " + matchDto.getAwayTeamChance() + "%  date: "
                        + matchDto.getDate() + "\n";
            }
            mails.add(new Mail(user.getEmail(), "Your matches",
                    "Your matches for today : \n" + message));
        }
        return mails;
    }
}
