package io.protone.scheduler.api.dto;

import io.protone.library.api.dto.thin.LibMediaItemThinDTO;
import io.protone.scheduler.api.dto.base.SchTemplateTimeParamsDTO;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * SchEmissionDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class SchEmissionTemplateDTO extends SchTemplateTimeParamsDTO {

    private LibMediaItemThinDTO mediaItem = null;


    private Boolean isInstance;

    private List<SchAttachmentTemplateDTO> attachments = new ArrayList<SchAttachmentTemplateDTO>();

    public SchEmissionTemplateDTO id(Long id) {
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

    public SchEmissionTemplateDTO mediaItem(LibMediaItemThinDTO mediaItem) {
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


    public SchEmissionTemplateDTO attachment(List<SchAttachmentTemplateDTO> attachment) {
        this.attachments = attachment;
        return this;
    }

    public SchEmissionTemplateDTO addAttachmentItem(SchAttachmentTemplateDTO attachmentItem) {
        this.attachments.add(attachmentItem);
        return this;
    }

    /**
     * Get attachments
     *
     * @return attachments
     **/
    @ApiModelProperty(value = "")
    public List<SchAttachmentTemplateDTO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<SchAttachmentTemplateDTO> attachments) {
        this.attachments = attachments;
    }


    public Long getSequence() {
        return sequence;
    }

    public Boolean getInstance() {
        return isInstance;
    }

    public void setInstance(Boolean instance) {
        isInstance = instance;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchEmissionTemplateDTO schEmissionDTO = (SchEmissionTemplateDTO) o;
        return
                Objects.equals(this.id, schEmissionDTO.id) &&
                        Objects.equals(this.mediaItem, schEmissionDTO.mediaItem) &&
                        Objects.equals(this.sequence, schEmissionDTO.sequence) &&
                        Objects.equals(this.attachments, schEmissionDTO.attachments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mediaItem, attachments, sequence);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SchEmissionDTO {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    mediaItem: ").append(toIndentedString(mediaItem)).append("\n");
        sb.append("    sequence: ").append(toIndentedString(sequence)).append("\n");
        sb.append("    attachments: ").append(toIndentedString(attachments)).append("\n");
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

