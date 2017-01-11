package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * SchPlaylistPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class SchPlaylistPT   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("block")
  private SchBlockPT block = null;

  public SchPlaylistPT id(Long id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(required = true, value = "")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public SchPlaylistPT block(SchBlockPT block) {
    this.block = block;
    return this;
  }

   /**
   * Get block
   * @return block
  **/
  @ApiModelProperty(value = "")
  public SchBlockPT getBlock() {
    return block;
  }

  public void setBlock(SchBlockPT block) {
    this.block = block;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SchPlaylistPT schPlaylistPT = (SchPlaylistPT) o;
    return Objects.equals(this.id, schPlaylistPT.id) &&
        Objects.equals(this.block, schPlaylistPT.block);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, block);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SchPlaylistPT {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    block: ").append(toIndentedString(block)).append("\n");
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

