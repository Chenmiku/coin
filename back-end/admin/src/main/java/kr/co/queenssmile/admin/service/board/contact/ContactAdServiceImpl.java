package kr.co.queenssmile.admin.service.board.contact;

import kr.co.queenssmile.core.config.exception.BadRequestException;
import kr.co.queenssmile.core.domain.board.contact.Contact;
import kr.co.queenssmile.core.domain.board.contact.ContactPredicate;
import kr.co.queenssmile.core.domain.board.contact.ContactRepository;
import kr.co.queenssmile.core.domain.user.UserRepository;
import kr.co.queenssmile.core.model.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ContactAdServiceImpl implements ContactAdService {

  @Autowired
  private ContactRepository contactRepository;

  @Autowired
  private UserRepository userRepository;

  @Override
  @Transactional
  public Contact create(Contact contact) {
    return contactRepository.save(contact);
  }

  @Override
  @Transactional
  public Contact update(Contact contact) {

    if (contact.getId() == null) {
      throw new BadRequestException();
    }

    return contactRepository.findById(contact.getId())
        .map(ori -> {
          BeanUtils.copyProperties(contact, ori, Contact.IGNORE_PROPERTIES);
          return contactRepository.save(ori);
        }).orElseThrow(BadRequestException::new);
  }

  @Override
  @Transactional
  public Contact get(Long id) {

    return contactRepository.findById(id)
        .map(contact -> {
          contact.lazy();
          return contact;
        }).orElse(null);
  }

  @Override
  @Transactional
  public void delete(Long id) {

    contactRepository.findById(id).ifPresent(ebContact -> {
      ebContact.delete();
      contactRepository.delete(ebContact);
    });
  }

  @Override
  @Transactional
  public Page<Contact> page(Filter filter) {

    Page<Contact> page = contactRepository.findAll(
        ContactPredicate.getInstance()
            .search(filter.getQuery())
            .startDate(filter.getStartDate())
            .endDate(filter.getEndDate())
            .values(),
        filter.getPageable());

    page.forEach(Contact::lazy);
    return page;
  }
  
}
