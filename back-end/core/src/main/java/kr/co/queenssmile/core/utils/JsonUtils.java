package kr.co.queenssmile.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class JsonUtils {

  public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static <T> T fromString(String jsonStr, Class<T> clazz) {
    try {
      return OBJECT_MAPPER.readValue(jsonStr, clazz);
    } catch (IOException e) {
      throw new IllegalArgumentException("The given string value: " + jsonStr + " cannot be transformed to Json object");
    }
  }

  public static <T> List<T> toList(String jsonStr, Class<T> clazz) {
    try {
      return OBJECT_MAPPER.readValue(jsonStr, OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
    } catch (IOException e) {
      throw new IllegalArgumentException("The given string value: " + jsonStr + " cannot be transformed to Json object");
    }
  }

  public static <K, V> Map<K, V> toMap(String jsonStr) {
    try {
      return OBJECT_MAPPER.readValue(jsonStr, new TypeReference<Map<K, V>>() {
      });
    } catch (IOException e) {
      throw new IllegalArgumentException("The given string value: " + jsonStr + " cannot be transformed to Json object");
    }
  }

  public static String toJson(Object value) {
    try {
      return OBJECT_MAPPER.writeValueAsString(value);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("The given Json object value: " + value + " cannot be transformed to a String");
    }
  }

  public static JsonNode toJsonNode(String jsonStr) {
    try {
      return OBJECT_MAPPER.readTree(jsonStr);
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }

  public static <T> T clone(T value) {
    return fromString(toJson(value), (Class<T>) value.getClass());
  }

  public static String toPrettyJson(String json) {
    Map jsonObject = JsonUtils.toMap(json);
    try {
      return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return "";
  }

  public static String toString(Object value) {
    try {
      return OBJECT_MAPPER.writeValueAsString(value);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("The given Json object value: " + value + " cannot be transformed to a String");
    }
  }
}
