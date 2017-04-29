package io.protone.web.rest.dto.thin;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.custom.service.dto.CorDictionaryPT;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * TraAdvertisementPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class TraAdvertisementThinDTO {
    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("industryId")
    private CorDictionaryPT industryId = null;

    @JsonProperty("mediaItemId")
    private LibMediaItemThinDTO mediaItemId = null;

    @JsonProperty("typeId")
    private CorDictionaryPT typeId;


    public TraAdvertisementThinDTO type(CorDictionaryPT typePT) {
        this.typeId = typePT;
        return this;
    }

    @ApiModelProperty(value = "")
    public CorDictionaryPT getTypeId() {
        return typeId;
    }

    public void setTypeId(CorDictionaryPT typeId) {
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

    public TraAdvertisementThinDTO industryId(CorDictionaryPT industryId) {
        this.industryId = industryId;
        return this;
    }

    /**
     * Get industryId
     *
     * @return industryId
     **/
    @ApiModelProperty(value = "")
    public CorDictionaryPT getIndustryId() {
        return industryId;
    }

    public void setIndustryId(CorDictionaryPT industryId) {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TraAdvertisementThinDTO traAdvertisementPT = (TraAdvertisementThinDTO) o;
        return
            Objects.equals(this.description, traAdvertisementPT.description) &&
                Objects.equals(this.id, traAdvertisementPT.id) &&
                Objects.equals(this.industryId, traAdvertisementPT.industryId) &&
                Objects.equals(this.mediaItemId, traAdvertisementPT.mediaItemId) &&
                Objects.equals(this.name, traAdvertisementPT.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, id, industryId, mediaItemId, name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TraAdvertisementPT {\n");
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

