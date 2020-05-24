package kr.co.queenssmile.core.utils;

import java.util.regex.Pattern;

public class RegExUtils {

  public static String KOREAN = "[ㄱ-|5가-힣]+";
  public static String POSTAL_CODE = "[1-7]\\d{2}-\\d{3}";
  public static String DOMESTIC_PHONE_NUMBER = "0[2-6]{1,2}(\\)|-)?[2-9]\\d{3,4}-?\\d{4}";
  public static String DOMESTIC_MOBILE = "01(0|1|6|7|8|9)-?\\d{3,4}-?\\d{4}";
  public static String INTERNATIONAL_PHONE_NUMBER = "\\+[1-9](?:[- ]?\\d){7,14}";
  public static String EMAIL = "^[\\w-.%]+@([\\w-]{2,63}\\.)+[a-zA-Z]{2,4}$";
  public static String IP = "((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
  public static String URL = "^https?://([\\w-]+.)+(/[\\w-./?&%=]*)?$";
  public static String CREDIT_CARD = "^\\d{4}-?\\d{4}-?\\d{4}-?\\d{4}$";
  public static String CREDIT_CARD_MASTERCARD = "^5\\d{3}-?\\d{4}-?\\d{4}-?\\d{4}$";
  public static String CREDIT_CARD_VISA = "^4\\d{3}-?\\d{4}-?\\d{4}-?\\d{4}$";
  public static String CREDIT_CARD_DOMESTIC = "^9\\d{3}-?\\d{4}-?\\d{4}-?\\d{4}$";
  public static String CREDIT_CARD_AMERICAN_EXPRESS = "^3\\d{3}-?\\d{4}-?\\d{4}-?\\d{4}$";


  public static boolean isMatcher(String pattern, String value) {
    if (StringUtils.isEmpty(pattern) || StringUtils.isEmpty(value)) {
      throw new NullPointerException();
    }
    return Pattern.compile(pattern).matcher(value).matches();
  }

}
