package io.protone.scheduler.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.library.api.dto.thin.LibMediaItemThinDTO;
import io.protone.scheduler.domain.enumeration.AttachmentTypeEnum;
import io.protone.scheduler.domain.enumeration.FadeTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamLongValue;

import java.util.Objects;

/**
 * SchAttachmentDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class SchAttachmentConfigurationDTO {
    private AttachmentTypeEnum attachmentType = null;

    @JsonProperty("id")
    private Long id = null;

    @PodamExclude
    private LibMediaItemThinDTO mediaItem = null;

    private Long fadeStart = null;

    @PodamLongValue
    private Long fadeInLength = null;

    private Long volumeLevel = null;

    private Long fadeOutLength = null;

    private FadeTypeEnum fadeType = null;

    private SchConfigurationTimeParamsDTO timeParamsDTO;

    private Long sequence;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public SchConfigurationTimeParamsDTO getTimeParamsDTO() {
        return timeParamsDTO;
    }

    public void setTimeParamsDTO(SchConfigurationTimeParamsDTO timeParamsDTO) {
        this.timeParamsDTO = timeParamsDTO;
    }

    public SchAttachmentConfigurationDTO attachmentType(AttachmentTypeEnum attachmentType) {
        this.attachmentType = attachmentType;
        return this;
    }

    /**
     * Get attachmentType
     *
     * @return attachmentType
     **/
    @ApiModelProperty(value = "")
    public AttachmentTypeEnum getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(AttachmentTypeEnum attachmentType) {
        this.attachmentType = attachmentType;
    }

    public SchAttachmentConfigurationDTO mediaItem(LibMediaItemThinDTO mediaItem) {
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

    public SchAttachmentConfigurationDTO fadeStart(Long fadeStart) {
        this.fadeStart = fadeStart;
        return this;
    }

    /**
     * Get fadeStart
     *
     * @return fadeStart
     **/
    @ApiModelProperty(value = "")
    public Long getFadeStart() {
        return fadeStart;
    }

    public void setFadeStart(Long fadeStart) {
        this.fadeStart = fadeStart;
    }

    public SchAttachmentConfigurationDTO fadeInLength(Long fadeInLenght) {
        this.fadeInLength = fadeInLenght;
        return this;
    }

    /**
     * Get fadeInLenght
     *
     * @return fadeInLength
     **/
    @ApiModelProperty(value = "")
    public Long getFadeInLength() {
        return fadeInLength;
    }

    public void setFadeInLength(Long fadeInLength) {
        this.fadeInLength = fadeInLength;
    }

    public SchAttachmentConfigurationDTO volumeLevel(Long volumeLevel) {
        this.volumeLevel = volumeLevel;
        return this;
    }

    /**
     * Get volumeLevel
     *
     * @return volumeLevel
     **/
    @ApiModelProperty(value = "")
    public Long getVolumeLevel() {
        return volumeLevel;
    }

    public void setVolumeLevel(Long volumeLevel) {
        this.volumeLevel = volumeLevel;
    }

    public SchAttachmentConfigurationDTO fadeOutLength(Long fadeOutLength) {
        this.fadeOutLength = fadeOutLength;
        return this;
    }

    /**
     * Get fadeOutLength
     *
     * @return fadeOutLength
     **/
    @ApiModelProperty(value = "")
    public Long getFadeOutLength() {
        return fadeOutLength;
    }

    public void setFadeOutLength(Long fadeOutLength) {
        this.fadeOutLength = fadeOutLength;
    }

    public SchAttachmentConfigurationDTO fadeType(FadeTypeEnum fadeType) {
        this.fadeType = fadeType;
        return this;
    }

    /**
     * Get fadeType
     *
     * @return fadeType
     **/
    @ApiModelProperty(value = "")
    public FadeTypeEnum getFadeType() {
        return fadeType;
    }

    public void setFadeType(FadeTypeEnum fadeType) {
        this.fadeType = fadeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchAttachmentConfigurationDTO schAttachmentDTO = (SchAttachmentConfigurationDTO) o;
        return Objects.equals(this.attachmentType, schAttachmentDTO.attachmentType) &&
                Objects.equals(this.mediaItem, schAttachmentDTO.mediaItem) &&
                Objects.equals(this.fadeStart, schAttachmentDTO.fadeStart) &&
                Objects.equals(this.fadeInLength, schAttachmentDTO.fadeInLength) &&
                Objects.equals(this.volumeLevel, schAttachmentDTO.volumeLevel) &&
                Objects.equals(this.fadeOutLength, schAttachmentDTO.fadeOutLength) &&
                Objects.equals(this.fadeType, schAttachmentDTO.fadeType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attachmentType, mediaItem, fadeStart, fadeInLength, volumeLevel, fadeOutLength, fadeType);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SchAttachmentDTO {\n");

        sb.append("    attachmentType: ").append(toIndentedString(attachmentType)).append("\n");
        sb.append("    mediaItem: ").append(toIndentedString(mediaItem)).append("\n");
        sb.append("    fadeStart: ").append(toIndentedString(fadeStart)).append("\n");
        sb.append("    fadeInLength: ").append(toIndentedString(fadeInLength)).append("\n");
        sb.append("    volumeLevel: ").append(toIndentedString(volumeLevel)).append("\n");
        sb.append("    fadeOutLength: ").append(toIndentedString(fadeOutLength)).append("\n");
        sb.append("    fadeType: ").append(toIndentedString(fadeType)).append("\n");
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

