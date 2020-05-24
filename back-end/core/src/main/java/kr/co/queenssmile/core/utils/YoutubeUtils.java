package kr.co.queenssmile.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YoutubeUtils {

  final static String youTubeUrlRegEx = "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/";
//  final static String[] videoIdRegex = {"\\?vi?=([^&]*)", "watch\\?.*v=([^&]*)", "(?:embed|vi?)/([^/?]*)", "^([A-Za-z0-9\\-]*)"};
  final static String[] videoIdRegex = {"\\?vi?=([^&]*)", "watch\\?.*v=([^&]*)", "(?:embed|vi?)/([^/?]*)", "^([A-Za-z0-9\\-]*)"};

  public static String extractVideoIdFromUrl(String url) {
//    try {
//      String youTubeLinkWithoutProtocolAndDomain = youTubeLinkWithoutProtocolAndDomain(url);
//
//      for (String regex : videoIdRegex) {
//        Pattern compiledPattern = Pattern.compile(regex);
//        Matcher matcher = compiledPattern.matcher(youTubeLinkWithoutProtocolAndDomain);
//
//        if (matcher.find()) {
//          return matcher.group(1);
//        }
//      }
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//
//    return null;

    String pattern = "https?:\\/\\/(?:[0-9A-Z-]+\\.)?(?:youtu\\.be\\/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|<\\/a>))[?=&+%\\w]*";

    Pattern compiledPattern = Pattern.compile(pattern,
        Pattern.CASE_INSENSITIVE);
    Matcher matcher = compiledPattern.matcher(url);
    if (matcher.find()) {
      return matcher.group(1);
    }/*from w  w  w.  j a  va  2 s .c om*/
    return null;
  }

  private static String youTubeLinkWithoutProtocolAndDomain(String url) {
    Pattern compiledPattern = Pattern.compile(youTubeUrlRegEx);
    Matcher matcher = compiledPattern.matcher(url);

    if (matcher.find()) {
      return url.replace(matcher.group(), "");
    }
    return url;
  }
}
