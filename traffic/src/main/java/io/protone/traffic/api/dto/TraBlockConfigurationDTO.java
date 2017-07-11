package io.protone.traffic.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.core.domain.enumeration.CorDayOfWeekEnum;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * TraBlockConfigurationDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class TraBlockConfigurationDTO implements Serializable {
    @JsonProperty("description")
    private String description = null;

    @JsonProperty("id")
    private Long id = null;

    @NotNull
    private String name = null;

    @JsonProperty("length")
    private Long length = null;

    @NotNull
    private Long startBlock = null;

    @NotNull
    private Long stopBlock = null;

    @NotNull
    private Integer sequence = null;


    @NotNull
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

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public TraBlockConfigurationDTO sequence(Integer sequence) {
        this.sequence = sequence;
        return this;
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
    public String toString() {
        return "TraBlockConfigurationDTO{" +
            "description='" + description + '\'' +
            ", id=" + id +
            ", name='" + name + '\'' +
            ", length=" + length +
            ", startBlock=" + startBlock +
            ", stopBlock=" + stopBlock +
            ", sequence=" + sequence +
            ", day=" + day +
            '}';
    }
    @Override
    public int hashCode() {
        return Objects.hash(description, id, name, length, day);
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

