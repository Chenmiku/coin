package kr.co.queenssmile.api.service.user;

import kr.co.queenssmile.core.domain.user.Authority;
import kr.co.queenssmile.core.domain.user.SocialId;
import kr.co.queenssmile.core.domain.user.TermsAgree;
import kr.co.queenssmile.core.domain.user.User;
import kr.co.queenssmile.core.model.file.FileMeta;
import kr.co.queenssmile.core.model.reqbody.user.*;
import kr.co.queenssmile.core.model.resbody.user.FindUserResBody;
import kr.co.queenssmile.core.model.resbody.user.MeResBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

public interface UserService {

    // CRUD
    User get(Long id);
    User get(String email);
    Authority authority(Authority.Role role);

    // 회원가입
    User join(SignUpReqBody signUpReqBody);

    // 로그인
    String getJWT(Long id);
    String getJWT(User user);
    void login(Long id);
    MeResBody profile(String email, Locale locale);

    // (REST) 로그인
//    ResponseEntity authAccessToken(LoginReqBody restLoginReqBody, HttpServletRequest request);
    ResponseEntity authAccessToken(LoginReqBody restLoginReqBody, HttpServletRequest request);
    ResponseEntity getAccessToken(LoginReqBody restLoginReqBody);
    // 프로필 이미지 업데이트
    FileMeta updateProfileImage(MultipartFile file, String email);

    // 변경 (비밀번호)
    void changePassword(ChangePasswordReqBody changePasswordReqBody, String email);
    void resetPasswordByEmail(String newPassword, String email);

    // 변경 (사용자 정보)
    void changeUser(String email, UpdateUserReqBody updateUserReqBody);

    void changeFullName(String fullName, String email);

    // 변경 (모바일)
    void changeMobile(CertConfirmReqBody confirm, String email);

    // 변경 (이메일)
    void changeEmail(CertReqBody cert, String email, HttpServletRequest request);

    // 변경 (SNS)
    void changeSns(ChangeSnsReqBody changeSnsReqBody, String email);
    void changeSnsNative(ChangeSnsNativeReqBody changeSnsReqBody, String email);

    // Native 변경 (SNS)
    void changeSns(SocialId socialId, String email);

    // 패스워드 일치 검사
    boolean matchPassword(User user, String password);
    boolean matchPassword(String email, String password);

    // 회원탈퇴
    void leave(Long id, String reason);
    // 회원탈퇴
    void deleteMember(Long id);
    // 개인정보 삭제
    void removePrivacy(Long id);

    // 아이디 찾기 (모바일)
    void findAccountByMobile(String mobile, HttpServletRequest request);
    // 아이디 찾기 확인 (모바일)
    List<FindUserResBody> findAccountByMobileConfirm(FindAccountReqBody findAccountReqBody);


    void findPasswordByMobile(String mobile, String email, String fullName, HttpServletRequest request);
    ResponseEntity findPasswordByMobileConfirm(FindPasswordReqBody findPasswordReqBody);
    // 패스워드 찾기 (이메일)
    String findPasswordByEmail(String email, HttpServletRequest request);

    // 비밀번호 만료 회원
    boolean isExpiredPassword(User user);

    // 이메일 수신동의 변경
    TermsAgree subscribeByEmail(boolean subscribe, String email);
    // 문자 수신동의 변경
    TermsAgree subscribeBySMS(boolean subscribe, String email);
    // 카카오톡 수신동의 변경
    TermsAgree subscribeByKakao(boolean subscribe, String email);

    List<User> userByAdmin();

    // 아이디 찾기 (모바일)
    void dormantByMobile(String mobile, HttpServletRequest request, DormantReqBody reqBody) ;
    // 아이디 찾기 확인 (모바일)
    void dormantByMobileConfirm(DormantReqBody dormantReqBody);

    void setDormant();

    void updateUserInfo(String email, UpdateUserInfoReqBody updateUserInfoReqBody);
    Boolean checkSuperAdminAuthAccess(User user);
    Boolean checkAdminAuthAccess(User user);
    Boolean checkAgencyAuthAccess(User user);
    Boolean checkWriteAuthAccess(User user);
}
