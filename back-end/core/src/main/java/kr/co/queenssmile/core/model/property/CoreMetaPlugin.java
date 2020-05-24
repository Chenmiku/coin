package kr.co.queenssmile.core.model.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = "core.plugin")
public class CoreMetaPlugin implements java.io.Serializable {

  private static final long serialVersionUID = -2078082333217913598L;

  private String gaTrackingId;
  private String channelId;
  private String jusoKey;
  private String googleMap;
  private String googlePlace;
  private String googleReCaptchaSecret;
  private String exchangeId;
}
