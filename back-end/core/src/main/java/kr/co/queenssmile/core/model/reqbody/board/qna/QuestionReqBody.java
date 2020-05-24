package kr.co.queenssmile.core.model.reqbody.board.qna;

import kr.co.queenssmile.core.domain.board.qna.Qna;
import kr.co.queenssmile.core.domain.board.qna.QnaNoMember;
import kr.co.queenssmile.core.domain.user.User;
import kr.co.queenssmile.core.model.BaseRequestBody;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class QuestionReqBody extends BaseRequestBody {

    private static final long serialVersionUID = -147872989563697266L;

    private boolean member;

    private Long idCategory;
    private String title;
    private String content;
    private Long idProduct;

    // 비회원
    private String fullname;
    private String mobile;
    private String password;

    public Qna toQna(User user) {
        Qna qna = new Qna();
        qna.setTitle(this.title);
        qna.setContent(this.content);
        qna.setIdCategory(this.idCategory);
        qna.setRelativeUser(user);
        qna.setMember(true);
        qna.setActive(true);
        return qna;
    }


    public Qna toQnaNoMember() {
        Qna qna = new Qna();
        qna.setTitle(this.title);
        qna.setContent(this.content);
        qna.setIdCategory(this.idCategory);
        qna.setMember(false);
        qna.setActive(true);

        QnaNoMember qnaNoMember = new QnaNoMember();
        qnaNoMember.setFullName(this.fullname);
        qnaNoMember.setMobile(this.mobile);
        qnaNoMember.setPassword(this.password);
        qna.setQnaNoMember(qnaNoMember);

        return qna;
    }


}
