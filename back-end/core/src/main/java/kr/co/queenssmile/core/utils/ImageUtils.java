package kr.co.queenssmile.core.utils;

import com.twelvemonkeys.image.ResampleOp;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.*;

public class ImageUtils {

  public static BufferedImage resize(BufferedImage input, int width, int height) throws IOException {
    // A good default filter, see class documentation for more info
    BufferedImageOp bufferedImageOp = new ResampleOp(width, height, ResampleOp.FILTER_LANCZOS);
    return bufferedImageOp.filter(input, null);
  }

  public static BufferedImage rotateClockwise90(BufferedImage src) {
    int width = src.getWidth();
    int height = src.getHeight();

    BufferedImage dest = new BufferedImage(height, width, src.getType());

    Graphics2D graphics2D = dest.createGraphics();
    graphics2D.translate((height - width) / 2, (height - width) / 2);
    graphics2D.rotate(Math.PI / 2, height / 2, width / 2);
    graphics2D.drawRenderedImage(src, null);

    return dest;
  }

  public static String getMostColor(InputStream inputStream) throws Exception {

    ImageInputStream is = ImageIO.createImageInputStream(inputStream);
    Iterator iter = ImageIO.getImageReaders(is);

    if (!iter.hasNext()) {
      System.exit(1);
    }

    ImageReader imageReader = (ImageReader) iter.next();
    imageReader.setInput(is);

    BufferedImage image = imageReader.read(0);

    int height = image.getHeight();
    int width = image.getWidth();

    Map m = new HashMap();
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        int rgb = image.getRGB(i, j);
        int[] rgbArr = getRGBArr(rgb);
        // Filter out grays....
        if (!isGray(rgbArr)) {
          Integer counter = (Integer) m.get(rgb);
          if (counter == null)
            counter = 0;
          counter++;
          m.put(rgb, counter);
        }
      }
    }
    String colourHex = getMostCommonColor(m);
    return colourHex;
  }

  public static String getMostCommonColor(Map map) {
    List list = new LinkedList(map.entrySet());
    Collections.sort(list, (Comparator) (o1, o2) -> ((Comparable) ((Map.Entry) (o1)).getValue())
        .compareTo(((Map.Entry) (o2)).getValue()));
    Map.Entry me = (Map.Entry) list.get(list.size() - 1);
    int[] rgb = getRGBArr((Integer) me.getKey());
    Color color = new Color(rgb[0], rgb[1], rgb[2]);
//    return Integer.toHexString(rgb[0]) + " " + Integer.toHexString(rgb[1]) + " " + Integer.toHexString(rgb[2]);
    String hex = Integer.toHexString(color.getRGB() & 0xffffff);
    if (hex.length() < 6) {
      hex = "0" + hex;
    }
    return "#" + hex;
  }

  public static int[] getRGBArr(int pixel) {
    int alpha = (pixel >> 24) & 0xff;
    int red = (pixel >> 16) & 0xff;
    int green = (pixel >> 8) & 0xff;
    int blue = (pixel) & 0xff;
    return new int[]{red, green, blue};

  }

  public static boolean isGray(int[] rgbArr) {
    int rgDiff = rgbArr[0] - rgbArr[1];
    int rbDiff = rgbArr[0] - rgbArr[2];
    // Filter out black, white and grays...... (tolerance within 10 pixels)
    int tolerance = 10;
    if (rgDiff > tolerance || rgDiff < -tolerance)
      if (rbDiff > tolerance || rbDiff < -tolerance) {
        return false;
      }
    return true;
  }
}
