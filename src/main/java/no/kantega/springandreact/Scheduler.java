package no.kantega.springandreact;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class Scheduler{

    //@Scheduled(cron = "0 0 6 * * 0")
    @Scheduled(cron = "0 0 6 * * 0", zone="CST")
    public void executeSundayAt6() {
       // System.out.println("this is working");
        SpringAndReactApplication.clearAirDatabase();
    }


    @Scheduled(cron = "0 30 6 * * 0", zone="CST")
    public void executeSundayAt630() {
        SpringAndReactApplication.clearWaterDatabase();
    }
}