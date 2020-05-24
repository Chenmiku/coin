package kr.co.queenssmile.core.dataloader;

import com.google.common.collect.Lists;
import kr.co.queenssmile.core.domain.International.InternationalMode;
import kr.co.queenssmile.core.domain.setting.AppSetting;
import kr.co.queenssmile.core.domain.setting.AppSettingRepository;
import kr.co.queenssmile.core.service.coin.CoinService;
import kr.co.queenssmile.core.utils.LocaleUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 초기 환결 데이터 값 설정
 */
@Slf4j
@Component
@Order(0) // 낮을수록 먼저
public class SettingDataLoader implements CommandLineRunner {

    @Value("${app.international.mode}")
    private boolean internationalMode;

    @Value("${app.international.locale}")
    private String internationalLocale;

    @Autowired
    private AppSettingRepository appSettingRepository;

    @Autowired
    private CoinService coinService;

    @Override
    public void run(String... args) {

        // init
        if (Lists.newArrayList(appSettingRepository.findAll()).size() == 0) {
            AppSetting setting = new AppSetting();
            setting.setInternational(internationalMode);
            setting.setDefaultLocale(LocaleUtils.toLocale(internationalLocale));

            InternationalMode im = new InternationalMode();
            im.setKoKr(true);
            im.setZhCn(true);
            im.setZhTw(true);
            im.setJaJp(true);
            im.setEnUs(true);
            setting.setInternationalMode(im);
            appSettingRepository.save(setting);
        }

        coinService.getCoinPrice();
    }
}
