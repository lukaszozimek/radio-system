package io.protone.web.rest.dto.library;

import io.protone.web.rest.dto.cor.CorKeyValueDTO;
import io.protone.domain.enumeration.LibItemStateEnum;
import io.protone.domain.enumeration.LibItemTypeEnum;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * LibMediaItemDTO
 */

public class LibMediaItemDTO implements Serializable {

    private Long id;

    private LibAlbumDTO album = null;

    private LibArtistDTO artist = null;

    private List<LibPersonDTO> authors = new ArrayList<>();

    private List<LibPersonDTO> composers = new ArrayList<>();

    @NotNull
    private String idx = null;

    private String command = null;

    private String description = null;

    private LibLabelDTO label = null;

    private Double length = null;

    private LibLibraryDTO library = null;

    private List<LibMarkerDTO> markers = new ArrayList<LibMarkerDTO>();

    @NotNull
    private String name = null;

    private List<CorKeyValueDTO> properties = new ArrayList<CorKeyValueDTO>();

    private LibItemTypeEnum itemType = null;

    private LibItemStateEnum state = null;

    private String stream = null;

    private List<String> tags = new ArrayList<String>();

    private LibTrackDTO track = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LibMediaItemDTO album(LibAlbumDTO album) {
        this.album = album;
        return this;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public LibMediaItemDTO command(String command) {
        this.command = command;
        return this;
    }

    public LibMediaItemDTO description(String description) {
        this.description = description;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get album
     *
     * @return album
     **/
    @ApiModelProperty(value = "")
    public LibAlbumDTO getAlbum() {
        return album;
    }

    public void setAlbum(LibAlbumDTO album) {
        this.album = album;
    }

    public LibMediaItemDTO artist(LibArtistDTO artist) {
        this.artist = artist;
        return this;
    }

    /**
     * Get artist
     *
     * @return artist
     **/
    @ApiModelProperty(value = "")
    public LibArtistDTO getArtist() {
        return artist;
    }

    public void setArtist(LibArtistDTO artist) {
        this.artist = artist;
    }

    public LibMediaItemDTO author(List<LibPersonDTO> author) {
        this.authors = author;
        return this;
    }

    public LibMediaItemDTO addAuthors(LibPersonDTO composer) {
        this.authors.add(composer);
        return this;
    }

    /**
     * Get authors
     *
     * @return authors
     **/
    @ApiModelProperty(value = "")
    public List<LibPersonDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(List<LibPersonDTO> authors) {
        this.authors = this.authors;
    }

    public LibMediaItemDTO composer(List<LibPersonDTO> composers) {
        this.composers = composers;
        return this;
    }

    /**
     * Get composers
     *
     * @return composers
     **/
    @ApiModelProperty(value = "")
    public List<LibPersonDTO> getComposers() {
        return composers;
    }

    public void setComposers(List<LibPersonDTO> composers) {
        this.composers = composers;
    }

    public LibMediaItemDTO addComposer(LibPersonDTO composer) {
        this.composers.add(composer);
        return this;
    }

    public LibMediaItemDTO idx(String idx) {
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

    public LibMediaItemDTO label(LibLabelDTO label) {
        this.label = label;
        return this;
    }

    /**
     * Get label
     *
     * @return label
     **/
    @ApiModelProperty(value = "")
    public LibLabelDTO getLabel() {
        return label;
    }

    public void setLabel(LibLabelDTO label) {
        this.label = label;
    }

    public LibMediaItemDTO length(Double length) {
        this.length = length;
        return this;
    }

    /**
     * Get length
     *
     * @return length
     **/
    @ApiModelProperty(value = "")
    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public LibMediaItemDTO library(LibLibraryDTO library) {
        this.library = library;
        return this;
    }

    /**
     * Get library
     *
     * @return library
     **/
    @ApiModelProperty(value = "")
    public LibLibraryDTO getLibrary() {
        return library;
    }

    public void setLibrary(LibLibraryDTO library) {
        this.library = library;
    }

    public LibMediaItemDTO markers(List<LibMarkerDTO> markers) {
        this.markers = markers;
        return this;
    }

    public LibMediaItemDTO addMarkersItem(LibMarkerDTO markersItem) {
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

    public LibMediaItemDTO name(String name) {
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

    public LibMediaItemDTO properties(List<CorKeyValueDTO> properties) {
        this.properties = properties;
        return this;
    }

    public LibMediaItemDTO addPropertiesItem(CorKeyValueDTO propertiesItem) {
        this.properties.add(propertiesItem);
        return this;
    }

    /**
     * Get properties
     *
     * @return properties
     **/
    @ApiModelProperty(value = "")
    public List<CorKeyValueDTO> getProperties() {
        return properties;
    }

    public void setProperties(List<CorKeyValueDTO> properties) {
        this.properties = properties;
    }

    public LibMediaItemDTO resourceType(LibItemTypeEnum resourceType) {
        this.itemType = resourceType;
        return this;
    }

    /**
     * Get itemType
     *
     * @return itemType
     **/
    @ApiModelProperty(value = "")
    public LibItemTypeEnum getItemType() {
        return itemType;
    }

    public void setItemType(LibItemTypeEnum itemType) {
        this.itemType = itemType;
    }

    public LibMediaItemDTO state(LibItemStateEnum state) {
        this.state = state;
        return this;
    }

    /**
     * Get state
     *
     * @return state
     **/
    @ApiModelProperty(value = "")
    public LibItemStateEnum getState() {
        return state;
    }

    public void setState(LibItemStateEnum state) {
        this.state = state;
    }

    public LibMediaItemDTO stream(String stream) {
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

    public LibMediaItemDTO tags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public LibMediaItemDTO addTagsItem(String tagsItem) {
        this.tags.add(tagsItem);
        return this;
    }

    /**
     * Get tags
     *
     * @return tags
     **/
    @ApiModelProperty(value = "")
    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public LibMediaItemDTO track(LibTrackDTO track) {
        this.track = track;
        return this;
    }

    /**
     * Get track
     *
     * @return track
     **/
    @ApiModelProperty(value = "")
    public LibTrackDTO getTrack() {
        return track;
    }

    public void setTrack(LibTrackDTO track) {
        this.track = track;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LibMediaItemDTO libMediaItemDTO = (LibMediaItemDTO) o;
        return Objects.equals(this.album, libMediaItemDTO.album) &&
            Objects.equals(this.artist, libMediaItemDTO.artist) &&
            Objects.equals(this.authors, libMediaItemDTO.authors) &&
            Objects.equals(this.composers, libMediaItemDTO.composers) &&
            Objects.equals(this.idx, libMediaItemDTO.idx) &&
            Objects.equals(this.label, libMediaItemDTO.label) &&
            Objects.equals(this.length, libMediaItemDTO.length) &&
            Objects.equals(this.library, libMediaItemDTO.library) &&
            Objects.equals(this.markers, libMediaItemDTO.markers) &&
            Objects.equals(this.name, libMediaItemDTO.name) &&
            Objects.equals(this.properties, libMediaItemDTO.properties) &&
            Objects.equals(this.itemType, libMediaItemDTO.itemType) &&
            Objects.equals(this.state, libMediaItemDTO.state) &&
            Objects.equals(this.stream, libMediaItemDTO.stream) &&
            Objects.equals(this.tags, libMediaItemDTO.tags) &&
            Objects.equals(this.id, libMediaItemDTO.id) &&
            Objects.equals(this.track, libMediaItemDTO.track);
    }

    @Override
    public int hashCode() {
        return Objects.hash(album, artist, authors, composers, idx, label, length, library, markers, name, properties, itemType, state, stream, tags, track, id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class LibMediaItemDTO {\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    album: ").append(toIndentedString(album)).append("\n");
        sb.append("    artist: ").append(toIndentedString(artist)).append("\n");
        sb.append("    authors: ").append(toIndentedString(authors)).append("\n");
        sb.append("    composers: ").append(toIndentedString(composers)).append("\n");
        sb.append("    idx: ").append(toIndentedString(idx)).append("\n");
        sb.append("    label: ").append(toIndentedString(label)).append("\n");
        sb.append("    length: ").append(toIndentedString(length)).append("\n");
        sb.append("    library: ").append(toIndentedString(library)).append("\n");
        sb.append("    markers: ").append(toIndentedString(markers)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
        sb.append("    itemType: ").append(toIndentedString(itemType)).append("\n");
        sb.append("    state: ").append(toIndentedString(state)).append("\n");
        sb.append("    stream: ").append(toIndentedString(stream)).append("\n");
        sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
        sb.append("    track: ").append(toIndentedString(track)).append("\n");
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

