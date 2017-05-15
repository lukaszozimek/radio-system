package io.protone.web.rest.dto.scheduler;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.joda.time.DateTime;
import javax.validation.constraints.*;
/**
 * SchTimeParamsDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class SchTimeParamsDTO   {
  @JsonProperty("relativeDelay")
  private Long relativeDelay = null;

  @JsonProperty("endTime")
  private DateTime endTime = null;

  @JsonProperty("startTime")
  private DateTime startTime = null;

  public SchTimeParamsDTO relativeDelay(Long relativeDelay) {
    this.relativeDelay = relativeDelay;
    return this;
  }

   /**
   * Get relativeDelay
   * @return relativeDelay
  **/
  @ApiModelProperty(value = "")
  public Long getRelativeDelay() {
    return relativeDelay;
  }

  public void setRelativeDelay(Long relativeDelay) {
    this.relativeDelay = relativeDelay;
  }

  public SchTimeParamsDTO endTime(DateTime endTime) {
    this.endTime = endTime;
    return this;
  }

   /**
   * Get endTime
   * @return endTime
  **/
  @ApiModelProperty(value = "")
  public DateTime getEndTime() {
    return endTime;
  }

  public void setEndTime(DateTime endTime) {
    this.endTime = endTime;
  }

  public SchTimeParamsDTO startTime(DateTime startTime) {
    this.startTime = startTime;
    return this;
  }

   /**
   * Get startTime
   * @return startTime
  **/
  @ApiModelProperty(value = "")
  public DateTime getStartTime() {
    return startTime;
  }

  public void setStartTime(DateTime startTime) {
    this.startTime = startTime;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SchTimeParamsDTO schTimeParamsDTO = (SchTimeParamsDTO) o;
    return Objects.equals(this.relativeDelay, schTimeParamsDTO.relativeDelay) &&
        Objects.equals(this.endTime, schTimeParamsDTO.endTime) &&
        Objects.equals(this.startTime, schTimeParamsDTO.startTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(relativeDelay, endTime, startTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SchTimeParamsDTO {\n");

    sb.append("    relativeDelay: ").append(toIndentedString(relativeDelay)).append("\n");
    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
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

