package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.protone.domain.enumeration.LibItemTypeEnum;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * LibItemPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class SchLibItemPT {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("album")
    private String albumName = null;

    @JsonProperty("artist")
    private String artistName = null;

    @JsonProperty("idx")
    private String idx = null;

    @JsonProperty("length")
    private Integer length = null;

    @JsonProperty("markers")
    private List<LibMarkerPT> markers = new ArrayList<LibMarkerPT>();

    @JsonProperty("name")
    private String name = null;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @JsonProperty("stream")
    private String stream = null;

    public SchLibItemPT album(String album) {
        this.albumName = album;
        return this;
    }

    /**
     * Get album
     *
     * @return album
     **/
    @ApiModelProperty(value = "")
    public String getAlbum() {
        return albumName;
    }

    public void setAlbum(String album) {
        this.albumName = album;
    }

    public SchLibItemPT artist(String artist) {
        this.artistName = artist;
        return this;
    }

    /**
     * Get artist
     *
     * @return artist
     **/
    @ApiModelProperty(value = "")
    public String getArtist() {
        return artistName;
    }

    public void setArtist(String artist) {
        this.artistName = artist;
    }

    public SchLibItemPT idx(String idx) {
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

    public SchLibItemPT length(Integer length) {
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

    public SchLibItemPT markers(List<LibMarkerPT> markers) {
        this.markers = markers;
        return this;
    }

    public SchLibItemPT addMarkersItem(LibMarkerPT markersItem) {
        this.markers.add(markersItem);
        return this;
    }

    /**
     * Get markers
     *
     * @return markers
     **/
    @ApiModelProperty(value = "")
    public List<LibMarkerPT> getMarkers() {
        return markers;
    }

    public void setMarkers(List<LibMarkerPT> markers) {
        this.markers = markers;
    }

    public SchLibItemPT name(String name) {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchLibItemPT libItemPT = (SchLibItemPT) o;
        return Objects.equals(this.albumName, libItemPT.albumName) &&
            Objects.equals(this.artistName, libItemPT.artistName) &&
            Objects.equals(this.idx, libItemPT.idx) &&
            Objects.equals(this.length, libItemPT.length) &&
            Objects.equals(this.markers, libItemPT.markers) &&
            Objects.equals(this.name, libItemPT.name) &&

            Objects.equals(this.stream, libItemPT.stream) &&
            Objects.equals(this.id, libItemPT.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(albumName, artistName, idx, length, markers, name,  stream, id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class LibItemPT {\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    album: ").append(toIndentedString(albumName)).append("\n");
        sb.append("    artist: ").append(toIndentedString(artistName)).append("\n");
        sb.append("    idx: ").append(toIndentedString(idx)).append("\n");
        sb.append("    length: ").append(toIndentedString(length)).append("\n");
        sb.append("    markers: ").append(toIndentedString(markers)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    stream: ").append(toIndentedString(stream)).append("\n");
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

