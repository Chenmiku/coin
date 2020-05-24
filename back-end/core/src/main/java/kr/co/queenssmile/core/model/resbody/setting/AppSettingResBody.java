package kr.co.queenssmile.core.model.resbody.setting;

import kr.co.queenssmile.core.domain.International.InternationalMode;
import kr.co.queenssmile.core.model.BaseResponseBody;
import lombok.*;
import org.springframework.hateoas.server.core.Relation;

import java.util.Locale;

@Relation(value = "product", collectionRelation = "products")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppSettingResBody extends BaseResponseBody {

  private static final long serialVersionUID = 2411225505181969676L;

  private Locale defaultLocale;
  private boolean international; // 국제화 모드
  private InternationalMode internationalMode;
}
