package io.protone.scheduler.api.dto;

import io.protone.library.api.dto.thin.LibMediaItemThinDTO;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * SchEmissionDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class SchEmissionDTO {

    private Long blockId = null;

    private Long id = null;

    private LibMediaItemThinDTO mediaItem = null;

    private List<SchAttachmentDTO> attachment = new ArrayList<SchAttachmentDTO>();


    private SchTimeParamsDTO timeParams = null;


    private Long sequence;

    public SchEmissionDTO blockId(Long blockId) {
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

    public SchEmissionDTO id(Long id) {
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

    public SchEmissionDTO mediaItem(LibMediaItemThinDTO mediaItem) {
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

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
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
     *
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
     *
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
    public boolean equals(Object o) {
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
                Objects.equals(this.sequence, schEmissionDTO.sequence) &&
                Objects.equals(this.attachment, schEmissionDTO.attachment) &&
                Objects.equals(this.timeParams, schEmissionDTO.timeParams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockId, id, mediaItem, attachment, timeParams, sequence);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SchEmissionDTO {\n");

        sb.append("    blockId: ").append(toIndentedString(blockId)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    mediaItem: ").append(toIndentedString(mediaItem)).append("\n");
        sb.append("    sequence: ").append(toIndentedString(sequence)).append("\n");
        sb.append("    attachment: ").append(toIndentedString(attachment)).append("\n");
        sb.append("    timeParams: ").append(toIndentedString(timeParams)).append("\n");
        sb.append("    sequence: ").append(toIndentedString(sequence)).append("\n");
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

