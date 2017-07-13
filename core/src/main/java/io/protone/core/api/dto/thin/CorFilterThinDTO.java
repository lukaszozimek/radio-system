package io.protone.core.api.dto.thin;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lukaszozimek on 13/07/2017.
 */
public class CorFilterThinDTO {


    @JsonProperty("value")
    private String value = null;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CorFilterThinDTO that = (CorFilterThinDTO) o;

        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CorFi{" +
                "value='" + value + '\'' +
                '}';
    }
}
