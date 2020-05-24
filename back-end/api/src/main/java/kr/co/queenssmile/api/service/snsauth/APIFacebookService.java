package kr.co.queenssmile.api.service.snsauth;

import java.util.Map;

public interface APIFacebookService {

  Map getAccessToken(String code, String redirectUri);

  Map getMe(String accessToken);
}
