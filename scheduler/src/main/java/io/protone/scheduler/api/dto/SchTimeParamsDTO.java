package io.protone.scheduler.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * SchTimeParamsDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class SchTimeParamsDTO extends SchBaseDTO {

    private Long sequence;

    @JsonProperty("relativeDelay")
    private Long relativeDelay = null;

    @JsonProperty("endTime")
    private LocalDateTime endTime = null;

    @JsonProperty("startTime")
    private LocalDateTime startTime = null;

    private Long length = null;

    @ApiModelProperty(value = "")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SchTimeParamsDTO relativeDelay(Long relativeDelay) {
        this.relativeDelay = relativeDelay;
        return this;
    }

    /**
     * Get relativeDelay
     *
     * @return relativeDelay
     **/
    @ApiModelProperty(value = "")
    public Long getRelativeDelay() {
        return relativeDelay;
    }

    public void setRelativeDelay(Long relativeDelay) {
        this.relativeDelay = relativeDelay;
    }

    public SchTimeParamsDTO endTime(LocalDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    /**
     * Get endTime
     *
     * @return endTime
     **/
    @ApiModelProperty(value = "")
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public SchTimeParamsDTO startTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    /**
     * Get startTime
     *
     * @return startTime
     **/
    @ApiModelProperty(value = "")
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchTimeParamsDTO schTimeParamsDTO = (SchTimeParamsDTO) o;
        return Objects.equals(this.relativeDelay, schTimeParamsDTO.relativeDelay) &&
                Objects.equals(this.endTime, schTimeParamsDTO.endTime) &&
                Objects.equals(this.startTime, schTimeParamsDTO.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(relativeDelay, endTime, startTime);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SchTimeParamsDTO {\n");

        sb.append("    relativeDelay: ").append(toIndentedString(relativeDelay)).append("\n");
        sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
        sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
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

