package kr.co.queenssmile.core.utils;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;

import java.util.UUID;

public class StringUtils {
  public static boolean isEmpty(Object str) {
    return str == null || "".equals(str);
  }

  public static boolean isNotEmpty(Object str) {
    return !isEmpty(str);
  }

  public static boolean hasLength(CharSequence str) {
    return str != null && str.length() > 0;
  }

  public static boolean hasLength(String str) {
    return hasLength((CharSequence) str);
  }

  public static boolean hasText(CharSequence str) {
    if (!hasLength(str)) {
      return false;
    } else {
      int strLen = str.length();

      for (int i = 0; i < strLen; ++i) {
        if (!Character.isWhitespace(str.charAt(i))) {
          return true;
        }
      }

      return false;
    }
  }

  public static boolean hasText(String str) {
    return hasText((CharSequence) str);
  }

  public static String toLanguageTag(java.util.Locale locale) {
    return locale.getLanguage() + (hasText(locale.getCountry()) ? "-" + locale.getCountry() : "");
  }

  public static String getRandomStr() {
    return UUID.randomUUID().toString().replace("-", "");
  }

  public static String[] toStringArray(String str, String regex) {
    return str.split(regex);
  }

  public static String generateRandomString(int length) {
    RandomStringGenerator randomStringGenerator =
        new RandomStringGenerator.Builder()
            .withinRange('0', 'Z')
            .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
            .build();
    return randomStringGenerator.generate(length).toUpperCase();
  }

  public static String convertNameToXXX(String name) {

    if (name == null) {
      return null;
    }

    int length = name.length() - 1;

    String result = "";

    for (int i = 0; i < length; i++) {
      result += "*";
    }

    return name.substring(0, 1) + result;
  }

  public static String busiNoBar(String number) {
    if (StringUtils.isNotEmpty(number) && number.length() == 10) {
      return number.replaceAll("(\\d{3})(\\d{2})(\\d{5})", "$1-$2-$3");
    }
    return number;
  }

}
