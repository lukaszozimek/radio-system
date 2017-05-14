package io.protone.web.rest.dto.scheduler;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.SchBlockDTO;
import io.swagger.model.SchEmissionDTO;
import io.swagger.model.SchQueueParamsDTO;
import io.swagger.model.SchTimeParamsDTO;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
/**
 * SchClockDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class SchClockDTO   {
  @JsonProperty("blocks")
  private List<SchBlockDTO> blocks = new ArrayList<SchBlockDTO>();

  @JsonProperty("emissions")
  private List<SchEmissionDTO> emissions = new ArrayList<SchEmissionDTO>();

  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("timeParams")
  private SchTimeParamsDTO timeParams = null;

  @JsonProperty("queueParams")
  private SchQueueParamsDTO queueParams = null;

  public SchClockDTO blocks(List<SchBlockDTO> blocks) {
    this.blocks = blocks;
    return this;
  }

  public SchClockDTO addBlocksItem(SchBlockDTO blocksItem) {
    this.blocks.add(blocksItem);
    return this;
  }

   /**
   * Get blocks
   * @return blocks
  **/
  @ApiModelProperty(value = "")
  public List<SchBlockDTO> getBlocks() {
    return blocks;
  }

  public void setBlocks(List<SchBlockDTO> blocks) {
    this.blocks = blocks;
  }

  public SchClockDTO emissions(List<SchEmissionDTO> emissions) {
    this.emissions = emissions;
    return this;
  }

  public SchClockDTO addEmissionsItem(SchEmissionDTO emissionsItem) {
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

  public SchClockDTO id(Long id) {
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

  public SchClockDTO name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(value = "")
  @Size(min=0,max=100)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public SchClockDTO timeParams(SchTimeParamsDTO timeParams) {
    this.timeParams = timeParams;
    return this;
  }

   /**
   * Get timeParams
   * @return timeParams
  **/
  @ApiModelProperty(value = "")
  public SchTimeParamsDTO getTimeParams() {
    return timeParams;
  }

  public void setTimeParams(SchTimeParamsDTO timeParams) {
    this.timeParams = timeParams;
  }

  public SchClockDTO queueParams(SchQueueParamsDTO queueParams) {
    this.queueParams = queueParams;
    return this;
  }

   /**
   * Get queueParams
   * @return queueParams
  **/
  @ApiModelProperty(value = "")
  public SchQueueParamsDTO getQueueParams() {
    return queueParams;
  }

  public void setQueueParams(SchQueueParamsDTO queueParams) {
    this.queueParams = queueParams;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SchClockDTO schClockDTO = (SchClockDTO) o;
    return Objects.equals(this.blocks, schClockDTO.blocks) &&
        Objects.equals(this.emissions, schClockDTO.emissions) &&
        Objects.equals(this.id, schClockDTO.id) &&
        Objects.equals(this.name, schClockDTO.name) &&
        Objects.equals(this.timeParams, schClockDTO.timeParams) &&
        Objects.equals(this.queueParams, schClockDTO.queueParams);
  }

  @Override
  public int hashCode() {
    return Objects.hash(blocks, emissions, id, name, timeParams, queueParams);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SchClockDTO {\n");

    sb.append("    blocks: ").append(toIndentedString(blocks)).append("\n");
    sb.append("    emissions: ").append(toIndentedString(emissions)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    timeParams: ").append(toIndentedString(timeParams)).append("\n");
    sb.append("    queueParams: ").append(toIndentedString(queueParams)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

