package kr.co.queenssmile.core.domain.coin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.queenssmile.core.domain.AbstractEntity;
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
public class Currency extends AbstractEntity<Long> {

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

  private String country;

  @Column(columnDefinition = "BIT(1) default 1")
  private boolean active;

  @Override
  public void delete() {

  }

  @Override
  public void lazy() {

  }
}
