package io.protone.library.api.dto.thin;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.library.api.dto.LibMarkerDTO;
import io.protone.library.domain.enumeration.LibItemStateEnum;
import io.protone.library.domain.enumeration.LibItemTypeEnum;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the LibMediaItem entity.
 */
public class LibMediaItemThinDTO implements Serializable {

    @JsonProperty("library")
    private String library = null;

    @JsonProperty("album")
    private String album = null;

    @JsonProperty("artist")
    private String artist = null;

    @JsonProperty("cover")
    private String cover = null;

    @JsonProperty("id")
    @NotNull
    private Long id = null;

    @JsonProperty("idx")
    @NotNull
    private String idx = null;

    @JsonProperty("itemType")
    private LibItemTypeEnum itemType = null;

    @JsonProperty("state")
    private LibItemStateEnum state = null;

    @JsonProperty("length")
    private Integer length = null;

    @JsonProperty("markers")
    private List<LibMarkerDTO> markers = new ArrayList<LibMarkerDTO>();

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("stream")
    private String stream = null;

    public LibMediaItemThinDTO library(String library) {
        this.library = library;
        return this;
    }

    /**
     * Get library
     *
     * @return library
     **/
    @ApiModelProperty(value = "")
    public String getLibrary() {
        return library;
    }

    public void setLibrary(String library) {
        this.library = library;
    }

    public LibMediaItemThinDTO album(String album) {
        this.album = album;
        return this;
    }

    /**
     * Get album
     *
     * @return album
     **/
    @ApiModelProperty(value = "")
    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public LibMediaItemThinDTO artist(String artist) {
        this.artist = artist;
        return this;
    }

    /**
     * Get artist
     *
     * @return artist
     **/
    @ApiModelProperty(value = "")
    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public LibMediaItemThinDTO cover(String cover) {
        this.cover = cover;
        return this;
    }

    /**
     * Get cover
     *
     * @return cover
     **/
    @ApiModelProperty(value = "")
    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public LibMediaItemThinDTO id(Long id) {
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

    public LibMediaItemThinDTO idx(String idx) {
        this.idx = idx;
        return this;
    }

    /**
     * Get idx
     *
     * @return idx
     **/
    @ApiModelProperty(value = "")
    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public LibMediaItemThinDTO length(Integer length) {
        this.length = length;
        return this;
    }

    /**
     * Get length
     *
     * @return length
     **/
    @ApiModelProperty(value = "")
    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public LibMediaItemThinDTO markers(List<LibMarkerDTO> markers) {
        this.markers = markers;
        return this;
    }

    public LibMediaItemThinDTO addMarkersItem(LibMarkerDTO markersItem) {
        this.markers.add(markersItem);
        return this;
    }

    /**
     * Get markers
     *
     * @return markers
     **/
    @ApiModelProperty(value = "")
    public List<LibMarkerDTO> getMarkers() {
        return markers;
    }

    public void setMarkers(List<LibMarkerDTO> markers) {
        this.markers = markers;
    }

    public LibMediaItemThinDTO name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get name
     *
     * @return name
     **/
    @ApiModelProperty(value = "")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LibMediaItemThinDTO stream(String stream) {
        this.stream = stream;
        return this;
    }

    /**
     * Get stream
     *
     * @return stream
     **/
    @ApiModelProperty(value = "")
    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public LibItemStateEnum getState() {
        return state;
    }

    public void setState(LibItemStateEnum state) {
        this.state = state;
    }
    public LibItemTypeEnum getItemType() {
        return itemType;
    }

    public void setItemType(LibItemTypeEnum itemType) {
        this.itemType = itemType;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LibMediaItemThinDTO schLibItemThinDTO = (LibMediaItemThinDTO) o;
        return Objects.equals(this.library, schLibItemThinDTO.library) &&
                Objects.equals(this.album, schLibItemThinDTO.album) &&
                Objects.equals(this.artist, schLibItemThinDTO.artist) &&
                Objects.equals(this.cover, schLibItemThinDTO.cover) &&
                Objects.equals(this.id, schLibItemThinDTO.id) &&
                Objects.equals(this.idx, schLibItemThinDTO.idx) &&
                Objects.equals(this.length, schLibItemThinDTO.length) &&
                Objects.equals(this.markers, schLibItemThinDTO.markers) &&
                Objects.equals(this.name, schLibItemThinDTO.name) &&
                Objects.equals(this.stream, schLibItemThinDTO.stream);
    }

    @Override
    public int hashCode() {
        return Objects.hash(library, album, artist, cover, id, idx, length, markers, name, stream);
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



    @Override
    public String toString() {
        return "LibMediaItemThinDTO{" +
                "library='" + library + '\'' +
                ", album='" + album + '\'' +
                ", artist='" + artist + '\'' +
                ", cover='" + cover + '\'' +
                ", id=" + id +
                ", idx='" + idx + '\'' +
                ", itemType=" + itemType +
                ", state=" + state +
                ", length=" + length +
                ", markers=" + markers +
                ", name='" + name + '\'' +
                ", stream='" + stream + '\'' +
                '}';
    }
}
