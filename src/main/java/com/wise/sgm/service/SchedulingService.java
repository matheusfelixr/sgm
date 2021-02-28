package com.wise.sgm.service;

import com.wise.sgm.controller.MaillingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulingService.class);


    @Autowired
    private MaillingService maillingService;

    /**
     * Libera Millings que estão travados por conta que um usuario pegou ele e não atendeu.
     */
    @Scheduled(cron = " 59 59 * * * *")
    public void releasesMillings() {
        LOGGER.info("[Scheduling] Inicio processo para liberar millings travados [Scheduling]");
        try {
            this.maillingService.releasesMillings();
        } catch (Exception e) {
            LOGGER.error("[Scheduling] Erro no processo para liberar millings travados [Scheduling]");
            e.printStackTrace();
        }
        LOGGER.info("[Scheduling] Finalizado processo para liberar millings travados [Scheduling]");

    }
}
