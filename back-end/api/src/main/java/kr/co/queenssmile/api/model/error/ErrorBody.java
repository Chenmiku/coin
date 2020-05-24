package kr.co.queenssmile.api.model.error;

import kr.co.queenssmile.core.model.BaseResponseBody;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorBody extends BaseResponseBody {
  private static final long serialVersionUID = -9002551609797123735L;

  private String code;
  private String message;
}
