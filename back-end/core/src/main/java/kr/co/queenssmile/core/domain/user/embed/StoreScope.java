package kr.co.queenssmile.core.domain.user.embed;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import java.io.Serializable;

@Slf4j
@Setter
@Getter
@ToString
@Builder
public class StoreScope implements Serializable {
  private static final long serialVersionUID = 970108922294769965L;

  @Column(columnDefinition = "BIT(1) default 0")
  private boolean invite; // 초대권한

  @Column(columnDefinition = "BIT(1) default 0")
  private boolean addStore; // 매장 추가 권한
}
