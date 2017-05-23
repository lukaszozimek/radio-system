package io.protone.web.rest.dto.traffic;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * TraPlaylistDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class TraPlaylistDTO implements Serializable {


    private Long id = null;

    @NotNull
    private LocalDate playlistDate;

    private List<TraBlockDTO> blocks = new ArrayList<TraBlockDTO>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPlaylistDate() {
        return playlistDate;
    }

    public void setPlaylistDate(LocalDate playlistDate) {
        this.playlistDate = playlistDate;
    }

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
     *
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

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    @Override
    public String toString() {
        return "TraPlaylistDTO{" +
            "id=" + id +
            ", playlistDate=" + playlistDate +
            ", blocks=" + blocks +
            '}';
    }

    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

