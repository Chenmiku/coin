package kr.co.queenssmile.api.service.board.qna;

import kr.co.queenssmile.api.service.user.UserService;
import kr.co.queenssmile.core.config.exception.BadRequestException;
import kr.co.queenssmile.core.domain.board.qna.Answer;
import kr.co.queenssmile.core.domain.board.qna.Qna;
import kr.co.queenssmile.core.domain.board.qna.QnaPredicate;
import kr.co.queenssmile.core.domain.board.qna.QnaRepository;
import kr.co.queenssmile.core.domain.board.qna.category.QnaCategoryRepository;
import kr.co.queenssmile.core.domain.user.User;
import kr.co.queenssmile.core.model.Filter;
import kr.co.queenssmile.core.model.reqbody.board.qna.QuestionReqBody;
import kr.co.queenssmile.core.model.resbody.board.QnaResBody;
import kr.co.queenssmile.core.service.setting.AppSettingService;
import kr.co.queenssmile.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class QnaServiceImpl implements QnaService {

  @Autowired
  private PagedResourcesAssembler pagedResourcesAssembler;

  @Autowired
  private QnaRepository qnaRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private QnaCategoryRepository qnaCategoryRepository;

  @Autowired
  private AppSettingService appSettingService;

  @Override
  @Transactional
  public Qna create(Qna qna) {
    return qnaRepository.save(qna);
  }

  @Override
  @Transactional
  public Qna update(Qna qna, HttpServletRequest request) {

    if (qna.getId() == null) {
      throw new BadRequestException();
    }

    return qnaRepository.findById(qna.getId())
        .map(ori -> {

          // 답변 이메일 발송하기
          if (qna.getAnswer() != null && StringUtils.isNotEmpty(qna.getAnswer().getContent())) {
            if (ori.getAnswer() == null
                || !Objects.equals(ori.getAnswer().getContent(), qna.getAnswer().getContent())) {

              String subject = "문의 내용에 답변합니다.";
              String email = ori.getRelativeUser().getEmail();

              QnaResBody questionAndAnswer = new QnaResBody();
              questionAndAnswer.setTitle(ori.getTitle());
              questionAndAnswer.setContent(ori.getContent());
              questionAndAnswer.setRegDate(ori.getCreatedDate());
              questionAndAnswer.setFullname(ori.getRelativeUser().getFullName());

              Answer answer = new Answer();
              answer.setContent(qna.getAnswer().getContent());
              questionAndAnswer.setAnswer(answer);

              if (ori.getCategories() != null) {
                questionAndAnswer.setCategories(ori.getCategories().stream()
                    .map(category -> category.getName().getValue())
                    .collect(Collectors.joining(",")));
              }

              Map<String, Object> model = new HashMap<>();
//              model.put("subject", subject);
//              model.put("email", email);
//              model.put("questionAndAnswer", questionAndAnswer);
//              model.put("nowDate", LocalDate.now());

//              emailService.send(email, subject, model, "email/qna-answer.ftl");
            }
          }

          BeanUtils.copyProperties(qna, ori, Qna.IGNORE_PROPERTIES);
          return qnaRepository.save(ori);
        }).orElseThrow(BadRequestException::new);
  }

  @Override
  @Transactional
  public Qna get(Long id) {

    return qnaRepository.findById(id)
        .map(qna -> {
          qna.lazy();
          return qna;
        }).orElse(null);
  }

  @Override
  @Transactional
  public void delete(Long id) {

    qnaRepository.findById(id).ifPresent(ebQna -> {
      ebQna.delete();
      qnaRepository.delete(ebQna);
    });
  }

  @Override
  @Transactional
  public Page<Qna> page(Filter filter, Boolean isActive) {

    Page<Qna> page = qnaRepository.findAll(
        QnaPredicate.getInstance()
            .search(filter.getQuery())
            .startDate(filter.getStartDate())
            .endDate(filter.getEndDate())
            .active(isActive)
            .values(),
        filter.getPageable());

    page.forEach(ebQna -> {
      ebQna.lazy();
    });
    return page;
  }

  @Override
  @Transactional
  public PagedModel<?> pagedResources(Locale locale, HttpServletRequest request, Filter filter, Long idCategory, String email) {

    Locale defaultLocale = appSettingService.getDefaultLocale();

    Page<Qna> page = qnaRepository.findAll(
        QnaPredicate.getInstance()
            .search(filter.getQuery())
            .startDate(filter.getStartDate())
            .endDate(filter.getEndDate())
            .category(idCategory)
            .active(true)
            .email(email)
            .values(),
        filter.getPageable());

    page.forEach(ebQna -> {
      ebQna.lazy();
    });

    List<QnaResBody> list = page.getContent().stream()
        .map(qna -> {
          qna.setLocale(locale);

          QnaResBody questionAndAnswer = new QnaResBody();
          questionAndAnswer.setId(qna.getId());
          questionAndAnswer.setTitle(qna.getTitle());
          questionAndAnswer.setContent(qna.getContent());
          questionAndAnswer.setRegDate(qna.getCreatedDate());

          if (qna.getAnswer() != null && StringUtils.isNotEmpty(qna.getAnswer().getContent())) {
            Answer answer = new Answer();
            answer.setContent(qna.getAnswer().getContent());
            answer.setRegDate(qna.getAnswer().getRegDate());
            questionAndAnswer.setAnswer(answer);
          }

          if (qna.getCategories() != null) {
            questionAndAnswer.setCategories(qna.getCategories().stream()
                .filter(category -> category.isLocale(locale, defaultLocale))
                .map(category -> category.getName().getValue())
                .collect(Collectors.joining(",")));
          }

          if (qna.getRelativeUser() != null) {
            questionAndAnswer.setFullname(qna.getRelativeUser().getFullName());
          }

          return questionAndAnswer;
        }).collect(Collectors.toList());

    Page<QnaResBody> qPage = new PageImpl<>(list, filter.getPageable(), page.getTotalElements());

    return pagedResourcesAssembler.toModel(qPage);
  }

  @Override
  @Transactional
  public EntityModel<?> resource(Locale locale, HttpServletRequest request, Long id, String email) {

    Qna qna = qnaRepository.getByUser(id, email);

    if (qna == null || !qna.isActive()) {
      throw new BadRequestException();
    }

    // 카테고리 로케일 설정
    qna.setLocale(locale);

    Locale defaultLocale = appSettingService.getDefaultLocale();

    QnaResBody questionAndAnswer = new QnaResBody();
    questionAndAnswer.setId(qna.getId());
    questionAndAnswer.setTitle(qna.getTitle());
    questionAndAnswer.setContent(qna.getContent());
    questionAndAnswer.setRegDate(qna.getCreatedDate());

    if (qna.getCategories() != null) {
      questionAndAnswer.setCategories(qna.getCategories().stream()
          .filter(category -> category.isLocale(locale, defaultLocale))
          .map(category -> category.getName().getValue())
          .collect(Collectors.joining(",")));
    }

    if (qna.getAnswer() != null && StringUtils.isNotEmpty(qna.getAnswer().getContent())) {
      Answer answer = new Answer();
      answer.setContent(qna.getAnswer().getContent());
      answer.setRegDate(qna.getAnswer().getRegDate());
      questionAndAnswer.setAnswer(answer);
    }

    if (qna.getRelativeUser() != null) {
      questionAndAnswer.setFullname(qna.getRelativeUser().getFullName());
    }

    return new EntityModel<>(questionAndAnswer);
  }

  @Override
  @Transactional
  public QuestionReqBody post(QuestionReqBody questionReqBody, String email) {

    User user = userService.get(email);

    Qna qna = questionReqBody.toQna(user);
    if (qna.getIdCategory() != null) {
      qnaCategoryRepository.findById(qna.getIdCategory()).ifPresent(category -> {
        qna.getCategories().add(category);
      });
    }
    Qna createdQna = this.create(qna);

    if (createdQna == null) {
      throw new RuntimeException();
    }
    return questionReqBody;
  }

  @Override
  public QuestionReqBody postNoMember(QuestionReqBody questionReqBody) {

    Qna qna = questionReqBody.toQnaNoMember();

    if (qna.getIdCategory() != null) {
      qnaCategoryRepository.findById(qna.getIdCategory()).ifPresent(category -> {
        qna.getCategories().add(category);
      });
    }
    Qna createdQna = this.create(qna);

    if (createdQna == null) {
      throw new RuntimeException();
    }
    return questionReqBody;
  }

  @Override
  @Transactional
  public void delete(Long id, String email) {

    Qna qna = qnaRepository.getByUser(id, email);
    if (qna != null) {
      qnaRepository.delete(qna);
    }
  }
}
