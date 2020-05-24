package kr.co.queenssmile.core.service.setting;

import kr.co.queenssmile.core.domain.International.InternationalMode;
import kr.co.queenssmile.core.domain.setting.AppSetting;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface AppSettingService {

    AppSetting setting(AppSetting appSetting);

    AppSetting getSetting();
    InternationalMode getInternationalMode();

    Locale getDefaultLocale();
    List<Map<String, String>> languages();
}
