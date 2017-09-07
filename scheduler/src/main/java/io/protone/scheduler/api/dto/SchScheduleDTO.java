package io.protone.scheduler.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import uk.co.jemos.podam.common.PodamExclude;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * SchScheduleDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class SchScheduleDTO extends SchBaseDTO {
    @JsonProperty("date")
    private LocalDate date = null;


    @PodamExclude
    private List<SchClockDTO> schClockDTOS = null;

    public SchScheduleDTO date(LocalDate date) {
        this.date = date;
        return this;
    }

    /**
     * Get date
     *
     * @return date
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public SchScheduleDTO id(Long id) {
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

    public SchScheduleDTO schGrid(List<SchClockDTO> schGrid) {
        this.schClockDTOS = schGrid;
        return this;
    }

    /**
     * Get schGridPT
     *
     * @return schGridPT
     **/
    @ApiModelProperty(value = "")
    public List<SchClockDTO> getSchClockDTOS() {
        return schClockDTOS;
    }

    public void setSchClockDTOS(List<SchClockDTO> schClockDTOS) {
        this.schClockDTOS = schClockDTOS;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchScheduleDTO schScheduleDTO = (SchScheduleDTO) o;
        return Objects.equals(this.date, schScheduleDTO.date) &&
                Objects.equals(this.id, schScheduleDTO.id) &&
                Objects.equals(this.schClockDTOS, schScheduleDTO.schClockDTOS);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, id, schClockDTOS);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SchScheduleDTO {\n");

        sb.append("    date: ").append(toIndentedString(date)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    schClockDTOS: ").append(toIndentedString(schClockDTOS)).append("\n");
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

