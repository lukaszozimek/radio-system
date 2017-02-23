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

    private Long libitemId;

    private Long industryId;

    private Long typeId;

    private Long customerId;

    private Long networkId;

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

    public void setMediaItemId(Long libMediaItemId) {
        this.mediaItemId = libMediaItemId;
    }

    public Long getLibitemId() {
        return libitemId;
    }

    public void setLibitemId(Long libMediaItemId) {
        this.libitemId = libMediaItemId;
    }

    public Long getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Long traIndustryId) {
        this.industryId = traIndustryId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long traAdvertismentTypeId) {
        this.typeId = traAdvertismentTypeId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long crmAccountId) {
        this.customerId = crmAccountId;
    }

    public Long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Long corNetworkId) {
        this.networkId = corNetworkId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TraAdvertisementDTO traAdvertisementDTO = (TraAdvertisementDTO) o;

        if ( ! Objects.equals(id, traAdvertisementDTO.id)) { return false; }

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
