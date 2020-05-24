package kr.co.queenssmile.api.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class ProcessEnvController {

  @Value("${process.env.set}")
  private String processEnv;

  @GetMapping("/process-env")
  public ResponseEntity<?> getProfile(HttpServletRequest request) {
    log.info(request.getRemoteHost());
    return ResponseEntity.ok(processEnv);
  }
}
