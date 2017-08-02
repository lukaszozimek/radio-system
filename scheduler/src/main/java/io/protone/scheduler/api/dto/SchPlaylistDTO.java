package io.protone.scheduler.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import uk.co.jemos.podam.common.PodamExclude;

import java.time.LocalDate;;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * SchPlaylistDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class SchPlaylistDTO   {

  @PodamExclude
  @JsonProperty("emissions")
  private List<SchEmissionDTO> emissions = new ArrayList<SchEmissionDTO>();

  @JsonProperty("date")
  private LocalDate date = null;

  @JsonProperty("id")
  private Long id = null;

  public SchPlaylistDTO emissions(List<SchEmissionDTO> emissions) {
    this.emissions = emissions;
    return this;
  }

  public SchPlaylistDTO addEmissionsItem(SchEmissionDTO emissionsItem) {
    this.emissions.add(emissionsItem);
    return this;
  }

   /**
   * Get emissions
   * @return emissions
  **/
  @ApiModelProperty(value = "")
  public List<SchEmissionDTO> getEmissions() {
    return emissions;
  }

  public void setEmissions(List<SchEmissionDTO> emissions) {
    this.emissions = emissions;
  }

  public SchPlaylistDTO date(LocalDate date) {
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

  public SchPlaylistDTO id(Long id) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SchPlaylistDTO schPlaylistDTO = (SchPlaylistDTO) o;
    return Objects.equals(this.emissions, schPlaylistDTO.emissions) &&
        Objects.equals(this.date, schPlaylistDTO.date) &&
        Objects.equals(this.id, schPlaylistDTO.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(emissions, date, id);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SchPlaylistDTO {\n");

    sb.append("    emissions: ").append(toIndentedString(emissions)).append("\n");
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

