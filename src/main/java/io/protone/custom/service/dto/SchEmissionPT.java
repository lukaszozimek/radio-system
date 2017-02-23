package io.protone.custom.service.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A DTO for the SchEmission entity.
 */
public class SchEmissionPT implements Serializable {

    private Long id;

    @NotNull
    private Integer seq;

    @NotNull
    @Size(max = 100)
    private String name;

    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private Long length;

    private Integer dimYear;
    private Integer dimMonth;
    private Integer dimDay;
    private Integer dimHour;
    private Integer dimMinute;
    private Integer dimSecond;

    private LibItemPT mediaItem;
    private Long blockId;
    private Long campaingsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
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

    public Integer getDimHour() {
        return dimHour;
    }

    public void setDimHour(Integer dimHour) {
        this.dimHour = dimHour;
    }

    public Integer getDimMinute() {
        return dimMinute;
    }

    public void setDimMinute(Integer dimMinute) {
        this.dimMinute = dimMinute;
    }

    public Integer getDimSecond() {
        return dimSecond;
    }

    public void setDimSecond(Integer dimSecond) {
        this.dimSecond = dimSecond;
    }

    public LibItemPT getMediaItem() {
        return mediaItem;
    }

    public void setMediaItem(LibItemPT mediaItem) {
        this.mediaItem = mediaItem;
    }

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public Long getCampaingsId() {
        return campaingsId;
    }

    public void setCampaingsId(Long campaingsId) {
        this.campaingsId = campaingsId;
    }

    public SchEmissionPT id(Long id) {
        this.id = id;
        return this;
    }

    public SchEmissionPT seq(Integer seq) {
        this.seq = seq;
        return this;
    }

    public SchEmissionPT name(String name) {
        this.name = name;
        return this;
    }

    public SchEmissionPT startTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public SchEmissionPT endTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public SchEmissionPT length(Long length) {
        this.length = length;
        return this;
    }

    public SchEmissionPT dimYear(Integer dimYear) {
        this.dimYear = dimYear;
        return this;
    }

    public SchEmissionPT dimMonth(Integer dimMonth) {
        this.dimMonth = dimMonth;
        return this;
    }

    public SchEmissionPT dimDay(Integer dimDay) {
        this.dimDay = dimDay;
        return this;
    }

    public SchEmissionPT dimHour(Integer dimHour) {
        this.dimHour = dimHour;
        return this;
    }

    public SchEmissionPT dimMinute(Integer dimMinute) {
        this.dimMinute = dimMinute;
        return this;
    }

    public SchEmissionPT dimSecond(Integer dimSecond) {
        this.dimSecond = dimSecond;
        return this;
    }

    public SchEmissionPT mediaItem(LibItemPT mediaItem) {
        this.mediaItem = mediaItem;
        return this;
    }

    public SchEmissionPT blockId(Long blockId) {
        this.blockId = blockId;
        return this;
    }

    public SchEmissionPT campaingsId(Long campaingsId) {
        this.campaingsId = campaingsId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchEmissionPT)) return false;

        SchEmissionPT that = (SchEmissionPT) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getSeq() != null ? !getSeq().equals(that.getSeq()) : that.getSeq() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getStartTime() != null ? !getStartTime().equals(that.getStartTime()) : that.getStartTime() != null)
            return false;
        if (getEndTime() != null ? !getEndTime().equals(that.getEndTime()) : that.getEndTime() != null) return false;
        if (getLength() != null ? !getLength().equals(that.getLength()) : that.getLength() != null) return false;
        if (getDimYear() != null ? !getDimYear().equals(that.getDimYear()) : that.getDimYear() != null) return false;
        if (getDimMonth() != null ? !getDimMonth().equals(that.getDimMonth()) : that.getDimMonth() != null)
            return false;
        if (getDimDay() != null ? !getDimDay().equals(that.getDimDay()) : that.getDimDay() != null) return false;
        if (getDimHour() != null ? !getDimHour().equals(that.getDimHour()) : that.getDimHour() != null) return false;
        if (getDimMinute() != null ? !getDimMinute().equals(that.getDimMinute()) : that.getDimMinute() != null)
            return false;
        if (getDimSecond() != null ? !getDimSecond().equals(that.getDimSecond()) : that.getDimSecond() != null)
            return false;
        if (getMediaItem() != null ? !getMediaItem().equals(that.getMediaItem()) : that.getMediaItem() != null)
            return false;
        if (getBlockId() != null ? !getBlockId().equals(that.getBlockId()) : that.getBlockId() != null) return false;
        return getCampaingsId() != null ? getCampaingsId().equals(that.getCampaingsId()) : that.getCampaingsId() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getSeq() != null ? getSeq().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getStartTime() != null ? getStartTime().hashCode() : 0);
        result = 31 * result + (getEndTime() != null ? getEndTime().hashCode() : 0);
        result = 31 * result + (getLength() != null ? getLength().hashCode() : 0);
        result = 31 * result + (getDimYear() != null ? getDimYear().hashCode() : 0);
        result = 31 * result + (getDimMonth() != null ? getDimMonth().hashCode() : 0);
        result = 31 * result + (getDimDay() != null ? getDimDay().hashCode() : 0);
        result = 31 * result + (getDimHour() != null ? getDimHour().hashCode() : 0);
        result = 31 * result + (getDimMinute() != null ? getDimMinute().hashCode() : 0);
        result = 31 * result + (getDimSecond() != null ? getDimSecond().hashCode() : 0);
        result = 31 * result + (getMediaItem() != null ? getMediaItem().hashCode() : 0);
        result = 31 * result + (getBlockId() != null ? getBlockId().hashCode() : 0);
        result = 31 * result + (getCampaingsId() != null ? getCampaingsId().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SchEmissionPT{");
        sb.append("id=").append(id);
        sb.append(", seq=").append(seq);
        sb.append(", name='").append(name).append('\'');
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", length=").append(length);
        sb.append(", dimYear=").append(dimYear);
        sb.append(", dimMonth=").append(dimMonth);
        sb.append(", dimDay=").append(dimDay);
        sb.append(", dimHour=").append(dimHour);
        sb.append(", dimMinute=").append(dimMinute);
        sb.append(", dimSecond=").append(dimSecond);
        sb.append(", mediaItem=").append(mediaItem);
        sb.append(", blockId=").append(blockId);
        sb.append(", campaingsId=").append(campaingsId);
        sb.append('}');
        return sb.toString();
    }
}
