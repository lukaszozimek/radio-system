package io.protone.traffic.api.dto;

import io.protone.core.api.dto.thin.CoreUserThinDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * TraPlaylistDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class TraPlaylistDTO implements Serializable {


    private Long id = null;

    @NotNull
    private LocalDate playlistDate;

    private List<TraBlockDTO> blocks = new ArrayList<TraBlockDTO>();

    private CoreUserThinDTO createdBy;

    private ZonedDateTime createdDate;

    private CoreUserThinDTO lastModifiedBy;

    private ZonedDateTime lastModifiedDate;


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

    public CoreUserThinDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(CoreUserThinDTO createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public CoreUserThinDTO getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(CoreUserThinDTO lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
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
                ", createdBy=" + createdBy +
                ", createdDate=" + createdDate +
                ", lastModifiedBy=" + lastModifiedBy +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TraPlaylistDTO that = (TraPlaylistDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (playlistDate != null ? !playlistDate.equals(that.playlistDate) : that.playlistDate != null) return false;
        if (blocks != null ? !blocks.equals(that.blocks) : that.blocks != null) return false;
        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        if (lastModifiedBy != null ? !lastModifiedBy.equals(that.lastModifiedBy) : that.lastModifiedBy != null)
            return false;
        if (lastModifiedDate != null ? !lastModifiedDate.equals(that.lastModifiedDate) : that.lastModifiedDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (playlistDate != null ? playlistDate.hashCode() : 0);
        result = 31 * result + (blocks != null ? blocks.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (lastModifiedBy != null ? lastModifiedBy.hashCode() : 0);
        result = 31 * result + (lastModifiedDate != null ? lastModifiedDate.hashCode() : 0);
        return result;
    }
}

