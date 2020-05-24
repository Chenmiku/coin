package kr.co.queenssmile.core.service.setting;

import com.google.common.collect.ImmutableMap;
import kr.co.queenssmile.core.domain.International.InternationalMode;
import kr.co.queenssmile.core.domain.setting.AppSetting;
import kr.co.queenssmile.core.domain.setting.AppSettingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@Transactional
public class AppSettingServiceImpl implements AppSettingService {

  @Autowired
  private AppSettingRepository settingRepository;

  @Override
  public AppSetting setting(AppSetting appSetting) {
    log.debug("## appSetting ::: {}", appSetting);
    if (appSetting.getId() == null) {
      return settingRepository.save(appSetting);
    } else {
      Optional<AppSetting> optAppSetting = settingRepository.findById(appSetting.getId());

      if (optAppSetting.isPresent()) {

        InternationalMode im = appSetting.getInternationalMode();
        if (im != null && appSetting.isInternational()) {
          final Locale defaultLocale = appSetting.getDefaultLocale();

          if (defaultLocale.equals(Locale.KOREA)) {
            if (!im.isKoKr()) {
              im.setKoKr(true);
            }
          } else if (defaultLocale.equals(Locale.US)) {
            if (!im.isEnUs()) {
              im.setEnUs(true);
            }
          } else if (defaultLocale.equals(Locale.CHINA)) {
            if (!im.isZhCn()) {
              im.setZhCn(true);
            }
          } else if (defaultLocale.equals(Locale.TAIWAN)) {
            if (!im.isZhTw()) {
              im.setZhTw(true);
            }
          } else if (defaultLocale.equals(Locale.JAPAN)) {
            if (!im.isJaJp()) {
              im.setJaJp(true);
            }
          }
        } else {
          im = new InternationalMode();
          appSetting.setInternationalMode(im);
        }

        AppSetting oriAppSetting = optAppSetting.get();
        BeanUtils.copyProperties(appSetting, oriAppSetting, AppSetting.IGNORE_PROPERTIES);
        return settingRepository.save(oriAppSetting);
      } else {
        return settingRepository.save(appSetting);
      }
    }
  }

  @Override
  public AppSetting getSetting() {
    Page<AppSetting> page = settingRepository.findAll(PageRequest.of(0, 1, Sort.Direction.DESC, "createdDate"));

    if (page.getContent().size() > 0) {
      return page.getContent().get(0);
    }

    AppSetting appSetting = new AppSetting();
    InternationalMode im = new InternationalMode();
    im.setKoKr(true);
    appSetting.setInternationalMode(im);
    return this.setting(appSetting);
  }

  @Override
  public InternationalMode getInternationalMode() {
    return this.getSetting().getInternationalMode();
  }

  @Override
  public Locale getDefaultLocale() {
    return this.getSetting().getDefaultLocale();
  }

  @Override
  public List<Map<String, String>> languages() {
    InternationalMode im = this.getInternationalMode();
    List<Map<String, String>> locales = new ArrayList<>();
    if (im.isKoKr()) {
      locales.add(ImmutableMap.of("code", "ko-KR", "name", "한국어"));
    }
    if (im.isEnUs()) {
      locales.add(ImmutableMap.of("code", "en-US", "name", "English"));
    }
    if (im.isZhCn()) {
      locales.add(ImmutableMap.of("code", "zh-CN", "name", "中国"));
    }
    if (im.isZhTw()) {
      locales.add(ImmutableMap.of("code", "zh-TW", "name", "中國傳統"));
    }
    if (im.isJaJp()) {
      locales.add(ImmutableMap.of("code", "ja-JP", "name", "日本"));
    }
    return locales;
  }
}
