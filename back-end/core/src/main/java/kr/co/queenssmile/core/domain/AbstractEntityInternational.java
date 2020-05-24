package kr.co.queenssmile.core.domain;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import kr.co.queenssmile.core.domain.International.InternationalMode;
import kr.co.queenssmile.core.utils.LocaleUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.Locale;
import java.util.Objects;

@MappedSuperclass
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY) // 모든 필드에 json 적용
@JsonInclude(value = JsonInclude.Include.ALWAYS) // 모든 데이터에 json 적용
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntityInternational<K extends java.io.Serializable> extends AbstractEntity<K> {

    private static final long serialVersionUID = 1199299179847861664L;
    
    @Getter
    @JsonIgnore
    @Transient
    protected Locale locale; // 언어

    public void setLocale(Locale locale) {

        this.locale = locale;
        if (this.locale == null) {
            this.locale = LocaleUtils.init();
        }
    }

    @Setter
    @Getter
    @JsonIgnore
    @Embedded
    protected InternationalMode internationalMode;

    public boolean isLocale(Locale locale, final Locale defaultLocale) {

        if (!Objects.equals(locale, Locale.KOREA)
                && Objects.equals(locale, Locale.US)
                && Objects.equals(locale, Locale.CHINA)
                && Objects.equals(locale, Locale.TAIWAN)
                && Objects.equals(locale, Locale.JAPAN)) {
            locale = defaultLocale;
        }

        if (Objects.equals(locale, Locale.KOREA)) {
            return this.internationalMode.isKoKr();
        } else if (Objects.equals(locale, Locale.US)) {
            return this.internationalMode.isEnUs();
        } else if (Objects.equals(locale, Locale.CHINA)) {
            return this.internationalMode.isZhCn();
        } else if (Objects.equals(locale, Locale.TAIWAN)) {
            return this.internationalMode.isZhTw();
        } else if (Objects.equals(locale, Locale.JAPAN)) {
            return this.internationalMode.isJaJp();
        }

        return false;
    }
}