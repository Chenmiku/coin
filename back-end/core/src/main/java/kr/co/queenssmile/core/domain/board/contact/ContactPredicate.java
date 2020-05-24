package kr.co.queenssmile.core.domain.board.contact;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@NoArgsConstructor(staticName = "getInstance")
public class ContactPredicate {

    public static final QContact Q_CONTACT = QContact.contact;

    private BooleanBuilder builder = new BooleanBuilder();

    public Predicate values() {
        return builder.getValue() == null ? builder.and(Q_CONTACT.id.isNotNull()) : builder.getValue();
    }

    public ContactPredicate startDate(final LocalDateTime startDate) {

        if (startDate != null) {
            builder.and(Q_CONTACT.createdDate.goe(startDate));
        }
        return this;
    }

    public ContactPredicate endDate(final LocalDateTime endDate) {

        if (endDate != null) {
            builder.and(Q_CONTACT.createdDate.loe(endDate));
        }
        return this;
    }


    public ContactPredicate search(String value) {

        if (!StringUtils.isEmpty(value)) {
            value = value.trim();

            builder.and(Q_CONTACT.content.containsIgnoreCase(value)
                    .or(Q_CONTACT.fullName.eq(value))
                    .or(Q_CONTACT.email.eq(value))
                    .or(Q_CONTACT.mobile.eq(value))
//                .or(Q_CONTACT.title.containsIgnoreCase(value))
            );
        }
        return this;
    }
}
