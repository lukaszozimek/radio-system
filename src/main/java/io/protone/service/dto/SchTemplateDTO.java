package io.protone.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the SchTemplate entity.
 */
public class SchTemplateDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String name;

    private Long channelId;

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

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long corChannelId) {
        this.channelId = corChannelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SchTemplateDTO schTemplateDTO = (SchTemplateDTO) o;

        if ( ! Objects.equals(id, schTemplateDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SchTemplateDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
