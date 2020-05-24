package kr.co.queenssmile.core.domain.coin;

import kr.co.queenssmile.core.domain.AbstractEntity;
import kr.co.queenssmile.core.domain.AbstractEntityInternational;
import kr.co.queenssmile.core.domain.International.AbstractInternational;
import kr.co.queenssmile.core.domain.International.InterText;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Entity
@Getter
@Setter
@ToString
public class CoinMarket extends AbstractEntityInternational<Long> {

  private static final long serialVersionUID = 2538945088285644707L;
  public static String[] IGNORE_PROPERTIES = {
      "id"
  };

  @Id
  @Column(unique = true)
  @GeneratedValue
  private Long id;

  private String name;

  private String code;

  @Embedded
  @AttributeOverrides({
          @AttributeOverride(name = "textKoKr", column = @Column(name = "introduceKoKr", columnDefinition = "TEXT")),
          @AttributeOverride(name = "textEnUs", column = @Column(name = "introduceEnUs", columnDefinition = "TEXT")),
          @AttributeOverride(name = "textJaJp", column = @Column(name = "introduceJaJp", columnDefinition = "TEXT")),
          @AttributeOverride(name = "textZhCn", column = @Column(name = "introduceZhCn", columnDefinition = "TEXT")),
          @AttributeOverride(name = "textZhTw", column = @Column(name = "introduceZhTw", columnDefinition = "TEXT"))
  })
  private InterText introduce;

  @Column(columnDefinition = "BIT(1) default 1")
  private boolean active;

  @Override
  public void delete() {

  }

  @Override
  public void lazy() {

  }
}
