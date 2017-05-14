package io.protone.web.rest.dto.traffic;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * TraBlockDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class TraBlockDTO   {
  @JsonProperty("description")
  private String description = null;

  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("lenght")
  private Long lenght = null;

  @JsonProperty("seq")
  private Long seq = null;

  @JsonProperty("emissions")
  private List<TraEmissionDTO> emissions = new ArrayList<TraEmissionDTO>();

  public TraBlockDTO description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(value = "")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public TraBlockDTO id(Long id) {
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

  public TraBlockDTO name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
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

  public TraBlockDTO lenght(Long lenght) {
    this.lenght = lenght;
    return this;
  }

   /**
   * Get lenght
   * @return lenght
  **/
  @ApiModelProperty(value = "")
  public Long getLenght() {
    return lenght;
  }

  public void setLenght(Long lenght) {
    this.lenght = lenght;
  }

  public TraBlockDTO seq(Long seq) {
    this.seq = seq;
    return this;
  }

   /**
   * Get seq
   * @return seq
  **/
  @ApiModelProperty(value = "")
  public Long getSeq() {
    return seq;
  }

  public void setSeq(Long seq) {
    this.seq = seq;
  }

  public TraBlockDTO emissions(List<TraEmissionDTO> emissions) {
    this.emissions = emissions;
    return this;
  }

  public TraBlockDTO addEmissionsItem(TraEmissionDTO emissionsItem) {
    this.emissions.add(emissionsItem);
    return this;
  }

   /**
   * Get emissions
   * @return emissions
  **/
  @ApiModelProperty(value = "")
  public List<TraEmissionDTO> getEmissions() {
    return emissions;
  }

  public void setEmissions(List<TraEmissionDTO> emissions) {
    this.emissions = emissions;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TraBlockDTO traBlockDTO = (TraBlockDTO) o;
    return Objects.equals(this.description, traBlockDTO.description) &&
        Objects.equals(this.id, traBlockDTO.id) &&
        Objects.equals(this.name, traBlockDTO.name) &&
        Objects.equals(this.lenght, traBlockDTO.lenght) &&
        Objects.equals(this.seq, traBlockDTO.seq) &&
        Objects.equals(this.emissions, traBlockDTO.emissions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, id, name, lenght, seq, emissions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TraBlockDTO {\n");

    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    lenght: ").append(toIndentedString(lenght)).append("\n");
    sb.append("    seq: ").append(toIndentedString(seq)).append("\n");
    sb.append("    emissions: ").append(toIndentedString(emissions)).append("\n");
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

