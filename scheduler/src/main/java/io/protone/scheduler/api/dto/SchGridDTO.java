package io.protone.scheduler.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.scheduler.api.dto.thin.SchClockThinDTO;
import io.protone.scheduler.domain.enumeration.DayOfWeekEnum;
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
    private List<SchClockThinDTO> clocks = new ArrayList<SchClockThinDTO>();

    @JsonProperty("dayOfWeek")
    private DayOfWeekEnum dayOfWeek = null;

    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("shortName")
    private String shortName = null;

    public SchGridDTO clocks(List<SchClockThinDTO> clocks) {
        this.clocks = clocks;
        return this;
    }

    public SchGridDTO addClock(SchClockThinDTO clock) {
        this.clocks.add(clock);
        return this;
    }

    public SchGridDTO removeClock(SchClockThinDTO clock) {
        this.clocks.remove(clock);
        return this;
    }

    /**
     * Get clocks
     *
     * @return clocks
     **/
    @ApiModelProperty(value = "")
    public List<SchClockThinDTO> getClocks() {
        return clocks;
    }

    public void setClocks(List<SchClockThinDTO> clocks) {
        this.clocks = clocks;
    }

    public SchGridDTO dayOfWeek(DayOfWeekEnum dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }

    /**
     * Get dayOfWeek
     *
     * @return dayOfWeek
     **/
    @ApiModelProperty(value = "")
    public DayOfWeekEnum getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeekEnum dayOfWeek) {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SchGridDTO {\n");

        sb.append("    clocks: ").append(toIndentedString(clocks)).append("\n");
        sb.append("    dayOfWeek: ").append(toIndentedString(dayOfWeek)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    shortName: ").append(toIndentedString(shortName)).append("\n");
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

