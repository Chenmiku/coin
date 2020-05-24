package kr.co.queenssmile.admin.service.board.contact;

import kr.co.queenssmile.core.domain.board.contact.Contact;
import kr.co.queenssmile.core.model.Filter;
import org.springframework.data.domain.Page;

public interface ContactAdService {

  // CUD
  Contact create(Contact contact);

  Contact update(Contact contact);

  void delete(Long id);

  // R
  Contact get(Long id);

  Page<Contact> page(Filter filter);

}
