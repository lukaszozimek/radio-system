package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * LibMarkerPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class LibMarkerPT {
    @JsonProperty("id")
    private Long id = null;

    /**
     * Gets or Sets markerType
     */
    public enum MarkerTypeEnum {
        BASIC("MT_BASIC"),

        INTRO("MT_INTRO"),

        FADE("MT_FADE"),

        HOOK("MT_HOOK"),

        LOOP("MT_LOOP"),

        CUSTOM("MT_CUSTOM");

        private String value;

        MarkerTypeEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static MarkerTypeEnum fromValue(String text) {
            for (MarkerTypeEnum b : MarkerTypeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonProperty("markerType")
    private MarkerTypeEnum markerType = null;

    @JsonProperty("mediaItemId")
    private Long mediaItemId = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("startTime")
    private Long startTime = null;

    public LibMarkerPT id(Long id) {
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

    public LibMarkerPT markerType(MarkerTypeEnum markerType) {
        this.markerType = markerType;
        return this;
    }

    /**
     * Get markerType
     *
     * @return markerType
     **/
    @ApiModelProperty(required = true, value = "")
    public MarkerTypeEnum getMarkerType() {
        return markerType;
    }

    public void setMarkerType(MarkerTypeEnum markerType) {
        this.markerType = markerType;
    }

    public LibMarkerPT mediaItemId(Long mediaItemId) {
        this.mediaItemId = mediaItemId;
        return this;
    }

    /**
     * Get mediaItemId
     *
     * @return mediaItemId
     **/
    @ApiModelProperty(value = "")
    public Long getMediaItemId() {
        return mediaItemId;
    }

    public void setMediaItemId(Long mediaItemId) {
        this.mediaItemId = mediaItemId;
    }

    public LibMarkerPT name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get name
     *
     * @return name
     **/
    @ApiModelProperty(required = true, value = "")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LibMarkerPT startTime(Long startTime) {
        this.startTime = startTime;
        return this;
    }

    /**
     * Get startTime
     *
     * @return startTime
     **/
    @ApiModelProperty(required = true, value = "")
    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LibMarkerPT libMarkerPT = (LibMarkerPT) o;
        return Objects.equals(this.id, libMarkerPT.id) &&
            Objects.equals(this.markerType, libMarkerPT.markerType) &&
            Objects.equals(this.mediaItemId, libMarkerPT.mediaItemId) &&
            Objects.equals(this.name, libMarkerPT.name) &&
            Objects.equals(this.startTime, libMarkerPT.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, markerType, mediaItemId, name, startTime);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class LibMarkerPT {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    markerType: ").append(toIndentedString(markerType)).append("\n");
        sb.append("    mediaItemId: ").append(toIndentedString(mediaItemId)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
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

