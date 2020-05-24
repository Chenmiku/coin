package kr.co.queenssmile.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class HtmlUtils {

  public static String firstText(String html, int limit) {
    try {
      Document doc = Jsoup.parseBodyFragment(html);

      String textElements[] = {"h1", "h2", "h3", "h4", "h5", "h6", "p"};


      Element body = doc.body();


      for (String tag : textElements) {

        Elements tags = doc.select(tag);

        if (!tags.isEmpty() && !StringUtils.isEmpty(tags.first().text())) {
          String text = tags.first().text();

          if (text.length() > limit) {
            text = text.substring(0, limit - 1);
          }
          return text;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String firstImg(String html) {
    try {
      Document doc = Jsoup.parseBodyFragment(html);
      Elements imgs = doc.select("img");

      if (!imgs.isEmpty() && !StringUtils.isEmpty(imgs.first().attr("src"))) {
        return imgs.first().attr("src");
      } else {
        return ""; // 이미지 없음.
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static boolean hasImg(String html) {
    try {
      Document doc = Jsoup.parseBodyFragment(html);
      Elements imgs = doc.select("img");

      return !imgs.isEmpty() && !StringUtils.isEmpty(imgs.first().attr("src"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }


  public static String convertLineSeparatorToBrTag(String content) {
    if (content == null) {
      return null;
    }
    return content
        .replaceAll("\r\n", "<br/>")
        .replaceAll(System.getProperty("line.separator"), "<br/>")
        .replaceAll("\n", "<br/>");
  }

  public static String convertBrTagToLineSeparator(String content) {
    if (content == null) {
      return null;
    }
    return content
        .replaceAll("<br/>", System.getProperty("line.separator"))
        .replaceAll("<br>", System.getProperty("line.separator"));
  }

}
