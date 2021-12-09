package com.kenez92.betwinner.controller.scheduler;

import com.kenez92.betwinner.mail.Mail;
import com.kenez92.betwinner.mail.SimpleEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class SendMailScheduler {
    private final SimpleEmailService simpleEmailService;

    @Scheduled(cron = "0 0 8 * * *")
    public void sendMails() {
        List<Mail> mails = simpleEmailService.prepareMails();
        for (Mail mail : mails) {
            simpleEmailService.send(mail);
        }
    }
}
