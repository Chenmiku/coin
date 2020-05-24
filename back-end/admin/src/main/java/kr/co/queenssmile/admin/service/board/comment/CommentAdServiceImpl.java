package kr.co.queenssmile.admin.service.board.comment;

import kr.co.queenssmile.core.config.exception.BadRequestException;
import kr.co.queenssmile.core.config.exception.crud.UpdateErrorException;
import kr.co.queenssmile.core.domain.board.comment.Comment;
import kr.co.queenssmile.core.domain.board.comment.CommentPredicate;
import kr.co.queenssmile.core.domain.board.comment.CommentRepository;
import kr.co.queenssmile.core.model.Filter;
import kr.co.queenssmile.core.service.setting.AppSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CommentAdServiceImpl implements CommentAdService {


  @Autowired
  private CommentRepository eventRepository;

  @Autowired
  private AppSettingService appSettingService;


  @Override
  @Transactional
  public Comment create(Comment comment) {
    return eventRepository.save(comment);
  }

  @Override
  @Transactional
  public Comment update(Comment event) {

    if (event.getId() == null) {
      throw new BadRequestException();
    }

    return eventRepository.findById(event.getId())
        .map(ori -> {
          BeanUtils.copyProperties(event, ori, Comment.IGNORE_PROPERTIES);
          return eventRepository.save(ori);
        }).orElseThrow(() -> new UpdateErrorException(event.getId(), Comment.class.getName()));
  }

  @Override
  @Transactional
  public void delete(Long id) {
    eventRepository.findById(id)
        .ifPresent(event -> {
          event.delete();
          eventRepository.delete(event);
        });
  }

  @Override
  @Transactional(readOnly = true)
  public Comment get(java.util.Locale locale, Long id) {
    return eventRepository.findById(id)
        .map(event -> {
          event.lazy();
          return event;
        }).orElse(null);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Comment> pageByEvent(java.util.Locale locale, Filter filter) {

    Page<Comment> page = eventRepository.findAll(
        CommentPredicate.getInstance()
            .search(filter.getQuery())
            .startDate(filter.getStartDate())
            .endDate(filter.getEndDate())
            .hasEvent()
            .values(),
        filter.getPageable());

    page.forEach(event -> {
      event.lazy();
    });
    return page;
  }
}
