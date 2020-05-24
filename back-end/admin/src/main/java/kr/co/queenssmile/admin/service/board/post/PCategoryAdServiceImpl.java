package kr.co.queenssmile.admin.service.board.post;

import kr.co.queenssmile.core.config.exception.BadRequestException;
import kr.co.queenssmile.core.domain.board.post.Post;
import kr.co.queenssmile.core.domain.board.post.category.PCategory;
import kr.co.queenssmile.core.domain.board.post.category.PCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@Service
public class PCategoryAdServiceImpl implements PCategoryAdService {

  @Autowired
  private PCategoryRepository categoryRepository;

  @Override
  @Transactional
  public PCategory create(PCategory category) {

    // ORDER
    Long highestOrder = categoryRepository.highestOrder(category.getType());
    category.setOrderAscending(highestOrder == null ? 0L : highestOrder + 1);

    return categoryRepository.save(category);
  }

  @Override
  @Transactional
  public PCategory update(PCategory category) {

    if (category.getId() == null) {
      throw new BadRequestException();
    }

    return categoryRepository.findById(category.getId())
      .map(ori -> {
        BeanUtils.copyProperties(category, ori, PCategory.IGNORE_PROPERTIES);
        return categoryRepository.save(ori);
      }).orElseThrow(BadRequestException::new);
  }

  @Override
  @Transactional(readOnly = true)
  public PCategory get(Locale locale, Long id) {

    return categoryRepository.findById(id)
      .map(category -> {
        category.lazy();
        category.setLocale(locale);
        return category;
      }).orElse(null);
  }

  @Override
  @Transactional
  public void delete(Long id) {

    categoryRepository.findById(id)
      .ifPresent(category -> {
        category.delete();
        categoryRepository.delete(category);
      });
  }

  @Override
  @Transactional
  public void changeOrder(Long id, String mode) {

    categoryRepository.findById(id)
      .ifPresent(category -> {

        final Post.Type type = category.getType();

        if (Objects.equals(mode, "UP")) {

          List<PCategory> previous = categoryRepository.previous(category.getOrderAscending(), type, PageRequest.of(0, 1));

          if (previous != null && previous.size() > 0) {
            category.changeOrder(previous.get(0));
          }
        } else if (Objects.equals(mode, "DOWN")) {

          List<PCategory> next = categoryRepository.next(category.getOrderAscending(), type, PageRequest.of(0, 1));

          if (next != null && next.size() > 0) {
            category.changeOrder(next.get(0));
          }
        }
      });
  }

  @Override
  @Transactional(readOnly = true)
  public List<PCategory> list(Locale locale, Post.Type type) {

    List<PCategory> list = categoryRepository.findByTypeOrderByOrderAscendingAsc(type);
    list.forEach(ebpCategory -> {
      ebpCategory.lazy();
      ebpCategory.setLocale(locale);
    });
    return list;
  }

  @Override
  public boolean isDuplicate(Locale locale, String name, Post.Type type) {

    if (locale.equals(Locale.KOREA)) {
      return categoryRepository.existsByNameAndTypeAndKoKr(name, type);
    } else if (locale.equals(Locale.US)) {
      return categoryRepository.existsByNameAndTypeAndEnUs(name, type);
    } else if (locale.equals(Locale.CHINA)) {
      return categoryRepository.existsByNameAndTypeAndZhCn(name, type);
    } else if (locale.equals(Locale.TAIWAN)) {
      return categoryRepository.existsByNameAndTypeAndZhTw(name, type);
    } else if (locale.equals(Locale.JAPAN)) {
      return categoryRepository.existsByNameAndTypeAndJaJp(name, type);
    }
    return false;

  }
}
