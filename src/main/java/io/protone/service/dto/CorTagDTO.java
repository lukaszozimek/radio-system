package io.protone.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CorTag entity.
 */
public class CorTagDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String tag;

    private Long networkId;

    private Long tagsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Long corNetworkId) {
        this.networkId = corNetworkId;
    }

    public Long getTagsId() {
        return tagsId;
    }

    public void setTagsId(Long libMediaItemId) {
        this.tagsId = libMediaItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CorTagDTO corTagDTO = (CorTagDTO) o;

        if ( ! Objects.equals(id, corTagDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CorTagDTO{" +
            "id=" + id +
            ", tag='" + tag + "'" +
            '}';
    }
}
