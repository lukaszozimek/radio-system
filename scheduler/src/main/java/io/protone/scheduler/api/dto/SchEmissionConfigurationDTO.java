package io.protone.scheduler.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.library.api.dto.thin.LibMediaItemThinDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * SchEmissionDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class SchEmissionConfigurationDTO {
    @JsonProperty("blockId")
    private Long blockId = null;

    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("mediaItem")
    private LibMediaItemThinDTO mediaItem = null;

    @JsonProperty("seq")
    private Integer seq = null;

    @JsonProperty("attachments")
    private List<SchAttachmentConfigurationDTO> attachments = new ArrayList<SchAttachmentConfigurationDTO>();

    @JsonProperty("timeParams")
    private SchConfigurationTimeParamsDTO timeParams = null;

    @JsonProperty("queueParams")
    private SchQueueParamsDTO queueParams = null;

    public SchEmissionConfigurationDTO blockId(Long blockId) {
        this.blockId = blockId;
        return this;
    }

    /**
     * Get blockId
     *
     * @return blockId
     **/
    @ApiModelProperty(value = "")
    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public SchEmissionConfigurationDTO id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     **/
    @ApiModelProperty(value = "")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SchEmissionConfigurationDTO mediaItem(LibMediaItemThinDTO mediaItem) {
        this.mediaItem = mediaItem;
        return this;
    }

    /**
     * Get mediaItem
     *
     * @return mediaItem
     **/
    @ApiModelProperty(value = "")
    public LibMediaItemThinDTO getMediaItem() {
        return mediaItem;
    }

    public void setMediaItem(LibMediaItemThinDTO mediaItem) {
        this.mediaItem = mediaItem;
    }

    public SchEmissionConfigurationDTO seq(Integer seq) {
        this.seq = seq;
        return this;
    }

    /**
     * Get seq
     *
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

    public SchEmissionConfigurationDTO attachment(List<SchAttachmentConfigurationDTO> attachment) {
        this.attachments = attachment;
        return this;
    }

    public SchEmissionConfigurationDTO addAttachmentItem(SchAttachmentConfigurationDTO attachmentItem) {
        this.attachments.add(attachmentItem);
        return this;
    }

    /**
     * Get attachments
     *
     * @return attachments
     **/
    @ApiModelProperty(value = "")
    public List<SchAttachmentConfigurationDTO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<SchAttachmentConfigurationDTO> attachments) {
        this.attachments = attachments;
    }

    public SchEmissionConfigurationDTO timeParams(SchConfigurationTimeParamsDTO timeParams) {
        this.timeParams = timeParams;
        return this;
    }

    /**
     * Get timeParams
     *
     * @return timeParams
     **/
    @ApiModelProperty(value = "")
    public SchConfigurationTimeParamsDTO getTimeParams() {
        return timeParams;
    }

    public void setTimeParams(SchConfigurationTimeParamsDTO timeParams) {
        this.timeParams = timeParams;
    }

    public SchEmissionConfigurationDTO queueParams(SchQueueParamsDTO queueParams) {
        this.queueParams = queueParams;
        return this;
    }

    /**
     * Get queueParams
     *
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchEmissionConfigurationDTO schEmissionDTO = (SchEmissionConfigurationDTO) o;
        return Objects.equals(this.blockId, schEmissionDTO.blockId) &&
                Objects.equals(this.id, schEmissionDTO.id) &&
                Objects.equals(this.mediaItem, schEmissionDTO.mediaItem) &&
                Objects.equals(this.seq, schEmissionDTO.seq) &&
                Objects.equals(this.attachments, schEmissionDTO.attachments) &&
                Objects.equals(this.timeParams, schEmissionDTO.timeParams) &&
                Objects.equals(this.queueParams, schEmissionDTO.queueParams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockId, id, mediaItem, seq, attachments, timeParams, queueParams);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SchEmissionDTO {\n");

        sb.append("    blockId: ").append(toIndentedString(blockId)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    mediaItem: ").append(toIndentedString(mediaItem)).append("\n");
        sb.append("    seq: ").append(toIndentedString(seq)).append("\n");
        sb.append("    attachments: ").append(toIndentedString(attachments)).append("\n");
        sb.append("    timeParams: ").append(toIndentedString(timeParams)).append("\n");
        sb.append("    queueParams: ").append(toIndentedString(queueParams)).append("\n");
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

