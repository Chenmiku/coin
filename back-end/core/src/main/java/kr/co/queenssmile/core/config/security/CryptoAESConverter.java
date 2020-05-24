package kr.co.queenssmile.core.config.security;//package kr.co.queenssmile.admin.config.security;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.nio.charset.StandardCharsets;
import java.security.Key;

@Slf4j
@Converter
@NoArgsConstructor
public class CryptoAESConverter implements AttributeConverter<String, String> {

  @Value("${crypto.key}")
  private String key;

  @Value("${crypto.iv}")
  private String iv;

  public Key getAESKey() throws Exception {



    Key keySpec;

    byte[] gxKeyByte = Base64.decodeBase64(key); // Base64 디코딩
    String key = new String(gxKeyByte);

//    log.debug(">> key ::: {} > {}", key, key.length());
//    log.debug(">> iv ::: {} > {}", iv, iv.length());

    if (key.length() != 128 / 8) {
      throw new IllegalArgumentException("> 'secretKey' must be 128 bit");
    }

    if (iv.length() != 128 / 8) {
      throw new IllegalArgumentException("> 'iv' must be 128 bit");
    }

    keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
    return keySpec;
  }

  @Override
  public String convertToDatabaseColumn(String attribute) {


    if (StringUtils.isEmpty(attribute)) {
      return null;
    }

    try {
      Key keySpec = getAESKey();
      String iv = this.iv.trim();
      Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
      c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8)));
      byte[] encrypted = c.doFinal(attribute.getBytes(StandardCharsets.UTF_8));
      String enStr = new String(Base64.encodeBase64(encrypted)); // Hex 인코딩

      return enStr;
    } catch (Exception e) {
      log.debug("암호화 에러 >>>>>>>>>>>>>>>>>>>>> attribute ::: {}", attribute);
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public String convertToEntityAttribute(String dbData) {

    if (StringUtils.isEmpty(dbData)) {
      return null;
    }

    try {
      Key keySpec = getAESKey();
      String iv = this.iv.trim();
      Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
      c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8)));
      byte[] byteStr = Base64.decodeBase64(dbData.getBytes(StandardCharsets.UTF_8));
      String decStr = new String(c.doFinal(byteStr), StandardCharsets.UTF_8);

      return decStr;
    } catch (Exception e) {
      log.debug("암호화 에러 >>>>>>>>>>>>>>>>>>>>> dbData ::: {}", dbData);
      e.printStackTrace();
    }
    return null;
  }
}