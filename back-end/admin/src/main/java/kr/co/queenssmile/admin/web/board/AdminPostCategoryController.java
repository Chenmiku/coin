package kr.co.queenssmile.admin.web.board;

import kr.co.queenssmile.admin.service.board.post.PCategoryAdService;
import kr.co.queenssmile.core.config.exception.BadRequestException;
import kr.co.queenssmile.core.domain.board.post.Post;
import kr.co.queenssmile.core.domain.board.post.category.PCategory;
import kr.co.queenssmile.core.domain.setting.AppSetting;
import kr.co.queenssmile.core.utils.LocaleUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/admin/category-post")
public class AdminPostCategoryController {

    @Autowired
    private PCategoryAdService categoryService;

    // DUPLICATE
    @ResponseBody
    @PostMapping(value = "/duplicate/{type}/{language}", consumes = org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> duplicate(@RequestBody org.springframework.util.MultiValueMap<String, String> body,
                                       @PathVariable Post.Type type,
                                       @PathVariable Locale language,
                                       @ModelAttribute("setting") AppSetting setting) {

        final String fieldName = LocaleUtils.getFieldNameByCategory(language);
        Map<String, Object> result = new HashMap<>();

        if (body == null || body.getFirst(fieldName) == null) {
            throw new BadRequestException();
        }

        String value = body.getFirst(fieldName);

        if (StringUtils.isEmpty(value)) {
            throw new BadRequestException();
        }

        if (categoryService.isDuplicate(language, value, type)) {
            result.put("result", "duplicate");
        } else {
            result.put("result", "success");
        }
        return ResponseEntity.ok(result);
    }

    @ResponseBody
    @PostMapping(value = "/{idCategory}/duplicate/{type}/{language}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> duplicateUpdate(@RequestBody org.springframework.util.MultiValueMap<String, String> body,
                                             @PathVariable Long idCategory,
                                             @PathVariable Post.Type type,
                                             @PathVariable Locale language,
                                             @ModelAttribute("setting") AppSetting setting) {

        final String fieldName = LocaleUtils.getFieldNameByCategory(language);
        Map<String, Object> result = new HashMap<>();

        if (body == null || body.getFirst(fieldName) == null) {
            return ResponseEntity.badRequest().build();
        }

        String value = body.getFirst(fieldName);

        PCategory category = categoryService.get(language, idCategory);
        final String name = category.getName().getValue();

        if (value.equals(name)) {
            result.put("result", "success");
        } else if (categoryService.isDuplicate(language, value, type)) {
            result.put("result", "duplicate");
        } else {
            result.put("result", "success");
        }
        return ResponseEntity.ok(result);
    }
}
