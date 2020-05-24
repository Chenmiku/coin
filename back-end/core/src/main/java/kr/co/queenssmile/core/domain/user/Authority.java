package kr.co.queenssmile.core.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public class Authority implements java.io.Serializable, Comparable<Authority> {

    private static final long serialVersionUID = -1863041203652704913L;

    @Getter
    public enum Role {
        SUPER("ROLE_SUPER", "SUPER", 10),
        ADMIN("ROLE_ADMIN", "ADMIN", 9),
        WRITER("ROLE_WRITER", "WRITER", 3),
        House_Hold("ROLE_House_Hold", "House_Hold", 2),
        AGENCY("ROLE_AGENCY", "AGENCY", 2),
        USER("ROLE_USER", "USER", 1);

        private final String role;
        private final String text;
        private final int level; // desc 가장 높은 권한 보여주기

        Role(final String role, final String text, final int level) {
            this.role = role;
            this.text = text;
            this.level = level;
        }

        @Override
        public String toString() {
            return this.getText();
        }

        public static Role toRole(String role) {
            if (role != null) {
                if (role.equals(SUPER.role)) {
                    return SUPER;
                } else if (role.equals(ADMIN.role)) {
                    return ADMIN;
                } else if (role.equals(WRITER.role)) {
                    return WRITER;
                } else if (role.equals(AGENCY.role)) {
                    return AGENCY;
                } else if (role.equals(House_Hold.role)) {
                    return House_Hold;
                } else if (role.equals(USER.role)) {
                    return USER;
                }
            }
            return null;
        }
    }

    @Id
    @GeneratedValue
    @Column(unique = true)
    private Long id;

    @NonNull
    @Column(length = 30, unique = true)
    @Enumerated(EnumType.STRING)
    private Role role;

    public int getLevel() {
        return role.getLevel();
    }

    @Override
    public String toString() {
        return this.role.getText();
    }

    @JsonIgnore
    @Transient
    @Getter
    @Setter
    private boolean checked;

    @Override
    public int compareTo(Authority authority) {
        return Integer.compare(authority.role.level, this.role.level);
    }
}
