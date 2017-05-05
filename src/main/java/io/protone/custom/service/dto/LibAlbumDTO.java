package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;
import java.util.Objects;

/**
 * LibAlbumDTO
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class LibAlbumDTO {
    @JsonProperty("albumType")
    private AlbumTypeEnum albumType = null;
    @JsonProperty("artistId")
    private Long artistId = null;
    @JsonProperty("coverId")
    private Long coverId = null;
    @JsonProperty("description")
    private String description = null;
    @JsonProperty("id")
    private Long id = null;
    @JsonProperty("labelId")
    private Long labelId = null;
    @JsonProperty("name")
    private String name = null;
    @JsonProperty("releaseDate")
    private LocalDate releaseDate = null;

    public LibAlbumDTO albumType(AlbumTypeEnum albumType) {
        this.albumType = albumType;
        return this;
    }

    /**
     * Get albumType
     *
     * @return albumType
     **/
    @ApiModelProperty(required = true, value = "")
    public AlbumTypeEnum getAlbumType() {
        return albumType;
    }

    public void setAlbumType(AlbumTypeEnum albumType) {
        this.albumType = albumType;
    }

    public LibAlbumDTO artistId(Long artistId) {
        this.artistId = artistId;
        return this;
    }

    /**
     * Get artistId
     *
     * @return artistId
     **/
    @ApiModelProperty(value = "")
    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public LibAlbumDTO coverId(Long coverId) {
        this.coverId = coverId;
        return this;
    }

    /**
     * Get coverId
     *
     * @return coverId
     **/
    @ApiModelProperty(value = "")
    public Long getCoverId() {
        return coverId;
    }

    public void setCoverId(Long coverId) {
        this.coverId = coverId;
    }

    public LibAlbumDTO description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Get description
     *
     * @return description
     **/
    @ApiModelProperty(value = "")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LibAlbumDTO id(Long id) {
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

    public LibAlbumDTO labelId(Long labelId) {
        this.labelId = labelId;
        return this;
    }

    /**
     * Get labelId
     *
     * @return labelId
     **/
    @ApiModelProperty(value = "")
    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public LibAlbumDTO name(String name) {
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

    public LibAlbumDTO releaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }
    /**
     * Get releaseDate
     *
     * @return releaseDate
     **/
    @ApiModelProperty(value = "")
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LibAlbumDTO libAlbumDTO = (LibAlbumDTO) o;
        return Objects.equals(this.albumType, libAlbumDTO.albumType) &&
            Objects.equals(this.artistId, libAlbumDTO.artistId) &&
            Objects.equals(this.coverId, libAlbumDTO.coverId) &&
            Objects.equals(this.description, libAlbumDTO.description) &&
            Objects.equals(this.id, libAlbumDTO.id) &&
            Objects.equals(this.labelId, libAlbumDTO.labelId) &&
            Objects.equals(this.name, libAlbumDTO.name) &&
            Objects.equals(this.releaseDate, libAlbumDTO.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(albumType, artistId, coverId, description, id, labelId, name, releaseDate);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class LibAlbumDTO {\n");

        sb.append("    albumType: ").append(toIndentedString(albumType)).append("\n");
        sb.append("    artistId: ").append(toIndentedString(artistId)).append("\n");
        sb.append("    coverId: ").append(toIndentedString(coverId)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    labelId: ").append(toIndentedString(labelId)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    releaseDate: ").append(toIndentedString(releaseDate)).append("\n");
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
     * Gets or Sets albumType
     */
    public enum AlbumTypeEnum {
        ALBUM("AT_ALBUM"),

        SINGLE("AT_SINGLE"),

        COMPILATION("AT_COMPILATION"),

        OTHER("AT_OTHER");

        private String value;

        AlbumTypeEnum(String value) {
            this.value = value;
        }

        @JsonCreator
        public static AlbumTypeEnum fromValue(String text) {
            for (AlbumTypeEnum b : AlbumTypeEnum.values()) {
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

