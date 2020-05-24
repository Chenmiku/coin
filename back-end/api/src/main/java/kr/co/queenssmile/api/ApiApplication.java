package kr.co.queenssmile.api;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Enumeration;
import java.util.Properties;

@SpringBootApplication(scanBasePackages = {
    "kr.co.queenssmile.api",
    "kr.co.queenssmile.core"
})
@EnableJpaRepositories(basePackages = {"kr.co.queenssmile.core.domain"})
@EntityScan(basePackages = {"kr.co.queenssmile.core.domain"})
public class ApiApplication {

  public static void main(String[] args) {
    System.out.println("# ApiApplication #");
    String profiles = System.getProperty("spring.profiles.default");

    if (StringUtils.isEmpty(profiles)) {
      System.setProperty("spring.profiles.default", "api");
    }
    System.out.println("> spring.profiles.default ::: " + System.getProperty("spring.profiles.default"));

//        SpringApplication.run(Server.class, args);
    Properties p = System.getProperties();
    Enumeration keys = p.keys();

    System.out.println("--------------------");
    while (keys.hasMoreElements()) {
      String key = (String) keys.nextElement();
      String value = (String) p.get(key);
      System.out.println("# Property : [" + key + " = " + value + "]");
    }
    System.out.println("--------------------");

    SpringApplication.run(ApiApplication.class, args);
  }
}
