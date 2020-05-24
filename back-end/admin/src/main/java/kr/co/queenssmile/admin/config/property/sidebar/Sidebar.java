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
@ConfigurationProperties(prefix = "admin-sidebar")
public class Sidebar implements java.io.Serializable {

  private static final long serialVersionUID = -87580138669014371L;

  List<Section> sections;
}
