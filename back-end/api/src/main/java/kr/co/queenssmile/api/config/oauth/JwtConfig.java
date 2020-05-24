package kr.co.queenssmile.api.config.oauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;

@Configuration
public class JwtConfig {

  @Value("${jwt.keystore.alias}")
  private String keyStoreAlias;

  @Value("${jwt.keystore.password}")
  private String keyStorePassword;

  @Bean
  public KeyPair keyPair() {
    return new KeyStoreKeyFactory(new ClassPathResource("keystore.jks"), keyStorePassword.toCharArray())
      .getKeyPair(keyStoreAlias);
  }
}
