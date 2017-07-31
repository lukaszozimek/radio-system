package io.protone.scheduler.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.protone.scheduler.api.dto.thin.SchLibItemThinDTO;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * SchAttachmentDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class SchAttachmentDTO   {
  @JsonProperty("attachmentType")
  private AttachmentTypeEnum attachmentType = null;
  @JsonProperty("mediaItem")
  private SchLibItemThinDTO mediaItem = null;
  @JsonProperty("fadeStart")
  private Long fadeStart = null;
  @JsonProperty("fadeInLenght")
  private Long fadeInLenght = null;
  @JsonProperty("volumeLevel")
  private Long volumeLevel = null;
  @JsonProperty("fadeOutLength")
  private Long fadeOutLength = null;
  @JsonProperty("fadeType")
  private FadeTypeEnum fadeType = null;

  public SchAttachmentDTO attachmentType(AttachmentTypeEnum attachmentType) {
    this.attachmentType = attachmentType;
    return this;
  }

   /**
   * Get attachmentType
   * @return attachmentType
  **/
  @ApiModelProperty(value = "")
  public AttachmentTypeEnum getAttachmentType() {
    return attachmentType;
  }

  public void setAttachmentType(AttachmentTypeEnum attachmentType) {
    this.attachmentType = attachmentType;
  }

  public SchAttachmentDTO mediaItem(SchLibItemThinDTO mediaItem) {
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

  public SchAttachmentDTO fadeStart(Long fadeStart) {
    this.fadeStart = fadeStart;
    return this;
  }

   /**
   * Get fadeStart
   * @return fadeStart
  **/
  @ApiModelProperty(value = "")
  public Long getFadeStart() {
    return fadeStart;
  }

  public void setFadeStart(Long fadeStart) {
    this.fadeStart = fadeStart;
  }

  public SchAttachmentDTO fadeInLenght(Long fadeInLenght) {
    this.fadeInLenght = fadeInLenght;
    return this;
  }

   /**
   * Get fadeInLenght
   * @return fadeInLenght
  **/
  @ApiModelProperty(value = "")
  public Long getFadeInLenght() {
    return fadeInLenght;
  }

  public void setFadeInLenght(Long fadeInLenght) {
    this.fadeInLenght = fadeInLenght;
  }

  public SchAttachmentDTO volumeLevel(Long volumeLevel) {
    this.volumeLevel = volumeLevel;
    return this;
  }

   /**
   * Get volumeLevel
   * @return volumeLevel
  **/
  @ApiModelProperty(value = "")
  public Long getVolumeLevel() {
    return volumeLevel;
  }

  public void setVolumeLevel(Long volumeLevel) {
    this.volumeLevel = volumeLevel;
  }

  public SchAttachmentDTO fadeOutLength(Long fadeOutLength) {
    this.fadeOutLength = fadeOutLength;
    return this;
  }

   /**
   * Get fadeOutLength
   * @return fadeOutLength
  **/
  @ApiModelProperty(value = "")
  public Long getFadeOutLength() {
    return fadeOutLength;
  }

  public void setFadeOutLength(Long fadeOutLength) {
    this.fadeOutLength = fadeOutLength;
  }

  public SchAttachmentDTO fadeType(FadeTypeEnum fadeType) {
    this.fadeType = fadeType;
    return this;
  }

   /**
   * Get fadeType
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
    SchAttachmentDTO schAttachmentDTO = (SchAttachmentDTO) o;
    return Objects.equals(this.attachmentType, schAttachmentDTO.attachmentType) &&
        Objects.equals(this.mediaItem, schAttachmentDTO.mediaItem) &&
        Objects.equals(this.fadeStart, schAttachmentDTO.fadeStart) &&
        Objects.equals(this.fadeInLenght, schAttachmentDTO.fadeInLenght) &&
        Objects.equals(this.volumeLevel, schAttachmentDTO.volumeLevel) &&
        Objects.equals(this.fadeOutLength, schAttachmentDTO.fadeOutLength) &&
        Objects.equals(this.fadeType, schAttachmentDTO.fadeType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(attachmentType, mediaItem, fadeStart, fadeInLenght, volumeLevel, fadeOutLength, fadeType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SchAttachmentDTO {\n");

    sb.append("    attachmentType: ").append(toIndentedString(attachmentType)).append("\n");
    sb.append("    mediaItem: ").append(toIndentedString(mediaItem)).append("\n");
    sb.append("    fadeStart: ").append(toIndentedString(fadeStart)).append("\n");
    sb.append("    fadeInLenght: ").append(toIndentedString(fadeInLenght)).append("\n");
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

    /**
     * Gets or Sets attachmentType
     */
    public enum AttachmentTypeEnum {
        VOICE_TRACK("AT_VOICE_TRACK"),

        OTHER("AT_OTHER");

        private String value;

        AttachmentTypeEnum(String value) {
            this.value = value;
        }

        @JsonCreator
        public static AttachmentTypeEnum fromValue(String text) {
            for (AttachmentTypeEnum b : AttachmentTypeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }
    }

    /**
     * Gets or Sets fadeType
     */
    public enum FadeTypeEnum {
        LINEAR("FT_LINEAR"),

        LOGARITMIC("FT_LOGARITMIC"),

        OTHER("FT_OTHER");

        private String value;

        FadeTypeEnum(String value) {
            this.value = value;
        }

        @JsonCreator
        public static FadeTypeEnum fromValue(String text) {
            for (FadeTypeEnum b : FadeTypeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }
    }
}

