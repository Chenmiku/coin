package kr.co.queenssmile.core.config.property;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ProcessEnv {

  public static final Integer PASS_ENCODER_BCRYPT = 1;
  public static final Integer PASS_ENCODER_MYSQL = 2;
  public static final Integer PASS_ENCODER_SHA256 = 3;

  @Value("${process.env}")
  private String processEnv;

  @Value("${spring.jpa.hibernate.ddl-auto}")
  private String ddlAuto;

  public Boolean isLocal() {
    return StringUtils.isNotEmpty(processEnv) && processEnv.equals("local");
  }

  public Boolean isProduction() {
    return StringUtils.isNotEmpty(processEnv) && processEnv.equals("production");
  }

  public Boolean isDevelopment() {
    return StringUtils.isNotEmpty(processEnv) && processEnv.equals("development");
  }

}
