package kr.co.queenssmile.core.config.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Converter
public class CryptoAESDateConverter implements AttributeConverter<LocalDate, String> {

    private static final String key = "1YFQGN92RLUKN6BV";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String AES_CBC_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String ENCODING = "UTF-8";

    private String iv;
    private java.security.Key keySpec;

    public CryptoAESDateConverter() throws UnsupportedEncodingException, InvalidKeyException {

        if (key == null) {
            throw new IllegalArgumentException();
        }

        if (key.length() < 16) {
            throw new InvalidKeyException("키의 길이가 짧습니다.");
        }

        this.iv = key.substring(0, 16);
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
        int len = b.length;
        if (len > keyBytes.length) {
            len = keyBytes.length;
        }
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        this.keySpec = keySpec;
    }

    @Override
    public String convertToDatabaseColumn(LocalDate attribute) {

        if (StringUtils.isEmpty(attribute)) {
            return null;
        }

        try {
            Cipher c = Cipher.getInstance(AES_CBC_ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
            byte[] encrypted = c.doFinal(attribute.format(FORMATTER).getBytes(ENCODING));
            return new String(Base64.encodeBase64(encrypted));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LocalDate convertToEntityAttribute(String dbData) {

        if (StringUtils.isEmpty(dbData)) {
            return null;
        }

        try {
            Cipher c = Cipher.getInstance(AES_CBC_ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
            byte[] byteStr = Base64.decodeBase64(dbData.getBytes());
            String string = new String(c.doFinal(byteStr), ENCODING);
            return LocalDate.parse(string, FORMATTER);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
