package kr.co.queenssmile.core.service.coin;

import com.webcerebrium.binance.api.BinanceApi;
import com.webcerebrium.binance.api.BinanceApiException;
import com.webcerebrium.binance.datatype.BinanceEventKline;
import com.webcerebrium.binance.datatype.BinanceInterval;
import com.webcerebrium.binance.datatype.BinanceSymbol;
import com.webcerebrium.binance.websocket.BinanceWebSocketAdapterKline;
import kr.co.queenssmile.core.domain.user.Authority;
import kr.co.queenssmile.core.domain.user.AuthorityRepository;
import kr.co.queenssmile.core.service.user.AuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.websocket.api.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CoinServiceImpl implements CoinService {

//    @Value("${binance.endpoint}")
//    private String endPoint;
//
//    @Value("${binance.key.apikey}")
//    private String apiKey;
//
//    @Value("${binance.key.secretkey}")
//    private String secretKey;

//    private final String endPoint = "https://api.binance.com";

    @Override
    @Transactional
    public void getCoinPrice() {
//        final String uri = endPoint + "/api/v3/ticker/24hr?symbol=ETHBTC";
//        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.getForObject(uri, String.class);
//
//        System.out.println(result);

        try {
            BinanceApi api = new BinanceApi();
            System.out.println("ETH-BTC PRICE=" + api.pricesMap().get("BTCUSDT"));
        } catch (BinanceApiException e) {
            System.out.println( "ERROR: " + e.getMessage());
        }

//        BinanceSymbol symbol = new BinanceSymbol("ETHBTC");
//        BinanceInterval interval = BinanceInterval.FIVE_MIN;
//        Session session = (new BinanceApi()).websocketKlines(symbol, interval, new BinanceWebSocketAdapterKline() {
//            @Override
//            public void onMessage(BinanceEventKline message) {
//                System.out.println(message.toString());
//            }
//        });
//        try { Thread.sleep(15000); } catch (InterruptedException e) {}
//        session.close();
    }
}
