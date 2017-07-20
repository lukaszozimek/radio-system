package io.protone.core.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.core.domain.enumeration.CorEntityTypeEnum;

import javax.validation.constraints.NotNull;

/**
 * Created by lukaszozimek on 13/07/2017.
 */
public class CorFilterDTO {

    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("name")
    @NotNull
    private String name = null;


    @JsonProperty("value")
    @NotNull
    private String value = null;

    @JsonProperty("type")
    private CorEntityTypeEnum type = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public CorFilterDTO name(String name) {
        this.name = name;
        return this;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public CorFilterDTO value(String value) {
        this.value = value;
        return this;
    }


    public CorEntityTypeEnum getType() {
        return type;
    }

    public void setType(CorEntityTypeEnum typeEnum) {
        this.type = typeEnum;
    }

    public CorFilterDTO type(CorEntityTypeEnum typeEnum) {
        this.type = typeEnum;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CorFilterDTO that = (CorFilterDTO) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getValue() != null ? !getValue().equals(that.getValue()) : that.getValue() != null) return false;
        return getType() == that.getType();
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CorFilterDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", type=" + type +
                '}';
    }
}
