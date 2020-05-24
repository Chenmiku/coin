package kr.co.queenssmile.admin.service.board.qna;

import kr.co.queenssmile.core.config.exception.BadRequestException;
import kr.co.queenssmile.core.domain.board.qna.Qna;
import kr.co.queenssmile.core.domain.board.qna.QnaPredicate;
import kr.co.queenssmile.core.domain.board.qna.QnaRepository;
import kr.co.queenssmile.core.domain.user.UserRepository;
import kr.co.queenssmile.core.model.Filter;
import kr.co.queenssmile.core.service.setting.AppSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class QnaAdServiceImpl implements QnaAdService {

  @Autowired
  private QnaRepository qnaRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AppSettingService appSettingService;

//  @Autowired
//  private EmailService emailService;

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
          /*
          if (qna.getAnswer() != null && StringUtils.isNotEmpty(qna.getAnswer().getContent())) {
            if (ori.getAnswer() == null
                || !Objects.equals(ori.getAnswer().getContent(), qna.getAnswer().getContent())) {

              String subject = "문의 내용에 답변합니다.";
              String email = ori.getRelativeUser().getEmail();

              QuestionAndAnswer questionAndAnswer = new QuestionAndAnswer();
              questionAndAnswer.setTitle(ori.getTitle());
              questionAndAnswer.setContent(ori.getContent());
              questionAndAnswer.setRegDate(ori.getCreatedDate());
              questionAndAnswer.setFullName(ori.getRelativeUser().getFullName());

              EBAnswer answer = new EBAnswer();
              answer.setContent(qna.getAnswer().getContent());
              questionAndAnswer.setAnswer(answer);

              if (ori.getCategories() != null) {
                questionAndAnswer.setCategories(ori.getCategories().stream()
                    .map(category -> category.getName().getValue())
                    .collect(Collectors.joining(",")));
              }

              Map<String, Object> model = new HashMap<>();
              model.put("subject", subject);
              model.put("email", email);
              model.put("questionAndAnswer", questionAndAnswer);
              model.put("nowDate", LocalDate.now());

              emailService.send(email, subject, model, "email/qna-answer.ftl");
            }
          }
          */

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
}
