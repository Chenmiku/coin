package kr.co.queenssmile.core.model.resbody.user;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;

@Relation(value = "header", collectionRelation = "headers")
@Slf4j
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessTokenHeaderResBody implements Serializable {
  private static final long serialVersionUID = -1699324195618118353L;

  private String authorization;
  private String contentType;
}
