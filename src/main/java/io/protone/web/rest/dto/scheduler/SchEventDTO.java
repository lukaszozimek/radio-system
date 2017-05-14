package io.protone.web.rest.dto.scheduler;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.SchEmissionDTO;
import io.swagger.model.SchQueueParamsDTO;
import io.swagger.model.SchTimeParamsDTO;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
/**
 * SchEventDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class SchEventDTO   {
  @JsonProperty("emissions")
  private List<SchEmissionDTO> emissions = new ArrayList<SchEmissionDTO>();

  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("queueParams")
  private SchQueueParamsDTO queueParams = null;

  /**
   * Gets or Sets eventType
   */
  public enum EventTypeEnum {
    MUSIC_IMPORT("ET_MUSIC_IMPORT"),

    COMMERCIAL_IMPORT("ET_COMMERCIAL_IMPORT"),

    AUTOMATION("ET_AUTOMATION"),

    AUDIO("ET_AUDIO"),

    OTHER("ET_OTHER");

    private String value;

    EventTypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static EventTypeEnum fromValue(String text) {
      for (EventTypeEnum b : EventTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("eventType")
  private EventTypeEnum eventType = null;

  @JsonProperty("timeParams")
  private SchTimeParamsDTO timeParams = null;

  public SchEventDTO emissions(List<SchEmissionDTO> emissions) {
    this.emissions = emissions;
    return this;
  }

  public SchEventDTO addEmissionsItem(SchEmissionDTO emissionsItem) {
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

  public SchEventDTO id(Long id) {
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

  public SchEventDTO name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull
  @Size(min=0,max=100)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public SchEventDTO queueParams(SchQueueParamsDTO queueParams) {
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

  public SchEventDTO eventType(EventTypeEnum eventType) {
    this.eventType = eventType;
    return this;
  }

   /**
   * Get eventType
   * @return eventType
  **/
  @ApiModelProperty(value = "")
  public EventTypeEnum getEventType() {
    return eventType;
  }

  public void setEventType(EventTypeEnum eventType) {
    this.eventType = eventType;
  }

  public SchEventDTO timeParams(SchTimeParamsDTO timeParams) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SchEventDTO schEventDTO = (SchEventDTO) o;
    return Objects.equals(this.emissions, schEventDTO.emissions) &&
        Objects.equals(this.id, schEventDTO.id) &&
        Objects.equals(this.name, schEventDTO.name) &&
        Objects.equals(this.queueParams, schEventDTO.queueParams) &&
        Objects.equals(this.eventType, schEventDTO.eventType) &&
        Objects.equals(this.timeParams, schEventDTO.timeParams);
  }

  @Override
  public int hashCode() {
    return Objects.hash(emissions, id, name, queueParams, eventType, timeParams);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SchEventDTO {\n");

    sb.append("    emissions: ").append(toIndentedString(emissions)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    queueParams: ").append(toIndentedString(queueParams)).append("\n");
    sb.append("    eventType: ").append(toIndentedString(eventType)).append("\n");
    sb.append("    timeParams: ").append(toIndentedString(timeParams)).append("\n");
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

