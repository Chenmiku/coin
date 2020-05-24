package kr.co.queenssmile.core.utils;

public class OSUtils {

  private static final String OS = System.getProperty("os.name").toLowerCase();

  public static boolean isWindows() {
    return OS.contains("win");
  }

  public static boolean isMac() {
    return OS.contains("mac");
  }

  public static boolean isLinux() {
    return OS.contains("linux");
  }

  public static boolean isUnix() {
    return OS.contains("nix") || OS.indexOf("aix") > 0;
  }

  public static boolean isSolaris() {
    return OS.contains("sunos");
  }
}
