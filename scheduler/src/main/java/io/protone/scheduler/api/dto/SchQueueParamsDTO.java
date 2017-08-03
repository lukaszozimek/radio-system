package io.protone.scheduler.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.protone.scheduler.domain.enumeration.ObjectTypeEnum;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * SchQueueParamsDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class SchQueueParamsDTO   {
  @JsonProperty("seq")
  private Integer seq = null;

  @JsonProperty("previousId")
  private Long previousId = null;
  @JsonProperty("previousType")
  private ObjectTypeEnum previousType = null;
  @JsonProperty("nextId")
  private Long nextId = null;
  @JsonProperty("nextType")
  private ObjectTypeEnum nextType = null;

  public SchQueueParamsDTO seq(Integer seq) {
    this.seq = seq;
    return this;
  }

   /**
   * Get seq
   * @return seq
  **/
  @ApiModelProperty(value = "")
  public Integer getSeq() {
    return seq;
  }

  public void setSeq(Integer seq) {
    this.seq = seq;
  }

  public SchQueueParamsDTO previousId(Long previousId) {
    this.previousId = previousId;
    return this;
  }

   /**
   * Get previousId
   * @return previousId
  **/
  @ApiModelProperty(value = "")
  public Long getPreviousId() {
    return previousId;
  }

  public void setPreviousId(Long previousId) {
    this.previousId = previousId;
  }

  public SchQueueParamsDTO previousType(ObjectTypeEnum previousType) {
    this.previousType = previousType;
    return this;
  }

   /**
   * Get previousType
   * @return previousType
  **/
  @ApiModelProperty(value = "")
  public ObjectTypeEnum getPreviousType() {
    return previousType;
  }

  public void setPreviousType(ObjectTypeEnum previousType) {
    this.previousType = previousType;
  }

  public SchQueueParamsDTO nextId(Long nextId) {
    this.nextId = nextId;
    return this;
  }

   /**
   * Get nextId
   * @return nextId
  **/
  @ApiModelProperty(value = "")
  public Long getNextId() {
    return nextId;
  }

  public void setNextId(Long nextId) {
    this.nextId = nextId;
  }

  public SchQueueParamsDTO nextType(ObjectTypeEnum nextType) {
    this.nextType = nextType;
    return this;
  }

   /**
   * Get nextType
   * @return nextType
  **/
  @ApiModelProperty(value = "")
  public ObjectTypeEnum getNextType() {
    return nextType;
  }

  public void setNextType(ObjectTypeEnum nextType) {
    this.nextType = nextType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SchQueueParamsDTO schQueueParamsDTO = (SchQueueParamsDTO) o;
    return Objects.equals(this.seq, schQueueParamsDTO.seq) &&
        Objects.equals(this.previousId, schQueueParamsDTO.previousId) &&
        Objects.equals(this.previousType, schQueueParamsDTO.previousType) &&
        Objects.equals(this.nextId, schQueueParamsDTO.nextId) &&
        Objects.equals(this.nextType, schQueueParamsDTO.nextType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(seq, previousId, previousType, nextId, nextType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SchQueueParamsDTO {\n");

    sb.append("    seq: ").append(toIndentedString(seq)).append("\n");
    sb.append("    previousId: ").append(toIndentedString(previousId)).append("\n");
    sb.append("    previousType: ").append(toIndentedString(previousType)).append("\n");
    sb.append("    nextId: ").append(toIndentedString(nextId)).append("\n");
    sb.append("    nextType: ").append(toIndentedString(nextType)).append("\n");
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

