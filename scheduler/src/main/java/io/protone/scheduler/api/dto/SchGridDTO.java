package io.protone.scheduler.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.core.domain.enumeration.CorDayOfWeekEnum;
import io.swagger.annotations.ApiModelProperty;
import uk.co.jemos.podam.common.PodamExclude;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * SchGridDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class SchGridDTO {

    @PodamExclude
    @JsonProperty("clocks")
    private List<SchClockConfigurationDTO> clocks = new ArrayList<SchClockConfigurationDTO>();

    @JsonProperty("dayOfWeek")
    private CorDayOfWeekEnum dayOfWeek = null;

    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("shortName")
    private String shortName = null;


    @JsonProperty("default")
    private Boolean defaultGrid = null;

    public SchGridDTO clocks(List<SchClockConfigurationDTO> clocks) {
        this.clocks = clocks;
        return this;
    }

    public SchGridDTO addClock(SchClockConfigurationDTO clock) {
        this.clocks.add(clock);
        return this;
    }

    public SchGridDTO removeClock(SchClockConfigurationDTO clock) {
        this.clocks.remove(clock);
        return this;
    }

    public Boolean getDefaultGrid() {
        return defaultGrid;
    }

    public void setDefaultGrid(Boolean defaultGrid) {
        this.defaultGrid = defaultGrid;
    }

    /**
     * Get clocks
     *
     * @return clocks
     **/
    @ApiModelProperty(value = "")
    public List<SchClockConfigurationDTO> getClocks() {
        return clocks;
    }

    public void setClocks(List<SchClockConfigurationDTO> clocks) {
        this.clocks = clocks;
    }

    public SchGridDTO dayOfWeek(CorDayOfWeekEnum dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }

    /**
     * Get dayOfWeek
     *
     * @return dayOfWeek
     **/
    @ApiModelProperty(value = "")
    public CorDayOfWeekEnum getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(CorDayOfWeekEnum dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public SchGridDTO id(Long id) {
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

    public SchGridDTO name(String name) {
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

    public SchGridDTO shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    /**
     * Get shortName
     *
     * @return shortName
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull
    @Size(min = 0, max = 3)
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchGridDTO schGridDTO = (SchGridDTO) o;
        return Objects.equals(this.clocks, schGridDTO.clocks) &&
                Objects.equals(this.dayOfWeek, schGridDTO.dayOfWeek) &&
                Objects.equals(this.id, schGridDTO.id) &&
                Objects.equals(this.name, schGridDTO.name) &&
                Objects.equals(this.shortName, schGridDTO.shortName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clocks, dayOfWeek, id, name, shortName);
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

    @Override
    public String toString() {
        return "SchGridDTO{" +
                "clocks=" + clocks +
                ", dayOfWeek=" + dayOfWeek +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", deafualtValue=" + defaultGrid +
                '}';
    }
}

