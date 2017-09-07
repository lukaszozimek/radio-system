package io.protone.scheduler.api.dto.thin;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.scheduler.api.dto.SchBaseDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

/**
 * SchScheduleDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class SchScheduleThinDTO extends SchBaseDTO {

    @JsonProperty("date")
    private LocalDate date = null;

    public SchScheduleThinDTO date(LocalDate date) {
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

    public SchScheduleThinDTO id(Long id) {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchScheduleThinDTO schScheduleDTO = (SchScheduleThinDTO) o;
        return Objects.equals(this.date, schScheduleDTO.date) &&
                Objects.equals(this.id, schScheduleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SchScheduleDTO {\n");

        sb.append("    date: ").append(toIndentedString(date)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
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

