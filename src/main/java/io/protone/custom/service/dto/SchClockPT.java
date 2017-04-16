package io.protone.custom.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * A DTO for the SchPlaylist entity.
 */
public class SchClockPT implements Serializable {

    private Long id;
    private String shortName;
    private Long channelId;
    private List<SchBlockPT> blocks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return shortName;
    }

    public void setDate(String date) {
        this.shortName = shortName;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }


    public List<SchBlockPT> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<SchBlockPT> blocks) {
        this.blocks = blocks;
    }

    public SchClockPT id(Long id) {
        this.id = id;
        return this;
    }

    public SchClockPT blocks(List<SchBlockPT> blocks) {
        this.blocks = blocks;
        return this;
    }

    public SchClockPT addBlock(SchBlockPT block) {
        this.blocks.add(block);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchClockPT)) return false;

        SchClockPT that = (SchClockPT) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getDate() != null ? !getDate().equals(that.getDate()) : that.getDate() != null) return false;
        if (getChannelId() != null ? !getChannelId().equals(that.getChannelId()) : that.getChannelId() != null)
            return false;
        return getBlocks() != null ? getBlocks().equals(that.getBlocks()) : that.getBlocks() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        result = 31 * result + (getChannelId() != null ? getChannelId().hashCode() : 0);
        result = 31 * result + (getBlocks() != null ? getBlocks().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SchPlaylistPT{");
        sb.append("id=").append(id);
        sb.append(", shortName=").append(shortName);
        sb.append(", channelId=").append(channelId);
        sb.append(", blocks=").append(blocks);
        sb.append('}');
        return sb.toString();
    }
}
