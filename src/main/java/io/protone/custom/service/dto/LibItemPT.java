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

public class LibItemPT {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("album")
    private LibAlbumPT album = null;

    @JsonProperty("itemType")
    private LibItemTypeEnum libItemTypeEnum = null;
    @JsonProperty("artist")
    private LibArtistPT artist = null;

    @JsonProperty("authors")
    private List<LibPersonPT> authors = new ArrayList<>();

    @JsonProperty("composers")
    private List<LibPersonPT> composers = new ArrayList<>();

    @JsonProperty("idx")
    private String idx = null;

    @JsonProperty("label")
    private LibLabelPT label = null;

    @JsonProperty("length")
    private Integer length = null;

    @JsonProperty("library")
    private LibraryPT library = null;

    @JsonProperty("markers")
    private List<LibMarkerPT> markers = new ArrayList<LibMarkerPT>();

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
    private LibTrackPT track = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LibItemPT album(LibAlbumPT album) {
        this.album = album;
        return this;
    }

    /**
     * Get album
     *
     * @return album
     **/
    @ApiModelProperty(value = "")
    public LibAlbumPT getAlbum() {
        return album;
    }

    public void setAlbum(LibAlbumPT album) {
        this.album = album;
    }

    public LibItemPT artist(LibArtistPT artist) {
        this.artist = artist;
        return this;
    }

    /**
     * Get artist
     *
     * @return artist
     **/
    @ApiModelProperty(value = "")
    public LibArtistPT getArtist() {
        return artist;
    }

    public void setArtist(LibArtistPT artist) {
        this.artist = artist;
    }

    public LibItemPT author(List<LibPersonPT> author) {
        this.authors = author;
        return this;
    }

    public LibItemPT addAuthors(LibPersonPT composer) {
        this.authors.add(composer);
        return this;
    }

    /**
     * Get authors
     *
     * @return authors
     **/
    @ApiModelProperty(value = "")
    public List<LibPersonPT> getAuthors() {
        return authors;
    }

    public void setAuthors(List<LibPersonPT> authors) {
        this.authors = this.authors;
    }

    public LibItemPT composer(List<LibPersonPT> composers) {
        this.composers = composers;
        return this;
    }

    /**
     * Get composers
     *
     * @return composers
     **/
    @ApiModelProperty(value = "")
    public List<LibPersonPT> getComposers() {
        return composers;
    }

    public void setComposers(List<LibPersonPT> composers) {
        this.composers = composers;
    }

    public LibItemPT addComposer(LibPersonPT composer) {
        this.composers.add(composer);
        return this;
    }

    public LibItemPT idx(String idx) {
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

    public LibItemPT label(LibLabelPT label) {
        this.label = label;
        return this;
    }

    /**
     * Get label
     *
     * @return label
     **/
    @ApiModelProperty(value = "")
    public LibLabelPT getLabel() {
        return label;
    }

    public void setLabel(LibLabelPT label) {
        this.label = label;
    }

    public LibItemPT length(Integer length) {
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

    public LibItemPT library(LibraryPT library) {
        this.library = library;
        return this;
    }

    /**
     * Get library
     *
     * @return library
     **/
    @ApiModelProperty(value = "")
    public LibraryPT getLibrary() {
        return library;
    }

    public void setLibrary(LibraryPT library) {
        this.library = library;
    }

    public LibItemPT markers(List<LibMarkerPT> markers) {
        this.markers = markers;
        return this;
    }

    public LibItemPT addMarkersItem(LibMarkerPT markersItem) {
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

    public LibItemPT name(String name) {
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

    public LibItemPT properties(List<CoreKeyValuePT> properties) {
        this.properties = properties;
        return this;
    }

    public LibItemPT addPropertiesItem(CoreKeyValuePT propertiesItem) {
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

    public LibItemPT resourceType(ResourceTypeEnum resourceType) {
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

    public LibItemPT state(StateEnum state) {
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

    public LibItemPT stream(String stream) {
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

    public LibItemPT tags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public LibItemPT addTagsItem(String tagsItem) {
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

    public LibItemPT track(LibTrackPT track) {
        this.track = track;
        return this;
    }

    /**
     * Get track
     *
     * @return track
     **/
    @ApiModelProperty(value = "")
    public LibTrackPT getTrack() {
        return track;
    }

    public void setTrack(LibTrackPT track) {
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
        LibItemPT libItemPT = (LibItemPT) o;
        return Objects.equals(this.album, libItemPT.album) &&
            Objects.equals(this.artist, libItemPT.artist) &&
            Objects.equals(this.authors, libItemPT.authors) &&
            Objects.equals(this.composers, libItemPT.composers) &&
            Objects.equals(this.idx, libItemPT.idx) &&
            Objects.equals(this.label, libItemPT.label) &&
            Objects.equals(this.length, libItemPT.length) &&
            Objects.equals(this.library, libItemPT.library) &&
            Objects.equals(this.markers, libItemPT.markers) &&
            Objects.equals(this.name, libItemPT.name) &&
            Objects.equals(this.properties, libItemPT.properties) &&
            Objects.equals(this.resourceType, libItemPT.resourceType) &&
            Objects.equals(this.state, libItemPT.state) &&
            Objects.equals(this.stream, libItemPT.stream) &&
            Objects.equals(this.tags, libItemPT.tags) &&
            Objects.equals(this.id, libItemPT.id) &&
            Objects.equals(this.track, libItemPT.track);
    }

    @Override
    public int hashCode() {
        return Objects.hash(album, artist, authors, composers, idx, label, length, library, markers, name, properties, resourceType, state, stream, tags, track, id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class LibItemPT {\n");
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

