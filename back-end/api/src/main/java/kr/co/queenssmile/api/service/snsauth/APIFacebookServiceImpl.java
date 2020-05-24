package kr.co.queenssmile.api.service.snsauth;

import kr.co.queenssmile.core.domain.user.sns.FacebookApiKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Slf4j
@Service
public class APIFacebookServiceImpl implements APIFacebookService {

  @Autowired
  private FacebookApiKey apiKey;

  @Override
  public Map getAccessToken(String code, String redirectUri) {

    URI uri = UriComponentsBuilder.fromHttpUrl(apiKey.getAccessTokenUri())
        .queryParam("client_id", apiKey.getClientId())
        .queryParam("client_secret", apiKey.getClientSecret())
        .queryParam("code", code)
        .queryParam("redirect_uri", redirectUri)
        .build().toUri();

    log.debug("uri ::: {}", uri.toString());

    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<Map> response = restTemplate.getForEntity(uri, Map.class);

    return response.getBody();
  }

  @Override
  public Map getMe(String accessToken) {

    URI uri = UriComponentsBuilder.fromHttpUrl(apiKey.getMeUri())
        .queryParam("access_token", accessToken)
        .queryParam("fields", apiKey.getMeFields())
        .build().toUri();

    log.debug("uri ::: {}", uri.toString());

    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<Map> response = restTemplate.getForEntity(uri, Map.class);

    return response.getBody();
  }
}
