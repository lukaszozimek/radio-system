package io.protone.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the TraAdvertisement entity.
 */
public class TraAdvertisementDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String name;

    private String description;


    private Long mediaItemId;

    private Long customerId;

    private Long industryId;

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
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getMediaItemId() {
        return mediaItemId;
    }

    public void setMediaItemId(Long lIBMediaItemId) {
        this.mediaItemId = lIBMediaItemId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long tRACustomerId) {
        this.customerId = tRACustomerId;
    }

    public Long getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Long tRAIndustryId) {
        this.industryId = tRAIndustryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TraAdvertisementDTO tRAAdvertisementDTO = (TraAdvertisementDTO) o;

        if ( ! Objects.equals(id, tRAAdvertisementDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TraAdvertisementDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
