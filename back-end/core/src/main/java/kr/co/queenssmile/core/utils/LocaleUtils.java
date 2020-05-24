package kr.co.queenssmile.core.utils;

import com.google.common.collect.Lists;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import kr.co.queenssmile.core.domain.International.AbstractInternational;
import kr.co.queenssmile.core.model.locale.Country;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
public class LocaleUtils {

  /**
   * 국가코드를 이용하여 Locale 생성하기
   *
   * @param countryCode 국가코드
   * @return Locale
   */
  public static Locale generateByCountryCode(String countryCode) {
    return new Locale("", countryCode);
  }

  public static List<Country> countries() {
    List<String> countries = Lists.newArrayList(Locale.getISOCountries());
    return countries.stream()
        .map(LocaleUtils::generateByCountryCode)
        .map(locale -> {
          Country country = new Country();
          country.setCode(locale.getCountry());
          country.setDisplayName(locale.getDisplayName(Locale.US) + " (" + locale.getDisplayName() + ")");
          return country;
        })
        .sorted(Comparator.comparing(Country::getDisplayName))
        .collect(Collectors.toList());
  }

  public static List<Locale> locales() {
    return Lists.newArrayList(Locale.getAvailableLocales());
  }

  public static List<Locale> defaultLocales() {

    List<Locale> locales = new ArrayList<>();
    locales.add(Locale.KOREA);
    locales.add(Locale.US);
    locales.add(Locale.CHINA);
    locales.add(Locale.TAIWAN);
    locales.add(Locale.JAPAN);
    return locales;
  }

  public static Locale init() {
    return new Locale("ko", "KR");
  }

  public static String locale(HttpServletRequest request) {
    String lang = request.getHeader("Accept-Language");
    if (StringUtils.isEmpty(lang)) {
      lang = "en-us";
    }
    return lang;
  }

  public static boolean isChinese(Locale locale) {
    return locale.getLanguage().equals("zh");
  }

  public static boolean isSimplifiedChinese(Locale locale) {
    return locale.equals(Locale.SIMPLIFIED_CHINESE)
        || locale.equals(Locale.CHINESE)
        || locale.equals(Locale.CHINA)
        || locale.equals(Locale.PRC);
  }

  public static boolean isTraditionalChinese(Locale locale) {
    return locale.equals(Locale.TRADITIONAL_CHINESE)
        || locale.equals(Locale.TAIWAN);
  }

  public static boolean isEnglish(Locale locale) {
    return locale.getLanguage().equals("en");
  }

  public static boolean isKorean(Locale locale) {
    return locale.getLanguage().equals("ko");
  }

  public static boolean isJapanese(Locale locale) {
    return locale.getLanguage().equals("ja");
  }

  public static String toValue(AbstractInternational ai) {

    if (ai.getLocale() != null) {

      if (Locale.CHINA.equals(ai.getLocale())) {
        return ai.getTextZhCn();
      } else if (Locale.TAIWAN.equals(ai.getLocale())) {
        return ai.getTextZhTw();
      } else if (Locale.KOREA.equals(ai.getLocale())) {
        return ai.getTextKoKr();
      } else if (Locale.JAPAN.equals(ai.getLocale())) {
        return ai.getTextJaJp();
      } else if (Locale.US.equals(ai.getLocale())) {
        return ai.getTextEnUs();
      } else {
        return ai.getTextKoKr();
      }
    } else {
      return ai.getTextKoKr();
    }
  }

  public static String getFieldNameByCategory(Locale locale) {
    if (locale.equals(Locale.KOREA)) {
      return "name.textKoKr";
    } else if (locale.equals(Locale.US)) {
      return "name.textEnUs";
    } else if (locale.equals(Locale.CHINA)) {
      return "name.textZhCn";
    } else if (locale.equals(Locale.TAIWAN)) {
      return "name.textZhTw";
    } else if (locale.equals(Locale.JAPAN)) {
      return "name.textJaJp";
    }
    return null;
  }

  public static Locale toLocale(String str) {

    if (!StringUtils.isEmpty(str)) {

      if ("en-us".equals(str)) {
        return Locale.US;
      } else if ("ko-kr".equals(str)) {
        return Locale.KOREA;
      } else if ("zh-cn".equals(str)) {
        return Locale.CHINA;
      } else if ("zh-tw".equals(str)) {
        return Locale.TAIWAN;
      } else if ("ja-jp".equals(str)) {
        return Locale.JAPAN;
      } else {
        return Locale.KOREA;
      }
    } else {
      return Locale.KOREA;
    }
  }

  public static Locale getLocaleForCountryCode(Integer countryCode) {

    if (countryCode != null) {
      String region = PhoneNumberUtil.getInstance().getRegionCodeForCountryCode(countryCode);
      if (StringUtils.isNotEmpty(region)) {
        return new Locale("", region);
      }
    }
    return null;
  }

  public static Integer getCountryCodeForLocale(Locale locale) {

    log.debug("locale ::: {}", locale.toString());
    log.debug("locale.getCountry() ::: {}", locale.getCountry());
    if (locale != null) {
      if (StringUtils.isNotEmpty(new Locale("", locale.getCountry()))) {
        log.debug("locale.getCountry().toUpperCase() ::: {}", locale.getCountry());
        return PhoneNumberUtil.getInstance().getCountryCodeForRegion(locale.getCountry().toUpperCase());
      }
    }
    return null;
  }

}
