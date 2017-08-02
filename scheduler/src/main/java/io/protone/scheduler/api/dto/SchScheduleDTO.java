package io.protone.scheduler.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import uk.co.jemos.podam.common.PodamExclude;

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

  @PodamExclude
  @JsonProperty("schGridPT")
  private SchGridThinDTO schGrid = null;

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

  public SchScheduleDTO schGrid(SchGridThinDTO schGrid) {
    this.schGrid = schGrid;
    return this;
  }

   /**
   * Get schGridPT
   * @return schGridPT
  **/
  @ApiModelProperty(value = "")
  public SchGridThinDTO getSchGrid() {
    return schGrid;
  }

  public void setSchGrid(SchGridThinDTO schGrid) {
    this.schGrid = schGrid;
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
        Objects.equals(this.schGrid, schScheduleDTO.schGrid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(date, id, schGrid);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SchScheduleDTO {\n");

    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    schGrid: ").append(toIndentedString(schGrid)).append("\n");
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

