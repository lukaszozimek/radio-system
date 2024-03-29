package io.protone.scheduler.api.dto;

import io.protone.library.api.dto.thin.LibMediaItemThinDTO;
import io.protone.scheduler.api.dto.base.SchTemplateTimeParamsDTO;
import io.protone.scheduler.domain.enumeration.AttachmentTypeEnum;
import io.protone.scheduler.domain.enumeration.FadeTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamLongValue;

/**
 * SchAttachmentDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class SchAttachmentTemplateDTO extends SchTemplateTimeParamsDTO {
    protected AttachmentTypeEnum attachmentType = null;

    @PodamExclude
    protected LibMediaItemThinDTO mediaItem = null;
    private Boolean isInstance;
    protected Long fadeStart = null;

    @PodamLongValue
    protected Long fadeInLength = null;

    protected Long volumeLevel = null;

    protected Long fadeOutLength = null;

    protected FadeTypeEnum fadeType = null;


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


    public SchAttachmentTemplateDTO attachmentType(AttachmentTypeEnum attachmentType) {
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

    public SchAttachmentTemplateDTO mediaItem(LibMediaItemThinDTO mediaItem) {
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

    public SchAttachmentTemplateDTO fadeStart(Long fadeStart) {
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

    public SchAttachmentTemplateDTO fadeInLength(Long fadeInLenght) {
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

    public SchAttachmentTemplateDTO volumeLevel(Long volumeLevel) {
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

    public SchAttachmentTemplateDTO fadeOutLength(Long fadeOutLength) {
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

    public SchAttachmentTemplateDTO fadeType(FadeTypeEnum fadeType) {
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

    public Boolean getInstance() {
        return isInstance;
    }

    public void setInstance(Boolean instance) {
        isInstance = instance;
    }
}

