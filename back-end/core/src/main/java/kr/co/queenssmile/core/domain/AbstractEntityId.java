package kr.co.queenssmile.core.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude
public abstract class AbstractEntityId implements java.io.Serializable {

    private static final long serialVersionUID = -3418089056897356473L;

    public abstract String toString();

    public abstract boolean equals(Object o);

    public abstract int hashCode();

}
