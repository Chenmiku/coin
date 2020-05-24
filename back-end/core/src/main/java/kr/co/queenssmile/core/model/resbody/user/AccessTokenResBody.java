package kr.co.queenssmile.core.model.resbody.user;

import kr.co.queenssmile.core.model.BaseResponseBody;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.core.Relation;

@Relation(value = "accessToken")
@Slf4j
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessTokenResBody extends BaseResponseBody {

  private static final long serialVersionUID = -4699324395618118353L;

  private AccessTokenHeaderResBody header;
  private String url;
  private String grantType;
  private String username;
  private String password;

  private String image;
}
