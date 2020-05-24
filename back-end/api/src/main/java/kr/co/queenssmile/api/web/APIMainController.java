package kr.co.queenssmile.api.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/start")
public class APIMainController {


  @Autowired
  public APIMainController() {
  }

  @GetMapping("/1")
  public ResponseEntity<?> test(){
    return ResponseEntity.ok("OK");
  }
}
