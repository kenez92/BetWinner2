package com.kenez92.betwinner.controller.scheduler;

import com.kenez92.betwinner.domain.scheduler.Mail;
import com.kenez92.betwinner.service.scheduler.SimpleEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class SendMailScheduler {
    private final SimpleEmailService simpleEmailService;

    //@Scheduled(fixedDelay = 10000)
    public void sendMails() {
        List<Mail> mails = simpleEmailService.prepareMails();
        for (Mail mail : mails) {
            simpleEmailService.send(mail);
        }
    }
}
