package io.protone.scheduler.api.dto;

import io.protone.library.api.dto.thin.LibMediaItemThinDTO;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * SchEmissionDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class SchEmissionDTO extends SchTimeParamsDTO {

    private Long blockId = null;

    private LibMediaItemThinDTO mediaItem = null;

    private List<SchAttachmentDTO> attachment = new ArrayList<SchAttachmentDTO>();


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

    public SchEmissionDTO sequence(Long sequence) {
        super.setSequence(sequence);
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

    public SchEmissionDTO endTime(LocalDateTime endTime) {
        super.setEndTime(endTime);
        return this;
    }

    public SchEmissionDTO startTime(LocalDateTime startTime) {
        super.setStartTime(startTime);
        return this;
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
                Objects.equals(this.attachment, schEmissionDTO.attachment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockId, id, mediaItem, attachment);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SchEmissionDTO {\n");

        sb.append("    blockId: ").append(toIndentedString(blockId)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    mediaItem: ").append(toIndentedString(mediaItem)).append("\n");
        sb.append("    attachment: ").append(toIndentedString(attachment)).append("\n");

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

