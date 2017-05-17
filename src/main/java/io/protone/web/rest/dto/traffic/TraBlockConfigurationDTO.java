package io.protone.web.rest.dto.traffic;

import java.time.LocalTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.protone.domain.enumeration.CorDayOfWeekEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.*;

/**
 * TraBlockConfigurationDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class TraBlockConfigurationDTO {
    @JsonProperty("description")
    private String description = null;

    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("length")
    private Long length = null;


    @JsonProperty("startBlock")
    private Long startBlock = null;

    @JsonProperty("stopBlock")
    private Long stopBlock = null;

    @JsonProperty("day")
    private CorDayOfWeekEnum day;

    public TraBlockConfigurationDTO description(String description) {
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

    public TraBlockConfigurationDTO id(Long id) {
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

    public TraBlockConfigurationDTO name(String name) {
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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TraBlockConfigurationDTO length(Long length) {
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

    public TraBlockConfigurationDTO day(CorDayOfWeekEnum day) {
        this.day = day;
        return this;
    }

    /**
     * Get dayOfWeek
     *
     * @return dayOfWeek
     **/
    @ApiModelProperty(value = "")
    public CorDayOfWeekEnum getDay() {
        return day;
    }

    public void setDay(CorDayOfWeekEnum day) {
        this.day = day;
    }

    public Long getStopBlock() {
        return stopBlock;
    }

    public void setStopBlock(Long stopBlock) {
        this.stopBlock = stopBlock;
    }

    public Long getStartBlock() {
        return startBlock;
    }

    public void setStartBlock(Long startBlock) {
        this.startBlock = startBlock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TraBlockConfigurationDTO traBlockConfigurationDTO = (TraBlockConfigurationDTO) o;
        return Objects.equals(this.description, traBlockConfigurationDTO.description) &&
            Objects.equals(this.id, traBlockConfigurationDTO.id) &&
            Objects.equals(this.name, traBlockConfigurationDTO.name) &&
            Objects.equals(this.length, traBlockConfigurationDTO.length) &&
            Objects.equals(this.day, traBlockConfigurationDTO.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, id, name, length, day);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TraBlockConfigurationDTO {\n");

        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    length: ").append(toIndentedString(length)).append("\n");
        sb.append("    dayOfWeek: ").append(toIndentedString(day)).append("\n");
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

