package kr.co.queenssmile.core.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.ResponseBody;

import java.util.HashMap;
import java.util.Map;

public class OkHttpUtils {

  public static Map<String, Object> toMap(ResponseBody body) {
    try {
      String json = body.string();
      return new ObjectMapper().readValue(json, new TypeReference<Map<String, Object>>() {
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new HashMap<>();
  }

  public static <T> T toObject(ResponseBody body, Class<T> clazz) {
    try {
      String json = body.string();
      return new ObjectMapper().readValue(json, clazz);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
