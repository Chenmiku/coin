package kr.co.queenssmile.core.domain.user.sns;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = "naver.api")
public class NaverApiKey {

  private String clientId;
  private String clientSecret;

  private String accessTokenUri;
  private String meUri;
}
