package io.protone.scheduler.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * SchScheduleDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class SchScheduleDTO   {
  @JsonProperty("date")
  private LocalDate date = null;

  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("schGridPT")
  private SchGridThinDTO schGridPT = null;

  public SchScheduleDTO date(LocalDate date) {
    this.date = date;
    return this;
  }

   /**
   * Get date
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
   * @return id
  **/
  @ApiModelProperty(value = "")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public SchScheduleDTO schGridPT(SchGridThinDTO schGridPT) {
    this.schGridPT = schGridPT;
    return this;
  }

   /**
   * Get schGridPT
   * @return schGridPT
  **/
  @ApiModelProperty(value = "")
  public SchGridThinDTO getSchGridPT() {
    return schGridPT;
  }

  public void setSchGridPT(SchGridThinDTO schGridPT) {
    this.schGridPT = schGridPT;
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
        Objects.equals(this.schGridPT, schScheduleDTO.schGridPT);
  }

  @Override
  public int hashCode() {
    return Objects.hash(date, id, schGridPT);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SchScheduleDTO {\n");

    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    schGridPT: ").append(toIndentedString(schGridPT)).append("\n");
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

