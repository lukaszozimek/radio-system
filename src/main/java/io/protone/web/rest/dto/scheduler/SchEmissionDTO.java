package io.protone.web.rest.dto.scheduler;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.web.rest.dto.scheduler.thin.SchLibItemThinDTO;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.SchAttachmentDTO;
import io.swagger.model.SchLibItemThinDTO;
import io.swagger.model.SchQueueParamsDTO;
import io.swagger.model.SchTimeParamsDTO;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
/**
 * SchEmissionDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class SchEmissionDTO   {
  @JsonProperty("blockId")
  private Long blockId = null;

  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("mediaItem")
  private SchLibItemThinDTO mediaItem = null;

  @JsonProperty("seq")
  private Integer seq = null;

  @JsonProperty("attachment")
  private List<SchAttachmentDTO> attachment = new ArrayList<SchAttachmentDTO>();

  @JsonProperty("timeParams")
  private SchTimeParamsDTO timeParams = null;

  @JsonProperty("queueParams")
  private SchQueueParamsDTO queueParams = null;

  public SchEmissionDTO blockId(Long blockId) {
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

  public SchEmissionDTO id(Long id) {
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

  public SchEmissionDTO mediaItem(SchLibItemThinDTO mediaItem) {
    this.mediaItem = mediaItem;
    return this;
  }

   /**
   * Get mediaItem
   * @return mediaItem
  **/
  @ApiModelProperty(value = "")
  public SchLibItemThinDTO getMediaItem() {
    return mediaItem;
  }

  public void setMediaItem(SchLibItemThinDTO mediaItem) {
    this.mediaItem = mediaItem;
  }

  public SchEmissionDTO seq(Integer seq) {
    this.seq = seq;
    return this;
  }

   /**
   * Get seq
   * @return seq
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull
  public Integer getSeq() {
    return seq;
  }

  public void setSeq(Integer seq) {
    this.seq = seq;
  }

  public SchEmissionDTO attachment(List<SchAttachmentDTO> attachment) {
    this.attachment = attachment;
    return this;
  }

  public SchEmissionDTO addAttachmentItem(SchAttachmentDTO attachmentItem) {
    this.attachment.add(attachmentItem);
    return this;
  }

   /**
   * Get attachment
   * @return attachment
  **/
  @ApiModelProperty(value = "")
  public List<SchAttachmentDTO> getAttachment() {
    return attachment;
  }

  public void setAttachment(List<SchAttachmentDTO> attachment) {
    this.attachment = attachment;
  }

  public SchEmissionDTO timeParams(SchTimeParamsDTO timeParams) {
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

  public SchEmissionDTO queueParams(SchQueueParamsDTO queueParams) {
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
    SchEmissionDTO schEmissionDTO = (SchEmissionDTO) o;
    return Objects.equals(this.blockId, schEmissionDTO.blockId) &&
        Objects.equals(this.id, schEmissionDTO.id) &&
        Objects.equals(this.mediaItem, schEmissionDTO.mediaItem) &&
        Objects.equals(this.seq, schEmissionDTO.seq) &&
        Objects.equals(this.attachment, schEmissionDTO.attachment) &&
        Objects.equals(this.timeParams, schEmissionDTO.timeParams) &&
        Objects.equals(this.queueParams, schEmissionDTO.queueParams);
  }

  @Override
  public int hashCode() {
    return Objects.hash(blockId, id, mediaItem, seq, attachment, timeParams, queueParams);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SchEmissionDTO {\n");

    sb.append("    blockId: ").append(toIndentedString(blockId)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    mediaItem: ").append(toIndentedString(mediaItem)).append("\n");
    sb.append("    seq: ").append(toIndentedString(seq)).append("\n");
    sb.append("    attachment: ").append(toIndentedString(attachment)).append("\n");
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

