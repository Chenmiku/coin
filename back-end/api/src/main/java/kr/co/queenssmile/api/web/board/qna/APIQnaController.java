package kr.co.queenssmile.api.web.board.qna;

import io.swagger.v3.oas.annotations.Operation;
import kr.co.queenssmile.api.service.board.qna.QnaCategoryService;
import kr.co.queenssmile.api.service.board.qna.QnaService;
import kr.co.queenssmile.core.config.exception.BadRequestException;
import kr.co.queenssmile.core.model.Filter;
import kr.co.queenssmile.core.model.SuccessResponseBody;
import kr.co.queenssmile.core.model.aws.SESSender;
import kr.co.queenssmile.core.model.reqbody.board.qna.QuestionReqBody;
import kr.co.queenssmile.core.service.aws.AWSSESService;
import kr.co.queenssmile.core.utils.StringUtils;
import kr.co.queenssmile.core.utils.ValidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 1:1 문의
 */
@RestController
@RequestMapping("/api/v1/qnas")
public class APIQnaController {


  @Autowired
  private QnaService qnaService;

  @Autowired
  private QnaCategoryService qnaCategoryService;

  @Autowired
  private AWSSESService awssesService;

  /**
   * [qna-1] 카테고리
   */
  @Operation(summary = "[qna-1] Get list qnacategory (카테고리)", description = "get list qna category")
  @GetMapping(value = "/categories", produces = MediaTypes.HAL_JSON_VALUE)
  public ResponseEntity<?> categories(Locale locale) {
    return ResponseEntity.ok(qnaCategoryService.categories(locale));
  }

  /**
   * [qna-2] 문의하기
   */
  @Operation(summary = "[qna-2] Pót qna (카테고리)", description = "create qna")
  @GetMapping
  public ResponseEntity<?> page(@PageableDefault(size = 20, sort = {"createdDate"}, direction = Sort.Direction.DESC) Pageable pageable,
                                @RequestParam(required = false, defaultValue = "") String query,
                                @RequestParam(required = false) String startDate,
                                @RequestParam(required = false) String endDate,
                                @RequestParam(required = false) Long idCategory,
                                OAuth2Authentication oAuth2Authentication,
                                HttpServletRequest request,
                                Locale locale) {


    Filter filter = new Filter(pageable, query, startDate, endDate);
    return ResponseEntity.ok(qnaService.pagedResources(locale, request, filter, idCategory, oAuth2Authentication != null ? oAuth2Authentication.getPrincipal().toString() : null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> get(@PathVariable Long id,
                               HttpServletRequest request,
                               OAuth2Authentication oAuth2Authentication,
                               Locale locale) {

    ValidUtils.isForbidden(oAuth2Authentication);
    return ResponseEntity.ok(qnaService.resource(locale, request, id, oAuth2Authentication.getPrincipal().toString()));
  }

  /**
   * [qna-2] 문의하기
   */
  @Operation(summary = "[qna-2] Post qna (카테고리)", description = "create qna")
  @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
  public ResponseEntity<?> post(@RequestBody QuestionReqBody question,
                                OAuth2Authentication oAuth2Authentication) {

    if (question == null
        || StringUtils.isEmpty(question.getTitle())
        || StringUtils.isEmpty(question.getContent())) {
      throw new BadRequestException("제목과 내용을 모두 입력해주세요.");
    }

    if (question.isMember()) {
      ValidUtils.isForbidden(oAuth2Authentication);

      qnaService.post(question, oAuth2Authentication.getPrincipal().toString());

      URI redirect = null;
      try {
        redirect = new URI("/");
      } catch (URISyntaxException e) {
        e.printStackTrace();
      }
      return ResponseEntity.created(redirect).body(SuccessResponseBody.of(true, "질문이 등록되었습니다."));
    } else {

      if (StringUtils.isEmpty(question.getFullname())
          || StringUtils.isEmpty(question.getMobile())
          || StringUtils.isEmpty(question.getPassword())) {
        throw new BadRequestException("비회원 정보를 모두 입력해주세요." + question.getFullname() + question.getMobile() + question.getPassword());
      }

      qnaService.postNoMember(question);

      // send mail
      String subject = question.getTitle();
      String message = question.getContent();
      List<String> stringList = new ArrayList<>();
      stringList.add(oAuth2Authentication.getPrincipal().toString());
      SESSender sesSender = new SESSender(stringList, subject, message);
      awssesService.send(sesSender);

      URI redirect = null;
      try {
        redirect = new URI("/");
      } catch (URISyntaxException e) {
        e.printStackTrace();
      }
      return ResponseEntity.created(redirect).body(SuccessResponseBody.of(true, "질문이 등록되었습니다."));
    }
  }

  @DeleteMapping(value = "{id}", produces = MediaTypes.HAL_JSON_VALUE)
  public ResponseEntity<?> delete(@PathVariable Long id,
                                  OAuth2Authentication oAuth2Authentication) {

    ValidUtils.isForbidden(oAuth2Authentication);
    qnaService.delete(id, oAuth2Authentication.getPrincipal().toString());
    return ResponseEntity.ok().build();
  }
}
