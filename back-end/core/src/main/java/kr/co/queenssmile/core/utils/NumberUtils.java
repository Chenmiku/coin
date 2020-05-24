package kr.co.queenssmile.core.utils;

import java.util.Random;

public class NumberUtils {

  public static String getRandomNumber(int length) {
    Random randomNum = new Random();
    String numTemp[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    String auth = "";

    for (int i = 0; i < length; i++) {
      auth = auth + numTemp[randomNum.nextInt(10)];
    }
    return auth;
  }

  public static String getRandomNumber5(int length) {
    Random randomNum = new Random();
    String numTemp[] = {"1", "2", "3", "4", "5"};
    String auth = "";

    for (int i = 0; i < length; i++) {
      auth = auth + numTemp[randomNum.nextInt(5)];
    }
    return auth;
  }

}
