package io.protone.custom.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the SchPlaylist entity.
 */
public class SchPlaylistPT implements Serializable {

    private Long id;

    @NotNull
    private LocalDate date;

    private List<SchClockPT> blocks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<SchClockPT> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<SchClockPT> blocks) {
        this.blocks = blocks;
    }

    public SchPlaylistPT id(Long id) {
        this.id = id;
        return this;
    }

    public SchPlaylistPT date(LocalDate date) {
        this.date = date;
        return this;
    }


    public SchPlaylistPT blocks(List<SchClockPT> blocks) {
        this.blocks = blocks;
        return this;
    }

    public SchPlaylistPT addBlock(SchClockPT block) {
        this.blocks.add(block);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchPlaylistPT)) return false;

        SchPlaylistPT that = (SchPlaylistPT) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getDate() != null ? !getDate().equals(that.getDate()) : that.getDate() != null) return false;

        return getBlocks() != null ? getBlocks().equals(that.getBlocks()) : that.getBlocks() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        result = 31 * result + (getBlocks() != null ? getBlocks().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SchPlaylistPT{");
        sb.append("id=").append(id);
        sb.append(", date=").append(date);
        sb.append(", clocks=").append(blocks);
        sb.append('}');
        return sb.toString();
    }
}
