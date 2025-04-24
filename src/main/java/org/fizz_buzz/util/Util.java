package org.fizz_buzz.util;

import org.fizz_buzz.repository.SessionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

@Component
public class Util {

    SessionRepository sessionRepository;

    public Util(SessionRepository sessionRepository) {

        this.sessionRepository = sessionRepository;
    }

    @Scheduled(cron = "@daily")
    @Transactional
    public void deleteOldSessions(){

        sessionRepository.deleteByExpiresAtBefore(Date.from(Instant.now()));
    }

}
