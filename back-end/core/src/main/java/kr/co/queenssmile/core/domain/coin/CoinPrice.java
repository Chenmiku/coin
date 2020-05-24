package kr.co.queenssmile.core.domain.coin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.queenssmile.core.domain.AbstractEntityInternational;
import kr.co.queenssmile.core.domain.International.InterText;
import kr.co.queenssmile.core.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Entity
@Getter
@Setter
@ToString
public class CoinPrice extends AbstractEntityInternational<Long> {

  private static final long serialVersionUID = 2538945088285644707L;
  public static String[] IGNORE_PROPERTIES = {
          "id"
  };

  @Id
  @Column(unique = true)
  @GeneratedValue
  private Long id;

  private String symbol;

  private BigDecimal price;

  private LocalDateTime timeCheckPrice;

  @Column(columnDefinition = "BIT(1) default 1")
  private boolean active;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "idCoin", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_Coin_For_CoinPrice"))
  private Coin relativeCoin;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "idCoinMarket", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_CoinMarket_For_CoinPrice"))
  private Coin relativeCoinMarket;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "idCurrency", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_Currency_For_CoinPrice"))
  private Currency relativeCurrency;

  @Override
  public void delete() {

  }

  @Override
  public void lazy() {

  }
}
