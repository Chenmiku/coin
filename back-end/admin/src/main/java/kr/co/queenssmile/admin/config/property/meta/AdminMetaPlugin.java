package kr.co.queenssmile.admin.config.property.meta;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = "admin.plugin")
public class AdminMetaPlugin implements java.io.Serializable {

  private static final long serialVersionUID = -2078082333217913598L;

  private String froalaId;
}
