package io.protone.web.rest.dto.traffic;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * TraPlaylistDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class TraPlaylistDTO   {
  @JsonProperty("blocks")
  private List<TraBlockDTO> blocks = new ArrayList<TraBlockDTO>();

  public TraPlaylistDTO blocks(List<TraBlockDTO> blocks) {
    this.blocks = blocks;
    return this;
  }

  public TraPlaylistDTO addBlocksItem(TraBlockDTO blocksItem) {
    this.blocks.add(blocksItem);
    return this;
  }

   /**
   * Get blocks
   * @return blocks
  **/
  @ApiModelProperty(value = "")
  public List<TraBlockDTO> getBlocks() {
    return blocks;
  }

  public void setBlocks(List<TraBlockDTO> blocks) {
    this.blocks = blocks;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TraPlaylistDTO traPlaylistDTO = (TraPlaylistDTO) o;
    return Objects.equals(this.blocks, traPlaylistDTO.blocks);
  }

  @Override
  public int hashCode() {
    return Objects.hash(blocks);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TraPlaylistDTO {\n");

    sb.append("    blocks: ").append(toIndentedString(blocks)).append("\n");
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

