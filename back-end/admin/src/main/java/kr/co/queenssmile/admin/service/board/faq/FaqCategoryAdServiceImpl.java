package kr.co.queenssmile.admin.service.board.faq;

import com.google.common.collect.Lists;
import kr.co.queenssmile.core.config.exception.BadRequestException;
import kr.co.queenssmile.core.domain.board.faq.category.FaqCategory;
import kr.co.queenssmile.core.domain.board.faq.category.FaqCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@Service
public class FaqCategoryAdServiceImpl implements FaqCategoryAdService {

  @Autowired
  private FaqCategoryRepository categoryRepository;

  @Override
  @Transactional
  public FaqCategory create(FaqCategory category) {

    // Order
    Long highestOrder = categoryRepository.highestOrder();
    category.setOrderAscending(highestOrder == null ? 0L : highestOrder + 1);

    return categoryRepository.save(category);
  }

  @Override
  @Transactional
  public FaqCategory update(FaqCategory category) {
    if (category.getId() == null) {
      throw new BadRequestException();
    }

    return categoryRepository.findById(category.getId())
        .map(ori -> {
          BeanUtils.copyProperties(category, ori, FaqCategory.IGNORE_PROPERTIES);
          return categoryRepository.save(ori);
        }).orElseThrow(BadRequestException::new);
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

          if (Objects.equals(mode, "UP")) {

            List<FaqCategory> previous = categoryRepository.previous(category.getOrderAscending(), PageRequest.of(0, 1));

            if (previous != null && previous.size() > 0) {
              category.changeOrder(previous.get(0));
            }
          } else if (Objects.equals(mode, "DOWN")) {

            List<FaqCategory> next = categoryRepository.next(category.getOrderAscending(), PageRequest.of(0, 1));

            if (next != null && next.size() > 0) {
              category.changeOrder(next.get(0));
            }
          }
        });
  }

  @Override
  @Transactional(readOnly = true)
  public FaqCategory get(Locale locale, Long id) {

    return categoryRepository.findById(id)
        .map(category -> {
          category.setLocale(locale);
          return category;
        }).orElse(null);
  }

  @Override
  @Transactional(readOnly = true)
  public List<FaqCategory> list(Locale locale) {

    List<FaqCategory> list = Lists.newArrayList(categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "orderAscending")));
    list.forEach(category -> category.setLocale(locale));
    return list;
  }

  @Override
  public boolean isDuplicate(Locale locale, String name) {

    if (locale.equals(Locale.KOREA)) {
      return categoryRepository.existsByNameAndKoKr(name);
    } else if (locale.equals(Locale.US)) {
      return categoryRepository.existsByNameAndEnUs(name);
    } else if (locale.equals(Locale.CHINA)) {
      return categoryRepository.existsByNameAndZhCn(name);
    } else if (locale.equals(Locale.TAIWAN)) {
      return categoryRepository.existsByNameAndZhTw(name);
    } else if (locale.equals(Locale.JAPAN)) {
      return categoryRepository.existsByNameAndJaJp(name);
    }
    return false;
  }
}
