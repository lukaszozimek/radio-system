package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * SchEmissionPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class SchEmissionPT   {
  @JsonProperty("blockId")
  private Long blockId = null;

  @JsonProperty("endTime")
  private String endTime = null;

  @JsonProperty("finished")
  private Boolean finished = null;

  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("length")
  private Long length = null;

  @JsonProperty("mediaItemId")
  private Long mediaItemId = null;

  @JsonProperty("relativeDelay")
  private Long relativeDelay = null;

  @JsonProperty("seq")
  private Integer seq = null;

  @JsonProperty("startTime")
  private String startTime = null;

  /**
   * Gets or Sets startType
   */
  public enum StartTypeEnum {
    FREE("ST_FREE"),

    RELATIVE("ST_RELATIVE"),

    ABSOLUTE("ST_ABSOLUTE");

    private String value;

    StartTypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static StartTypeEnum fromValue(String text) {
      for (StartTypeEnum b : StartTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("startType")
  private StartTypeEnum startType = null;

  @JsonProperty("templateId")
  private Long templateId = null;

  public SchEmissionPT blockId(Long blockId) {
    this.blockId = blockId;
    return this;
  }

   /**
   * Get blockId
   * @return blockId
  **/
  @ApiModelProperty(value = "")
  public Long getBlockId() {
    return blockId;
  }

  public void setBlockId(Long blockId) {
    this.blockId = blockId;
  }

  public SchEmissionPT endTime(String endTime) {
    this.endTime = endTime;
    return this;
  }

   /**
   * Get endTime
   * @return endTime
  **/
  @ApiModelProperty(required = true, value = "")
  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public SchEmissionPT finished(Boolean finished) {
    this.finished = finished;
    return this;
  }

   /**
   * Get finished
   * @return finished
  **/
  @ApiModelProperty(required = true, value = "")
  public Boolean getFinished() {
    return finished;
  }

  public void setFinished(Boolean finished) {
    this.finished = finished;
  }

  public SchEmissionPT id(Long id) {
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

  public SchEmissionPT length(Long length) {
    this.length = length;
    return this;
  }

   /**
   * Get length
   * @return length
  **/
  @ApiModelProperty(required = true, value = "")
  public Long getLength() {
    return length;
  }

  public void setLength(Long length) {
    this.length = length;
  }

  public SchEmissionPT mediaItemId(Long mediaItemId) {
    this.mediaItemId = mediaItemId;
    return this;
  }

   /**
   * Get mediaItemId
   * @return mediaItemId
  **/
  @ApiModelProperty(value = "")
  public Long getMediaItemId() {
    return mediaItemId;
  }

  public void setMediaItemId(Long mediaItemId) {
    this.mediaItemId = mediaItemId;
  }

  public SchEmissionPT relativeDelay(Long relativeDelay) {
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

  public SchEmissionPT seq(Integer seq) {
    this.seq = seq;
    return this;
  }

   /**
   * Get seq
   * @return seq
  **/
  @ApiModelProperty(required = true, value = "")
  public Integer getSeq() {
    return seq;
  }

  public void setSeq(Integer seq) {
    this.seq = seq;
  }

  public SchEmissionPT startTime(String startTime) {
    this.startTime = startTime;
    return this;
  }

   /**
   * Get startTime
   * @return startTime
  **/
  @ApiModelProperty(required = true, value = "")
  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public SchEmissionPT startType(StartTypeEnum startType) {
    this.startType = startType;
    return this;
  }

   /**
   * Get startType
   * @return startType
  **/
  @ApiModelProperty(required = true, value = "")
  public StartTypeEnum getStartType() {
    return startType;
  }

  public void setStartType(StartTypeEnum startType) {
    this.startType = startType;
  }

  public SchEmissionPT templateId(Long templateId) {
    this.templateId = templateId;
    return this;
  }

   /**
   * Get templateId
   * @return templateId
  **/
  @ApiModelProperty(value = "")
  public Long getTemplateId() {
    return templateId;
  }

  public void setTemplateId(Long templateId) {
    this.templateId = templateId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SchEmissionPT schEmissionPT = (SchEmissionPT) o;
    return Objects.equals(this.blockId, schEmissionPT.blockId) &&
        Objects.equals(this.endTime, schEmissionPT.endTime) &&
        Objects.equals(this.finished, schEmissionPT.finished) &&
        Objects.equals(this.id, schEmissionPT.id) &&
        Objects.equals(this.length, schEmissionPT.length) &&
        Objects.equals(this.mediaItemId, schEmissionPT.mediaItemId) &&
        Objects.equals(this.relativeDelay, schEmissionPT.relativeDelay) &&
        Objects.equals(this.seq, schEmissionPT.seq) &&
        Objects.equals(this.startTime, schEmissionPT.startTime) &&
        Objects.equals(this.startType, schEmissionPT.startType) &&
        Objects.equals(this.templateId, schEmissionPT.templateId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(blockId, endTime, finished, id, length, mediaItemId, relativeDelay, seq, startTime, startType, templateId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SchEmissionPT {\n");

    sb.append("    blockId: ").append(toIndentedString(blockId)).append("\n");
    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
    sb.append("    finished: ").append(toIndentedString(finished)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    length: ").append(toIndentedString(length)).append("\n");
    sb.append("    mediaItemId: ").append(toIndentedString(mediaItemId)).append("\n");
    sb.append("    relativeDelay: ").append(toIndentedString(relativeDelay)).append("\n");
    sb.append("    seq: ").append(toIndentedString(seq)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    startType: ").append(toIndentedString(startType)).append("\n");
    sb.append("    templateId: ").append(toIndentedString(templateId)).append("\n");
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

