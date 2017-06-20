package io.protone.web.rest.dto.library;

import io.protone.domain.enumeration.LibItemStateEnum;
import io.protone.domain.enumeration.LibItemTypeEnum;
import io.protone.web.rest.dto.cor.CorKeyValueDTO;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    private List<LibImageItemDTO> libImageItem = new ArrayList<>();

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

    public List<LibImageItemDTO> getLibImageItem() {
        return libImageItem;
    }

    public void setLibImageItem(List<LibImageItemDTO> libImageItem) {
        this.libImageItem = libImageItem;
    }

    public LibMediaItemDTO addImage(LibImageItemDTO libImageItemDTO) {
        this.libImageItem.add(libImageItemDTO);
        return this;
    }

    @Override
    public String toString() {
        return "LibMediaItemDTO{" +
            "id=" + id +
            ", album=" + album +
            ", artist=" + artist +
            ", authors=" + authors +
            ", composers=" + composers +
            ", idx='" + idx + '\'' +
            ", command='" + command + '\'' +
            ", description='" + description + '\'' +
            ", label=" + label +
            ", length=" + length +
            ", library=" + library +
            ", markers=" + markers +
            ", name='" + name + '\'' +
            ", properties=" + properties +
            ", itemType=" + itemType +
            ", state=" + state +
            ", stream='" + stream + '\'' +
            ", tags=" + tags +
            ", track=" + track +
            ", libImageItem=" + libImageItem +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LibMediaItemDTO that = (LibMediaItemDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (album != null ? !album.equals(that.album) : that.album != null) return false;
        if (artist != null ? !artist.equals(that.artist) : that.artist != null) return false;
        if (authors != null ? !authors.equals(that.authors) : that.authors != null) return false;
        if (composers != null ? !composers.equals(that.composers) : that.composers != null) return false;
        if (idx != null ? !idx.equals(that.idx) : that.idx != null) return false;
        if (command != null ? !command.equals(that.command) : that.command != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (label != null ? !label.equals(that.label) : that.label != null) return false;
        if (length != null ? !length.equals(that.length) : that.length != null) return false;
        if (library != null ? !library.equals(that.library) : that.library != null) return false;
        if (markers != null ? !markers.equals(that.markers) : that.markers != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (properties != null ? !properties.equals(that.properties) : that.properties != null) return false;
        if (itemType != that.itemType) return false;
        if (state != that.state) return false;
        if (stream != null ? !stream.equals(that.stream) : that.stream != null) return false;
        if (tags != null ? !tags.equals(that.tags) : that.tags != null) return false;
        if (track != null ? !track.equals(that.track) : that.track != null) return false;
        return libImageItem != null ? libImageItem.equals(that.libImageItem) : that.libImageItem == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (album != null ? album.hashCode() : 0);
        result = 31 * result + (artist != null ? artist.hashCode() : 0);
        result = 31 * result + (authors != null ? authors.hashCode() : 0);
        result = 31 * result + (composers != null ? composers.hashCode() : 0);
        result = 31 * result + (idx != null ? idx.hashCode() : 0);
        result = 31 * result + (command != null ? command.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + (length != null ? length.hashCode() : 0);
        result = 31 * result + (library != null ? library.hashCode() : 0);
        result = 31 * result + (markers != null ? markers.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (properties != null ? properties.hashCode() : 0);
        result = 31 * result + (itemType != null ? itemType.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (stream != null ? stream.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (track != null ? track.hashCode() : 0);
        result = 31 * result + (libImageItem != null ? libImageItem.hashCode() : 0);
        return result;
    }

}

