package kr.co.queenssmile.core.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import kr.co.queenssmile.core.model.locale.PhoneNumber;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class PhoneNumberUtils {

  public static List<PhoneNumber> phoneNumbers() {
    PhoneNumberUtil instance = PhoneNumberUtil.getInstance();
    Set<String> supportedRegions = instance.getSupportedRegions();
    return supportedRegions.stream()
        .map(region -> {

          return PhoneNumber.builder()
              .code(instance.getCountryCodeForRegion(region))
              .displayName(LocaleUtils.generateByCountryCode(region).getDisplayName(Locale.US) + " (" + LocaleUtils.generateByCountryCode(region).getDisplayName() + ")")
              .build();
        })
        .sorted(Comparator.comparing(PhoneNumber::getDisplayName))
        .collect(Collectors.toList());
  }

  public static int phoneNumber(String countryCode) {
    AtomicInteger phoneNumber = new AtomicInteger(0);
    phoneNumbers().forEach(ph -> {
      LocaleUtils.countries().forEach(country -> {
        if (country.getCode().equals(countryCode) &&
            country.getDisplayName().equals(ph.getDisplayName())) {
          phoneNumber.set(ph.getCode());
        }
      });
    });
    return phoneNumber.get();
  }

  // 국제전화번호로 변환
  public static String convertByFormat(String countryCode, String mobile, PhoneNumberUtil.PhoneNumberFormat numberFormat) {
    if (StringUtils.isEmpty(countryCode) || StringUtils.isEmpty(mobile)) {
      return null;
    }

    try {
      int code = extractCountryCode(countryCode);

      PhoneNumberUtil numberUtil = PhoneNumberUtil.getInstance();

      String regionCode = numberUtil.getRegionCodeForCountryCode(code);

      Phonenumber.PhoneNumber number = numberUtil.parse(mobile, regionCode);

      return numberUtil.format(number, numberFormat);

    } catch (NumberParseException e) {
      throw new RuntimeException(e);
    }
  }

  // 국제전화번호로 변환 (E164) -> +821095501666
  public static String convertByFormatE164(String countryCode, String mobile) {
    return convertByFormat(countryCode, mobile, PhoneNumberUtil.PhoneNumberFormat.E164);
  }

  // 국제전화번호로 변환 (INTERNATIONAL) -> +82 10-9550-1666
  public static String convertByFormatINTERNATIONAL(String countryCode, String mobile) {
    return convertByFormat(countryCode, mobile, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
  }

  // 국제전화번호로 변환 (NATIONAL) -> 01095501666
  public static String convertByFormatNATIONAL(String countryCode, String mobile) {
    return convertByFormat(countryCode, mobile, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
  }

  // 국제전화번호로 변환 (RFC3966) -> tel:+82-10-9550-1666
  public static String convertByFormatRFC3966(String countryCode, String mobile) {
    return convertByFormat(countryCode, mobile, PhoneNumberUtil.PhoneNumberFormat.RFC3966);
  }

  // 지역번호가 포함된 국가코드에서 순수 국가코드만 추출하기 ex) 1-671 -> 1
  public static int extractCountryCode(String codeStr) {
    if (codeStr.contains("-")) {
      String str = codeStr.substring(0, codeStr.indexOf("-"));
      return Integer.valueOf(str);
    }
    return Integer.valueOf(codeStr);
  }
}
