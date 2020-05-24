package kr.co.queenssmile.core.config.database;

import kr.co.queenssmile.core.config.property.ProcessEnv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PwdEncConfig {

  @Value("${app.password.encoder}")
  private Integer passEncoder;

  public PasswordEncoder getPasswordEncoder() {

    if (passEncoder == null) {
      throw new RuntimeException("지정된 비밀번호 암호 인코더가 없습니다.");
    }

    if (passEncoder.intValue() == ProcessEnv.PASS_ENCODER_BCRYPT) {
      return new BCryptPasswordEncoder();
    } else if (passEncoder.intValue() == ProcessEnv.PASS_ENCODER_MYSQL) {
      return new MySqlPasswordEncoder();
    } else if (passEncoder.intValue() == ProcessEnv.PASS_ENCODER_SHA256) {
      return new StandardPasswordEncoder(); // 요구사항 SHA-256 알고리즘
    } else {
      throw new RuntimeException("지정된 비밀번호 암호 인코더가 없습니다.");
    }
  }
}
