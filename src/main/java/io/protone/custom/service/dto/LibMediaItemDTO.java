package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.protone.domain.enumeration.LibItemTypeEnum;
import io.protone.web.rest.dto.library.LibLibraryDTO;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * LibMediaItemDTO
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class LibMediaItemDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("album")
    private LibAlbumDTO album = null;

    @JsonProperty("itemType")
    private LibItemTypeEnum libItemTypeEnum = null;

    @JsonProperty("artist")
    private LibArtistDTO artist = null;

    @JsonProperty("authors")
    private List<LibPersonDTO> authors = new ArrayList<>();

    @JsonProperty("composers")
    private List<LibPersonDTO> composers = new ArrayList<>();

    @JsonProperty("idx")
    private String idx = null;

    @JsonProperty("label")
    private LibLabelDTO label = null;

    @JsonProperty("length")
    private Integer length = null;

    @JsonProperty("library")
    private LibLibraryDTO library = null;

    @JsonProperty("markers")
    private List<LibMarkerDTO> markers = new ArrayList<LibMarkerDTO>();

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("properties")
    private List<CoreKeyValuePT> properties = new ArrayList<CoreKeyValuePT>();

    @JsonProperty("resourceType")
    private ResourceTypeEnum resourceType = null;

    @JsonProperty("state")
    private StateEnum state = null;

    @JsonProperty("stream")
    private String stream = null;

    @JsonProperty("tags")
    private List<String> tags = new ArrayList<String>();

    @JsonProperty("track")
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

    public LibMediaItemDTO length(Integer length) {
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

    public LibMediaItemDTO properties(List<CoreKeyValuePT> properties) {
        this.properties = properties;
        return this;
    }

    public LibMediaItemDTO addPropertiesItem(CoreKeyValuePT propertiesItem) {
        this.properties.add(propertiesItem);
        return this;
    }

    /**
     * Get properties
     *
     * @return properties
     **/
    @ApiModelProperty(value = "")
    public List<CoreKeyValuePT> getProperties() {
        return properties;
    }

    public void setProperties(List<CoreKeyValuePT> properties) {
        this.properties = properties;
    }

    public LibMediaItemDTO resourceType(ResourceTypeEnum resourceType) {
        this.resourceType = resourceType;
        return this;
    }

    /**
     * Get resourceType
     *
     * @return resourceType
     **/
    @ApiModelProperty(value = "")
    public ResourceTypeEnum getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceTypeEnum resourceType) {
        this.resourceType = resourceType;
    }

    public LibMediaItemDTO state(StateEnum state) {
        this.state = state;
        return this;
    }

    /**
     * Get state
     *
     * @return state
     **/
    @ApiModelProperty(value = "")
    public StateEnum getState() {
        return state;
    }

    public void setState(StateEnum state) {
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
            Objects.equals(this.resourceType, libMediaItemDTO.resourceType) &&
            Objects.equals(this.state, libMediaItemDTO.state) &&
            Objects.equals(this.stream, libMediaItemDTO.stream) &&
            Objects.equals(this.tags, libMediaItemDTO.tags) &&
            Objects.equals(this.id, libMediaItemDTO.id) &&
            Objects.equals(this.track, libMediaItemDTO.track);
    }

    @Override
    public int hashCode() {
        return Objects.hash(album, artist, authors, composers, idx, label, length, library, markers, name, properties, resourceType, state, stream, tags, track, id);
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
        sb.append("    resourceType: ").append(toIndentedString(resourceType)).append("\n");
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

    /**
     * Gets or Sets resourceType
     */
    public enum ResourceTypeEnum {
        AUDIO("IT_AUDIO"),

        VIDEO("IT_VIDEO"),

        COMMAND("IT_COMMAND"),

        OTHER("IT_OTHER");

        private String value;

        ResourceTypeEnum(String value) {
            this.value = value;
        }

        @JsonCreator
        public static ResourceTypeEnum fromValue(String text) {
            for (ResourceTypeEnum b : ResourceTypeEnum.values()) {
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
     * Gets or Sets state
     */
    public enum StateEnum {
        NEW("IS_NEW"),

        POSTPROCESS("IS_POSTPROCESS"),

        ENABLED("IS_ENABLED"),

        DISABLED("IS_DISABLED"),

        ARCHIVED("IS_ARCHIVED"),

        DELETED("IS_DELETED"),

        ERROR("IS_ERROR"),

        OTHER("IS_OTHER");

        private String value;

        StateEnum(String value) {
            this.value = value;
        }

        @JsonCreator
        public static StateEnum fromValue(String text) {
            for (StateEnum b : StateEnum.values()) {
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

