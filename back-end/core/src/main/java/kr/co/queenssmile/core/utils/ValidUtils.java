package kr.co.queenssmile.core.utils;

import kr.co.queenssmile.core.config.exception.ForbiddenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidUtils {

  public static final String PATTERN_USERNAME = "^[a-zA-Z]{1}[a-zA-Z0-9_]+$";
  public static final String PATTERN_FULLNAME = "^[가-힣a-zA-Z]+$";
  public static final String PATTERN_NICKNAME = "^[가-힣a-zA-Z0-9]+$";
  public static final String PATTERN_FILENAME = "^[가-힣a-zA-Z0-9!#.%&@?-_\\s]+$";
  public static final String PATTERN_KOREAN_ONLY = "^[가-힣]+$";
  public static final String PATTERN_KOREAN_INCLUDE = ".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*"; // 한글이 포함되면 true
  public static final String PATTERN_ENGLISH = "^[a-zA-Z]+$";
  public static final String PATTERN_NUMBER = "^[0-9]+$";
  public static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
  public static final String PATTERN_DATE_YYYYMMDDHHMMSS = "^([\\+-]?\\d{4}(?!\\d{2}\\b))((/?)((0[1-9]|1[0-2])(\\3([12]\\d|0[1-9]|3[01]))?|W([0-4]\\d|5[0-2])(/?[1-7])?|(00[1-9]|0[1-9]\\d|[12]\\d{2}|3([0-5]\\d|6[1-6])))([T\\s]((([01]\\d|2[0-3])((:?)[0-5]\\d)?|24\\:?00)([\\.,]\\d+(?!:))?)?(\\17[0-5]\\d([\\.,]\\d+)?)?([zZ]|([\\+-])([01]\\d|2[0-3]):?([0-5]\\d)?)?)?)?$";
  public static final String PATTERN_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=\\S+$).{8,}$";  //  8자 이상, 영문 대소문자, 숫자 한개 이상, 특수문자 한개 이상
  public static final String PATTERN_PASSWORD_NEW = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{6,}$";  //  6글자 이상, 영문, 숫자 한개 이상
  public static final String MESSAGE_PASSWORD_NEW = "비밀번호는 영문, 숫자 조합으로 6자리 이상 입력하여야 합니다.";
  public static final String PATTERN_PASSWORD_NUMBER = "^(?=.*[0-9])$"; // 적어도 한개의 숫자
  public static final String PATTERN_PASSWORD_LOWERCASE = "^(?=.*[0-9])$"; // 적어도 한개의 소문자
  public static final String PATTERN_PASSWORD_UPPERCASE = "^(?=.*[a-z])$"; // 적어도 한개의 대문자
  public static final String PATTERN_PASSWORD_SPECIAL_CHAR = "^(?=.*[!@#$%^&*()-_+=])$"; // 적어도 한개의 특수문자
  public static final String PATTERN_PASSWORD_NOT_WHITE_SPACE = "^(?=\\S+$)$"; // 공백이 없어야한다.
  public static final String PATTERN_PASSWORD_LEAST_EIGHT = "^.{8,}$"; // 8글자 이상
  public static final String PATTERN_AWS_S3_FILENAME = "^[0-9a-zA-Z!-_.*'()]+$"; // 영숫자 문자[0-9a-zA-Z] !, -, _, ., *, ', (, 및 )의 특수 문자

  public static final String PATTERN_DOMAIN = "\\b((?=[a-z0-9-]{1,63}\\.)[a-z0-9]+(-[a-z0-9]+)*\\.)+[a-z]{2,63}\\b";

  public static boolean isValidate(final String pattern, final String hex) {
    Matcher matcher = Pattern.compile(pattern).matcher(hex);
    return StringUtils.isEmpty(hex) || matcher.matches();
  }

  // 입력 값이 한글 형식인지 검사한다.
  public static boolean isKoreanPattern(final String value) {
    return StringUtils.isNotEmpty(value) && isValidate(PATTERN_KOREAN_ONLY, value);
  }

  // 입력 값이 한글이 포함된 형식인지 검사한다.
  public static boolean isIncludeKoreanPattern(final String value) {
    return StringUtils.isNotEmpty(value) && isValidate(PATTERN_KOREAN_INCLUDE, value);
  }

  // 입력 값이 영문 형식인지 검사한다.
  public static boolean isEnglishPattern(final String value) {
    return StringUtils.isNotEmpty(value) && isValidate(PATTERN_ENGLISH, value);
  }

  // 입력 값이 이메일 형식인지 검사한다.
  public static boolean isEmailPattern(final String value) {
    return StringUtils.isNotEmpty(value) && isValidate(PATTERN_EMAIL, value);
  }

  // 입력 값이 아이디 형식인지 검사한다.
  public static boolean isUsernamePattern(final String value) {
    return StringUtils.isNotEmpty(value) && isValidate(PATTERN_USERNAME, value);
  }

  // 입력 값이 이름 형식인지 검사한다.
  public static boolean isFullamePattern(final String value) {
    return StringUtils.isNotEmpty(value) && isValidate(PATTERN_FULLNAME, value);
  }

  // 입력 값이 닉네임 형식인지 검사한다.
  public static boolean isNicknamePattern(final String value) {
    return StringUtils.isNotEmpty(value) && isValidate(PATTERN_NICKNAME, value);
  }

  // 입력 값이 파일명 형식인지 검사한다.
  public static boolean isFilenamePattern(final String value) {
    return StringUtils.isNotEmpty(value) && isValidate(PATTERN_FILENAME, value);
  }

  // 입력 값이 도메인 형식인지 검사한다.
  public static boolean isDomainPattern(final String value) {
    return StringUtils.isNotEmpty(value) && isValidate(PATTERN_DOMAIN, value);
  }

  // 입력 값이 비밀번호 형식인지 검사한다.
  public static boolean isPasswordPattern(final String value) {
    return StringUtils.isNotEmpty(value) && isValidate(PATTERN_PASSWORD_NEW, value);
  }

  public static boolean isPasswordNumberPattern(final String value) {
    return StringUtils.isNotEmpty(value) && isValidate(PATTERN_PASSWORD_NUMBER, value);
  }

  public static boolean isPasswordLowercasePattern(final String value) {
    return StringUtils.isNotEmpty(value) && isValidate(PATTERN_PASSWORD_LOWERCASE, value);
  }

  public static boolean isPasswordUppercasePattern(final String value) {
    return StringUtils.isNotEmpty(value) && isValidate(PATTERN_PASSWORD_UPPERCASE, value);
  }

  public static boolean isPasswordSpecialCharPattern(final String value) {
    return StringUtils.isNotEmpty(value) && isValidate(PATTERN_PASSWORD_SPECIAL_CHAR, value);
  }

  public static boolean isPasswordNotWhiteSpacePattern(final String value) {
    return StringUtils.isNotEmpty(value) && isValidate(PATTERN_PASSWORD_NOT_WHITE_SPACE, value);
  }

  public static boolean isPasswordLeastEightPattern(final String value) {
    return StringUtils.isNotEmpty(value) && isValidate(PATTERN_PASSWORD_LEAST_EIGHT, value);
  }

  // 입력 값이 AWS S3의 파일명 형식인지 검사한다
  public static boolean isAWSS3FilenamePattern(final String value) {
    return StringUtils.isNotEmpty(value) && isValidate(PATTERN_AWS_S3_FILENAME, value);
  }

  public static void isForbidden(OAuth2Authentication oAuth2Authentication) {
    if (oAuth2Authentication == null || oAuth2Authentication.getPrincipal() == null) {
      throw new ForbiddenException();
    }
  }
}
