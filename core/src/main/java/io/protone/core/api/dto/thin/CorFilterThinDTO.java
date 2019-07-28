package io.protone.core.api.dto.thin;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.core.domain.enumeration.CorEntityTypeEnum;

/**
 * Created by lukaszozimek on 13/07/2017.
 */
public class CorFilterThinDTO {


    @JsonProperty("value")
    private String value = null;


    @JsonProperty("type")
    private CorEntityTypeEnum type = null;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;

    }

    public CorFilterThinDTO value(String value) {
        this.value = value;
        return this;
    }

    public CorEntityTypeEnum getType() {
        return type;
    }

    public void setType(CorEntityTypeEnum type) {
        this.type = type;
    }

    public CorFilterThinDTO type(CorEntityTypeEnum type) {
        this.type = type;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CorFilterThinDTO that = (CorFilterThinDTO) o;

        if (getValue() != null ? !getValue().equals(that.getValue()) : that.getValue() != null) return false;
        return getType() != null ? getType().equals(that.getType()) : that.getType() == null;
    }

    @Override
    public int hashCode() {
        int result = getValue() != null ? getValue().hashCode() : 0;
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CorFilterThinDTO{" +
                "value='" + value + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
