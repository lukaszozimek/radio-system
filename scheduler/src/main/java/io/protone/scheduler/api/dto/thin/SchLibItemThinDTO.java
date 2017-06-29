package io.protone.scheduler.api.dto.thin;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.library.api.dto.LibMarkerDTO;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * SchLibItemThinDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class SchLibItemThinDTO   {
  @JsonProperty("library")
  private String library = null;

  @JsonProperty("album")
  private String album = null;

  @JsonProperty("artist")
  private String artist = null;

  @JsonProperty("cover")
  private String cover = null;

  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("idx")
  private String idx = null;

  @JsonProperty("length")
  private Integer length = null;

  @JsonProperty("markers")
  private List<LibMarkerDTO> markers = new ArrayList<LibMarkerDTO>();

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("stream")
  private String stream = null;

  public SchLibItemThinDTO library(String library) {
    this.library = library;
    return this;
  }

   /**
   * Get library
   * @return library
  **/
  @ApiModelProperty(value = "")
  public String getLibrary() {
    return library;
  }

  public void setLibrary(String library) {
    this.library = library;
  }

  public SchLibItemThinDTO album(String album) {
    this.album = album;
    return this;
  }

   /**
   * Get album
   * @return album
  **/
  @ApiModelProperty(value = "")
  public String getAlbum() {
    return album;
  }

  public void setAlbum(String album) {
    this.album = album;
  }

  public SchLibItemThinDTO artist(String artist) {
    this.artist = artist;
    return this;
  }

   /**
   * Get artist
   * @return artist
  **/
  @ApiModelProperty(value = "")
  public String getArtist() {
    return artist;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  public SchLibItemThinDTO cover(String cover) {
    this.cover = cover;
    return this;
  }

   /**
   * Get cover
   * @return cover
  **/
  @ApiModelProperty(value = "")
  public String getCover() {
    return cover;
  }

  public void setCover(String cover) {
    this.cover = cover;
  }

  public SchLibItemThinDTO id(Long id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public SchLibItemThinDTO idx(String idx) {
    this.idx = idx;
    return this;
  }

   /**
   * Get idx
   * @return idx
  **/
  @ApiModelProperty(value = "")
  public String getIdx() {
    return idx;
  }

  public void setIdx(String idx) {
    this.idx = idx;
  }

  public SchLibItemThinDTO length(Integer length) {
    this.length = length;
    return this;
  }

   /**
   * Get length
   * @return length
  **/
  @ApiModelProperty(value = "")
  public Integer getLength() {
    return length;
  }

  public void setLength(Integer length) {
    this.length = length;
  }

  public SchLibItemThinDTO markers(List<LibMarkerDTO> markers) {
    this.markers = markers;
    return this;
  }

  public SchLibItemThinDTO addMarkersItem(LibMarkerDTO markersItem) {
    this.markers.add(markersItem);
    return this;
  }

   /**
   * Get markers
   * @return markers
  **/
  @ApiModelProperty(value = "")
  public List<LibMarkerDTO> getMarkers() {
    return markers;
  }

  public void setMarkers(List<LibMarkerDTO> markers) {
    this.markers = markers;
  }

  public SchLibItemThinDTO name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(value = "")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public SchLibItemThinDTO stream(String stream) {
    this.stream = stream;
    return this;
  }

   /**
   * Get stream
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
    SchLibItemThinDTO schLibItemThinDTO = (SchLibItemThinDTO) o;
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

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SchLibItemThinDTO {\n");

    sb.append("    library: ").append(toIndentedString(library)).append("\n");
    sb.append("    album: ").append(toIndentedString(album)).append("\n");
    sb.append("    artist: ").append(toIndentedString(artist)).append("\n");
    sb.append("    cover: ").append(toIndentedString(cover)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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

