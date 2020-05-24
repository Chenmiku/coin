package kr.co.queenssmile.core.service.coin;

import com.webcerebrium.binance.api.BinanceApiException;
import kr.co.queenssmile.core.domain.user.Authority;

import java.util.List;

public interface CoinService {
    void getCoinPrice();
}
