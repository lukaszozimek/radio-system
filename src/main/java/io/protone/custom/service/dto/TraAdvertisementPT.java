package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.web.rest.dto.cor.CorDictionaryDTO;
import io.protone.web.rest.dto.library.thin.LibMediaItemThinDTO;
import io.protone.web.rest.dto.traffic.thin.TraCustomerThinDTO;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * TraAdvertisementPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class TraAdvertisementPT {
    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("customerId")
    private TraCustomerThinDTO customerId = null;

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


    public TraAdvertisementPT type(CorDictionaryDTO typePT) {
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

    public TraAdvertisementPT customerId(TraCustomerThinDTO customerId) {
        this.customerId = customerId;
        return this;
    }

    /**
     * Get customerId
     *
     * @return customerId
     **/
    @ApiModelProperty(value = "")
    public TraCustomerThinDTO getCustomerId() {
        return customerId;
    }

    public void setCustomerId(TraCustomerThinDTO customerId) {
        this.customerId = customerId;
    }

    public TraAdvertisementPT description(String description) {
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

    public TraAdvertisementPT id(Long id) {
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

    public TraAdvertisementPT industryId(CorDictionaryDTO industryId) {
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

    public TraAdvertisementPT mediaItemId(LibMediaItemThinDTO mediaItemId) {
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

    public TraAdvertisementPT name(String name) {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TraAdvertisementPT traAdvertisementPT = (TraAdvertisementPT) o;
        return Objects.equals(this.customerId, traAdvertisementPT.customerId) &&
            Objects.equals(this.description, traAdvertisementPT.description) &&
            Objects.equals(this.id, traAdvertisementPT.id) &&
            Objects.equals(this.industryId, traAdvertisementPT.industryId) &&
            Objects.equals(this.mediaItemId, traAdvertisementPT.mediaItemId) &&
            Objects.equals(this.name, traAdvertisementPT.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, description, id, industryId, mediaItemId, name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TraAdvertisementPT {\n");

        sb.append("    customerId: ").append(toIndentedString(customerId)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    industryId: ").append(toIndentedString(industryId)).append("\n");
        sb.append("    mediaItemId: ").append(toIndentedString(mediaItemId)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

