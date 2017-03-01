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
    private Long channelId;

    private Integer dimYear;
    private Integer dimMonth;
    private Integer dimDay;

    private List<SchBlockPT> blocks;

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

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Integer getDimYear() {
        return dimYear;
    }

    public void setDimYear(Integer dimYear) {
        this.dimYear = dimYear;
    }

    public Integer getDimMonth() {
        return dimMonth;
    }

    public void setDimMonth(Integer dimMonth) {
        this.dimMonth = dimMonth;
    }

    public Integer getDimDay() {
        return dimDay;
    }

    public void setDimDay(Integer dimDay) {
        this.dimDay = dimDay;
    }

    public List<SchBlockPT> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<SchBlockPT> blocks) {
        this.blocks = blocks;
    }

    public SchPlaylistPT id(Long id) {
        this.id = id;
        return this;
    }

    public SchPlaylistPT date(LocalDate date) {
        this.date = date;
        this.dimYear(date.getYear())
            .dimMonth(date.getMonthValue())
            .dimDay(date.getDayOfMonth());

        return this;
    }

    public SchPlaylistPT channelId(Long channelId) {
        this.channelId = channelId;
        return this;
    }

    public SchPlaylistPT dimYear(Integer dimYear) {
        this.dimYear = dimYear;
        return this;
    }

    public SchPlaylistPT dimMonth(Integer dimMonth) {
        this.dimMonth = dimMonth;
        return this;
    }

    public SchPlaylistPT dimDay(Integer dimDay) {
        this.dimDay = dimDay;
        return this;
    }

    public SchPlaylistPT blocks(List<SchBlockPT> blocks) {
        this.blocks = blocks;
        return this;
    }

    public SchPlaylistPT addBlock(SchBlockPT block) {
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
        if (getChannelId() != null ? !getChannelId().equals(that.getChannelId()) : that.getChannelId() != null)
            return false;
        if (getDimYear() != null ? !getDimYear().equals(that.getDimYear()) : that.getDimYear() != null) return false;
        if (getDimMonth() != null ? !getDimMonth().equals(that.getDimMonth()) : that.getDimMonth() != null)
            return false;
        if (getDimDay() != null ? !getDimDay().equals(that.getDimDay()) : that.getDimDay() != null) return false;
        return getBlocks() != null ? getBlocks().equals(that.getBlocks()) : that.getBlocks() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        result = 31 * result + (getChannelId() != null ? getChannelId().hashCode() : 0);
        result = 31 * result + (getDimYear() != null ? getDimYear().hashCode() : 0);
        result = 31 * result + (getDimMonth() != null ? getDimMonth().hashCode() : 0);
        result = 31 * result + (getDimDay() != null ? getDimDay().hashCode() : 0);
        result = 31 * result + (getBlocks() != null ? getBlocks().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SchPlaylistPT{");
        sb.append("id=").append(id);
        sb.append(", date=").append(date);
        sb.append(", channelId=").append(channelId);
        sb.append(", dimYear=").append(dimYear);
        sb.append(", dimMonth=").append(dimMonth);
        sb.append(", dimDay=").append(dimDay);
        sb.append(", blocks=").append(blocks);
        sb.append('}');
        return sb.toString();
    }
}
