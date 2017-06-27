package io.protone.application.web.rest.dto.library;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * LibTrackDTO
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class LibTrackDTO {
  @JsonProperty("albumId")
  private Long albumId = null;

  @JsonProperty("artistId")
  private Long artistId = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("discNo")
  private Integer discNo = null;

  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("length")
  private Long length = null;

  @JsonProperty("name")
  private String name = null;


  @JsonProperty("trackNo")
  private Integer trackNo = null;

  public LibTrackDTO albumId(Long albumId) {
    this.albumId = albumId;
    return this;
  }

   /**
   * Get albumId
   * @return albumId
  **/
  @ApiModelProperty(value = "")
  public Long getAlbumId() {
    return albumId;
  }

  public void setAlbumId(Long albumId) {
    this.albumId = albumId;
  }

  public LibTrackDTO artistId(Long artistId) {
    this.artistId = artistId;
    return this;
  }

   /**
   * Get artistId
   * @return artistId
  **/
  @ApiModelProperty(value = "")
  public Long getArtistId() {
    return artistId;
  }

  public void setArtistId(Long artistId) {
    this.artistId = artistId;
  }

  public LibTrackDTO description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(value = "")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LibTrackDTO discNo(Integer discNo) {
    this.discNo = discNo;
    return this;
  }

   /**
   * Get discNo
   * @return discNo
  **/
  @ApiModelProperty(required = true, value = "")
  public Integer getDiscNo() {
    return discNo;
  }

  public void setDiscNo(Integer discNo) {
    this.discNo = discNo;
  }

  public LibTrackDTO id(Long id) {
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

  public LibTrackDTO length(Long length) {
    this.length = length;
    return this;
  }

   /**
   * Get length
   * @return length
  **/
  @ApiModelProperty(required = true, value = "")
  public Long getLength() {
    return length;
  }

  public void setLength(Long length) {
    this.length = length;
  }

  public LibTrackDTO name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(required = true, value = "")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LibTrackDTO trackNo(Integer trackNo) {
    this.trackNo = trackNo;
    return this;
  }

   /**
   * Get trackNo
   * @return trackNo
  **/
  @ApiModelProperty(required = true, value = "")
  public Integer getTrackNo() {
    return trackNo;
  }

  public void setTrackNo(Integer trackNo) {
    this.trackNo = trackNo;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LibTrackDTO libTrackDTO = (LibTrackDTO) o;
    return Objects.equals(this.albumId, libTrackDTO.albumId) &&
        Objects.equals(this.artistId, libTrackDTO.artistId) &&
        Objects.equals(this.description, libTrackDTO.description) &&
        Objects.equals(this.discNo, libTrackDTO.discNo) &&
        Objects.equals(this.id, libTrackDTO.id) &&
        Objects.equals(this.length, libTrackDTO.length) &&
        Objects.equals(this.name, libTrackDTO.name) &&
        Objects.equals(this.trackNo, libTrackDTO.trackNo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(albumId, artistId, description, discNo, id, length, name, trackNo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LibTrackDTO {\n");

    sb.append("    albumId: ").append(toIndentedString(albumId)).append("\n");
    sb.append("    artistId: ").append(toIndentedString(artistId)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    discNo: ").append(toIndentedString(discNo)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    length: ").append(toIndentedString(length)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    trackNo: ").append(toIndentedString(trackNo)).append("\n");
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

