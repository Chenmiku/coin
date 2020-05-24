package kr.co.queenssmile.core.domain.board.qna.category;

public interface QnaCategoryRepositoryCustom {

    boolean existsByNameAndKoKr(String name);

    boolean existsByNameAndEnUs(String name);

    boolean existsByNameAndZhCn(String name);

    boolean existsByNameAndZhTw(String name);

    boolean existsByNameAndJaJp(String name);
}
