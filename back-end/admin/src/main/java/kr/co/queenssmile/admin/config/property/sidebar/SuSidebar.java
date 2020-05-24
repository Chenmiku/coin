package kr.co.queenssmile.admin.config.property.sidebar;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix = "super-sidebar")
public class SuSidebar implements java.io.Serializable {

  private static final long serialVersionUID = -4043579355153889762L;

  private List<Section> sections;
}
