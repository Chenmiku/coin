package kr.co.queenssmile.admin.service.board.faq;

import kr.co.queenssmile.core.config.exception.BadRequestException;
import kr.co.queenssmile.core.domain.board.faq.Faq;
import kr.co.queenssmile.core.domain.board.faq.FaqPredicate;
import kr.co.queenssmile.core.domain.board.faq.FaqRepository;
import kr.co.queenssmile.core.model.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@Service
public class FaqAdServiceImpl implements FaqAdService {

  @Autowired
  private FaqRepository faqRepository;

  @Override
  @Transactional
  public Faq create(Faq faq) {

    // ORDER
    Long highestOrder = faqRepository.highestOrder();
    faq.setOrderAscending(highestOrder == null ? 0L : highestOrder + 1);

    return faqRepository.save(faq);
  }

  @Override
  @Transactional
  public Faq update(Faq faq) {

    if (faq.getId() == null) {
      throw new BadRequestException();
    }

    return faqRepository.findById(faq.getId())
        .map(ori -> {
          BeanUtils.copyProperties(faq, ori, Faq.IGNORE_PROPERTIES);
          return faqRepository.save(ori);
        }).orElseThrow(BadRequestException::new);
  }

  @Override
  @Transactional
  public void delete(Long id) {

    faqRepository.findById(id)
        .ifPresent(faq -> {
          faq.delete();
          faqRepository.delete(faq);
        });
  }

  @Override
  @Transactional
  public void changeOrder(Long id, String mode) {

    faqRepository.findById(id)
        .ifPresent(faq -> {

          if (Objects.equals(mode, "UP")) {

            List<Faq> previous = faqRepository.previous(faq.getOrderAscending(), PageRequest.of(0, 1));

            if (previous != null && previous.size() > 0) {
              faq.changeOrder(previous.get(0));
            }
          } else if (Objects.equals(mode, "DOWN")) {

            List<Faq> next = faqRepository.next(faq.getOrderAscending(), PageRequest.of(0, 1));

            if (next != null && next.size() > 0) {
              faq.changeOrder(next.get(0));
            }
          }
        });
  }


  @Override
  @Transactional(readOnly = true)
  public Faq get(Locale locale, Long id) {
    return this.get(locale, id, null);
  }

  @Override
  @Transactional(readOnly = true)
  public Faq get(Locale locale, Long id, Boolean isActive) {

    return faqRepository.findOne(
        FaqPredicate.getInstance()
            .id(id)
            .active(isActive)
            .values())
        .map(faq -> {
          faq.setLocale(locale);
          faq.lazy();
          return faq;
        }).orElse(null);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Faq> page(Locale locale, Filter filter, Boolean isActive, Long idCategory) {

    Page<Faq> page = faqRepository.findAll(
        FaqPredicate.getInstance()
            .search(filter.getQuery())
            .startDate(filter.getStartDate())
            .endDate(filter.getEndDate())
            .active(isActive)
            .category(idCategory)
            .values(),
        filter.getPageable());

    page.forEach(faq -> {
      faq.setLocale(locale);
      faq.lazy();
    });

    return page;
  }
}
