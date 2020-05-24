package kr.co.queenssmile.api.config.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.security.KeyPair;

/**
 * Token 발급 URL : /oauth/token?grant_type=password&username=%s&password=%s
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
public class OAuthConfig extends AuthorizationServerConfigurerAdapter {

  @Value("${jwt.keystore.password}")
  private String keyStorePassword;

  @Value("${oauth.password.client.id}")
  private String clientID;

  @Value("${oauth.password.client.secret}")
  private String clientSecret; // 비밀키 - 아무도 보지 못하게 한다.

  @Value("${jwt.token.expire-time}")
  private int tokenExpireTime; // 토큰 만료일

  /* boot2.1 migration start */
  private AuthenticationManager authenticationManager;

  public OAuthConfig(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
  }
  /* boot2.1 migration end */

  @Autowired
  private KeyPair keyPair;

  @Bean
  public JwtTokenStore tokenStore() {
    return new JwtTokenStore(jwtAccessTokenConverter());
  }

  /**
   * keytool -genkeypair -keyalg RSA -alias jwtkey-queenssmile-1 -dname "cn=auth server, o=queenssmile, st=Seoul, c=KR" -keypass AF_IcAQPf5O1q3q -keystore ./keystore.jks -storepass AF_IcAQPf5O1q3q -storetype pkcs12
   * keytool -export -keystore keystore.jks -alias jwtkey-queenssmile-1 -file jwtkey-queenssmile-1.cer
   * [리소스 서버에서 사용될 public key 생성 ]
   * openssl x509 -inform der -in jwtkey-queenssmile-1.cer -pubkey -noout
   */
  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    /*
     * Custom jwtAccessTokenConverter 설정
     * JwtAccessTokenConverter converter = new CustomJwtAccessTokenConverter();
     */
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//    KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource("keystore.jks"), keyStorePassword.toCharArray()).getKeyPair("jwtkey-queenssmile-1");
    converter.setKeyPair(keyPair);
//        converter.setSigningKey("HS256");
    return converter;
  }

  //https://brunch.co.kr/@sbcoba/4
  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

    log.debug("> clientID : {}", clientID);
    log.debug("> clientSecret : {}", clientSecret);

    clients.inMemory()
        .withClient(clientID)
        .secret(clientSecret)
        .authorizedGrantTypes("authorization_code", "refresh_token", "password")
        .scopes("read")
        .autoApprove(true)
        .accessTokenValiditySeconds(tokenExpireTime) // 50일 주기
    ;
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints.authenticationManager(authenticationManager).accessTokenConverter(jwtAccessTokenConverter());
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
    oauthServer
        .tokenKeyAccess("permitAll()")
        .checkTokenAccess("isAuthenticated()")
    /* boot2.1 migration start */
    /* passwordEncoder 설정
     * 참고: https://thecodinglog.github.io/spring/security/2018/06/12/spring-security-4.html 에 Password Encoding 섹션 */
//      .passwordEncoder(new BCryptPasswordEncoder())
    ;
    /* boot2.1 migration end */
  }
}
