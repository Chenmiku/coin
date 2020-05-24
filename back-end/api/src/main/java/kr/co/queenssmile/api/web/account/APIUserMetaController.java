package kr.co.queenssmile.api.web.account;

import com.google.common.collect.Lists;
import io.swagger.v3.oas.annotations.Operation;
import kr.co.queenssmile.core.domain.user.Gender;
import kr.co.queenssmile.core.utils.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class APIUserMetaController {

  @Operation(summary = "[user-meta-1] Country List (국가 항목)", description = "")
  @GetMapping("/countries")
  public ResponseEntity<?> countries() {

    List<Map<String, String>> list = Lists.newArrayList(Locale.getAvailableLocales()).stream()
        .filter(locale -> StringUtils.isNotEmpty(locale.getCountry()))
        .map(locale -> {
          Map<String, String> map = new HashMap<>();
          map.put("code", locale.getCountry().toUpperCase());
          map.put("name", locale.getDisplayCountry());
          return map;
        }).collect(Collectors.toList());
    return ResponseEntity.ok(list);
  }

  @Operation(summary = "[user-meta-2] Gender (남/여)", description = "")
  @GetMapping("/genders")
  public ResponseEntity<?> genders() {

    List<Map<String, String>> list = Lists.newArrayList(Gender.values()).stream()
        .map(gender -> {
          Map<String, String> map = new HashMap<>();
          map.put(gender.toString(), gender.getValue());
          return map;
        }).collect(Collectors.toList());

    return ResponseEntity.ok(list);
  }
}
