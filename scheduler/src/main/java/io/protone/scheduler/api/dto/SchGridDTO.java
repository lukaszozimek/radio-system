package io.protone.scheduler.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.core.api.dto.thin.CoreUserThinDTO;
import io.protone.core.domain.enumeration.CorDayOfWeekEnum;
import io.protone.scheduler.api.dto.base.SchBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import uk.co.jemos.podam.common.PodamExclude;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * SchGridDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class SchGridDTO extends SchBaseDTO {

    @PodamExclude
    @JsonProperty("clocks")
    private List<SchClockTemplateDTO> clocks = new ArrayList<SchClockTemplateDTO>();

    @JsonProperty("dayOfWeek")
    private CorDayOfWeekEnum dayOfWeek = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("length")
    private Long length;

    @JsonProperty("shortName")
    private String shortName = null;


    @JsonProperty("default")
    private Boolean defaultGrid = null;

    private CoreUserThinDTO createdBy;

    private ZonedDateTime createdDate;

    private CoreUserThinDTO lastModifiedBy;

    private ZonedDateTime lastModifiedDate;

    public SchGridDTO clocks(List<SchClockTemplateDTO> clocks) {
        this.clocks = clocks;
        return this;
    }

    public SchGridDTO addClock(SchClockTemplateDTO clock) {
        this.clocks.add(clock);
        return this;
    }

    public SchGridDTO removeClock(SchClockTemplateDTO clock) {
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
    public List<SchClockTemplateDTO> getClocks() {
        return clocks;
    }

    public void setClocks(List<SchClockTemplateDTO> clocks) {
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
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public CoreUserThinDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(CoreUserThinDTO createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public CoreUserThinDTO getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(CoreUserThinDTO lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
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

