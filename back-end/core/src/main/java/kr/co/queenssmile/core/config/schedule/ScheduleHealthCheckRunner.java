package kr.co.queenssmile.core.config.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

@Slf4j
@Component
@Order(11)
public class ScheduleHealthCheckRunner implements CommandLineRunner {

  @Autowired
  private DataSource dataSource;

  @Override
  public void run(String... args) throws Exception {
  }

  @Async
  @Scheduled(fixedDelay = 1000 * 10)
  public void scheduleTest() throws SQLException {
//    log.info("> DB Close ::: {}", dataSource.getConnection().isClosed());
//    log.info(LocalDateTime.now().toString());
  }

//  // 매일 오전 11시
//  @org.springframework.scheduling.annotation.Async
//  @Scheduled(cron = "0 0 8 * * ?")
//  public void exchange8() {
//    log.debug("exchange1");
//    exchangeService.saveExchange();
//  }
//
//  @org.springframework.scheduling.annotation.Async
//  @Scheduled(cron = "0 0 11 * * ?")
//  public void exchange11() {
//    log.debug("exchange1");
//    exchangeService.saveExchange();
//  }
//
//  // 매일 오전 3시
//  @Async
//  @Scheduled(cron = "0 0 15 * * ?")
//  public void exchange15() {
//    log.debug("exchange2");
//    exchangeService.saveExchange();
//  }
//
//  // 매일 오전 6시
//  @Async
//  @Scheduled(cron = "0 0 18 * * ?")
//  public void exchange18() {
//    log.debug("exchange3");
//    exchangeService.saveExchange();
//  }


}
