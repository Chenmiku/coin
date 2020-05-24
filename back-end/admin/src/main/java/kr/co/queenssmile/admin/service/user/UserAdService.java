package kr.co.queenssmile.admin.service.user;

import kr.co.queenssmile.core.domain.user.Authority;
import kr.co.queenssmile.core.domain.user.User;
import kr.co.queenssmile.core.model.Filter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 관리자 전용 비즈니스 로직
 */
//@PreAuthorize("hasAnyRole('ROLE_SUPER', 'ROLE_ADMIN')")
public interface UserAdService {

    // CUD
    User create(User user);
    User update(User user);
    void delete(Long id);
    void leave(Long id);
    void removePrivacy(Long id);
    void restoreLeave(Long id);

    // R
    User get(Long id);
    List<User> list(Filter filter);
    Page<User> page(Filter filter, Authority.Role role);
    Page<User> pageByManager(Filter filter, Authority.Role role);
    Authority authority(Authority.Role role);  // 권한 불러오기

    boolean isDuplicateEmail(String email);
    boolean isDuplicateMobile(String mobile);

    // 비밀번호 재설정 (아이디, 병견할 비밀번호)
    void resetPassword(Long id, String password);
}
