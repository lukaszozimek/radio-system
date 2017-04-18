package io.protone.custom.service.dto.thin;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * A DTO for the SchPlaylist entity.
 */
public class SchPlaylistThinPT implements Serializable {

    private Long id;
    @NotNull
    private LocalDate date;

    private List<SchClockThinPT> clocks;

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

    public List<SchClockThinPT> getClocks() {
        return clocks;
    }

    public void setClocks(List<SchClockThinPT> clocks) {
        this.clocks = clocks;
    }

    public SchPlaylistThinPT id(Long id) {
        this.id = id;
        return this;
    }

    public SchPlaylistThinPT date(LocalDate date) {
        this.date = date;
        return this;
    }

    public SchPlaylistThinPT blocks(List<SchClockThinPT> clocks) {
        this.clocks = clocks;
        return this;
    }

    public SchPlaylistThinPT addBlock(SchClockThinPT clocks) {
        this.clocks.add(clocks);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchPlaylistThinPT)) return false;

        SchPlaylistThinPT that = (SchPlaylistThinPT) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getDate() != null ? !getDate().equals(that.getDate()) : that.getDate() != null) return false;

        return getClocks() != null ? getClocks().equals(that.getClocks()) : that.getClocks() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        result = 31 * result + (getClocks() != null ? getClocks().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SchPlaylistPT{");
        sb.append("id=").append(id);
        sb.append(", date=").append(date);
        sb.append(", clocks=").append(clocks);
        sb.append('}');
        return sb.toString();
    }

}
