package io.protone.scheduler.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.core.api.dto.CorDictionaryDTO;
import io.protone.scheduler.domain.enumeration.EventTypeEnum;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * SchEventDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class SchEventDTO {
    @JsonProperty("emissions")
    private List<SchEventEmissionDTO> emissions = new ArrayList<SchEventEmissionDTO>();

    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("name")
    private String name = null;
    @JsonProperty("shortName")
    private String shortName = null;
    @JsonProperty("queueParams")
    private SchQueueParamsDTO queueParams = null;
    @JsonProperty("eventType")
    private EventTypeEnum eventType = null;
    @JsonProperty("timeParams")
    private SchTimeParamsDTO timeParams = null;

    private Set<SchEventDTO> blocks = new HashSet<>();

    private CorDictionaryDTO eventCategory;

    public SchEventDTO emissions(List<SchEventEmissionDTO> emissions) {
        this.emissions = emissions;
        return this;
    }

    public SchEventDTO addEmissionsItem(SchEventEmissionDTO emissionsItem) {
        this.emissions.add(emissionsItem);
        return this;
    }

    /**
     * Get emissions
     *
     * @return emissions
     **/
    @ApiModelProperty(value = "")
    public List<SchEventEmissionDTO> getEmissions() {
        return emissions;
    }

    public void setEmissions(List<SchEventEmissionDTO> emissions) {
        this.emissions = emissions;
    }

    public SchEventDTO id(Long id) {
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

    public SchEventDTO name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get name
     *
     * @return name
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull
    @Size(min = 0, max = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SchEventDTO queueParams(SchQueueParamsDTO queueParams) {
        this.queueParams = queueParams;
        return this;
    }

    /**
     * Get queueParams
     *
     * @return queueParams
     **/
    @ApiModelProperty(value = "")
    public SchQueueParamsDTO getQueueParams() {
        return queueParams;
    }

    public void setQueueParams(SchQueueParamsDTO queueParams) {
        this.queueParams = queueParams;
    }

    public SchEventDTO eventType(EventTypeEnum eventType) {
        this.eventType = eventType;
        return this;
    }

    /**
     * Get eventType
     *
     * @return eventType
     **/
    @ApiModelProperty(value = "")
    public EventTypeEnum getEventType() {
        return eventType;
    }

    public void setEventType(EventTypeEnum eventType) {
        this.eventType = eventType;
    }

    public SchEventDTO timeParams(SchTimeParamsDTO timeParams) {
        this.timeParams = timeParams;
        return this;
    }

    /**
     * Get timeParams
     *
     * @return timeParams
     **/
    @ApiModelProperty(value = "")
    public SchTimeParamsDTO getTimeParams() {
        return timeParams;
    }

    public void setTimeParams(SchTimeParamsDTO timeParams) {
        this.timeParams = timeParams;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Set<SchEventDTO> getBlocks() {
        return blocks;
    }

    public void setBlocks(Set<SchEventDTO> events) {
        this.blocks = events;
    }

    public void setEventCategory(CorDictionaryDTO eventCategory) {
        this.eventCategory = eventCategory;

    }

    public CorDictionaryDTO getEventCategory() {
        return eventCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchEventDTO schEventDTO = (SchEventDTO) o;
        return Objects.equals(this.emissions, schEventDTO.emissions) &&
                Objects.equals(this.id, schEventDTO.id) &&
                Objects.equals(this.name, schEventDTO.name) &&
                Objects.equals(this.queueParams, schEventDTO.queueParams) &&
                Objects.equals(this.eventType, schEventDTO.eventType) &&
                Objects.equals(this.timeParams, schEventDTO.timeParams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emissions, id, name, queueParams, eventType, timeParams);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SchEventDTO {\n");

        sb.append("    emissions: ").append(toIndentedString(emissions)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    queueParams: ").append(toIndentedString(queueParams)).append("\n");
        sb.append("    eventType: ").append(toIndentedString(eventType)).append("\n");
        sb.append("    timeParams: ").append(toIndentedString(timeParams)).append("\n");
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

