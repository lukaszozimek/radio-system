package io.protone.scheduler.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.scheduler.domain.enumeration.EventTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import uk.co.jemos.podam.common.PodamExclude;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * SchBlockDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class SchBlockDTO {

    @PodamExclude
    @JsonProperty("blocks")
    private List<SchBlockDTO> blocks = new ArrayList<SchBlockDTO>();

    @PodamExclude
    @JsonProperty("emissions")
    private List<SchEmissionDTO> emissions = new ArrayList<SchEmissionDTO>();

    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("length")
    private Long length = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("timeParams")
    private SchTimeParamsDTO timeParams = null;

    @JsonProperty("queueParams")
    private SchQueueParamsDTO queueParams = null;

    @JsonProperty("schEventType")
    private EventTypeEnum eventType = null;

    public SchBlockDTO blocks(List<SchBlockDTO> blocks) {
        this.blocks = blocks;
        return this;
    }

    public SchBlockDTO addBlocksItem(SchBlockDTO blocksItem) {
        this.blocks.add(blocksItem);
        return this;
    }

    /**
     * Get blocks
     *
     * @return blocks
     **/
    @ApiModelProperty(value = "")
    public List<SchBlockDTO> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<SchBlockDTO> blocks) {
        this.blocks = blocks;
    }

    public SchBlockDTO emissions(List<SchEmissionDTO> emissions) {
        this.emissions = emissions;
        return this;
    }

    public SchBlockDTO addEmissionsItem(SchEmissionDTO emissionsItem) {
        this.emissions.add(emissionsItem);
        return this;
    }

    /**
     * Get emissions
     *
     * @return emissions
     **/
    @ApiModelProperty(value = "")
    public List<SchEmissionDTO> getEmissions() {
        return emissions;
    }

    public void setEmissions(List<SchEmissionDTO> emissions) {
        this.emissions = emissions;
    }

    public SchBlockDTO id(Long id) {
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

    public SchBlockDTO length(Long length) {
        this.length = length;
        return this;
    }

    /**
     * Get length
     *
     * @return length
     **/
    @ApiModelProperty(value = "")
    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public SchBlockDTO name(String name) {
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

    public SchBlockDTO timeParams(SchTimeParamsDTO timeParams) {
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

    public SchBlockDTO queueParams(SchQueueParamsDTO queueParams) {
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

    public SchBlockDTO eventType(EventTypeEnum eventType) {
        this.eventType = eventType;
        return this;
    }

    /**
     * Get schEventType
     *
     * @return schEventType
     **/
    @ApiModelProperty(value = "")
    public EventTypeEnum getEventType() {
        return eventType;
    }

    public void setEventType(EventTypeEnum eventType) {
        this.eventType = eventType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchBlockDTO schBlockDTO = (SchBlockDTO) o;
        return Objects.equals(this.blocks, schBlockDTO.blocks) &&
                Objects.equals(this.emissions, schBlockDTO.emissions) &&
                Objects.equals(this.id, schBlockDTO.id) &&
                Objects.equals(this.length, schBlockDTO.length) &&
                Objects.equals(this.name, schBlockDTO.name) &&
                Objects.equals(this.timeParams, schBlockDTO.timeParams) &&
                Objects.equals(this.queueParams, schBlockDTO.queueParams) &&
                Objects.equals(this.eventType, schBlockDTO.eventType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blocks, emissions, id, length, name, timeParams, queueParams, eventType);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SchBlockDTO {\n");

        sb.append("    blocks: ").append(toIndentedString(blocks)).append("\n");
        sb.append("    emissions: ").append(toIndentedString(emissions)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    length: ").append(toIndentedString(length)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    timeParams: ").append(toIndentedString(timeParams)).append("\n");
        sb.append("    queueParams: ").append(toIndentedString(queueParams)).append("\n");
        sb.append("    eventType: ").append(toIndentedString(eventType)).append("\n");
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

