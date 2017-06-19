package io.protone.web.rest.dto.traffic.thin;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.web.rest.dto.cor.CorDictionaryDTO;
import io.protone.web.rest.dto.library.thin.LibMediaItemThinDTO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * TraAdvertisementDTO
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class TraAdvertisementThinDTO implements Serializable {
    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("industryId")
    private CorDictionaryDTO industryId = null;

    @JsonProperty("mediaItemId")
    private LibMediaItemThinDTO mediaItemId = null;

    @JsonProperty("typeId")
    private CorDictionaryDTO typeId;

    @JsonProperty("customerId")
    private Integer customerId;

    public TraAdvertisementThinDTO() {
    }


    public TraAdvertisementThinDTO type(CorDictionaryDTO typePT) {
        this.typeId = typePT;
        return this;
    }

    @ApiModelProperty(value = "")
    public CorDictionaryDTO getTypeId() {
        return typeId;
    }

    public void setTypeId(CorDictionaryDTO typeId) {
        this.typeId = typeId;
    }

    public TraAdvertisementThinDTO description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Get description
     *
     * @return description
     **/
    @ApiModelProperty(value = "")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TraAdvertisementThinDTO id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     **/
    @ApiModelProperty(value = "")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TraAdvertisementThinDTO industryId(CorDictionaryDTO industryId) {
        this.industryId = industryId;
        return this;
    }

    /**
     * Get industryId
     *
     * @return industryId
     **/
    @ApiModelProperty(value = "")
    public CorDictionaryDTO getIndustryId() {
        return industryId;
    }

    public void setIndustryId(CorDictionaryDTO industryId) {
        this.industryId = industryId;
    }

    public TraAdvertisementThinDTO mediaItemId(LibMediaItemThinDTO mediaItemId) {
        this.mediaItemId = mediaItemId;
        return this;
    }

    /**
     * Get mediaItemId
     *
     * @return mediaItemId
     **/
    @ApiModelProperty(value = "")
    public LibMediaItemThinDTO getMediaItemId() {
        return mediaItemId;
    }

    public void setMediaItemId(LibMediaItemThinDTO mediaItemId) {
        this.mediaItemId = mediaItemId;
    }

    public TraAdvertisementThinDTO name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get name
     *
     * @return name
     **/
    @ApiModelProperty(required = true, value = "")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TraAdvertisementThinDTO that = (TraAdvertisementThinDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (industryId != null ? !industryId.equals(that.industryId) : that.industryId != null) return false;
        if (mediaItemId != null ? !mediaItemId.equals(that.mediaItemId) : that.mediaItemId != null) return false;
        if (typeId != null ? !typeId.equals(that.typeId) : that.typeId != null) return false;
        if (customerId != null ? !customerId.equals(that.customerId) : that.customerId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (industryId != null ? industryId.hashCode() : 0);
        result = 31 * result + (mediaItemId != null ? mediaItemId.hashCode() : 0);
        result = 31 * result + (typeId != null ? typeId.hashCode() : 0);
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TraAdvertisementThinDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", industryId=" + industryId +
            ", mediaItemId=" + mediaItemId +
            ", typeId=" + typeId +
            ", customerId=" + customerId +
            '}';
    }
}

