package io.protone.custom.service.dto;


import io.protone.domain.enumeration.SchBlockTypeEnum;
import io.protone.domain.enumeration.SchStartTypeEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the SchBlock entity.
 */
public class SchBlockPT implements Serializable {

    private Long id;

    @NotNull
    private Integer seq;

    @NotNull
    @Size(max = 100)
    private String name;

    private SchBlockTypeEnum type;
    private SchStartTypeEnum startType;
    private Long relativeDelay;

    private ZonedDateTime scheduledStartTime;
    private ZonedDateTime scheduledEndTime;
    private Long scheduledLength;

    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private Long length;

    private Integer dimYear;
    private Integer dimMonth;
    private Integer dimDay;
    private Integer dimHour;
    private Integer dimMinute;
    private Integer dimSecond;

    private Long playlistId;
    private Long templateId;
    private Long parentBlockId;

    private List<SchBlockPT> blocks;
    private List<SchEmissionPT> emissions;

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

    public SchBlockTypeEnum getType() {
        return type;
    }

    public void setType(SchBlockTypeEnum type) {
        this.type = type;
    }

    public SchStartTypeEnum getStartType() {
        return startType;
    }

    public void setStartType(SchStartTypeEnum startType) {
        this.startType = startType;
    }

    public Long getRelativeDelay() {
        return relativeDelay;
    }

    public void setRelativeDelay(Long relativeDelay) {
        this.relativeDelay = relativeDelay;
    }

    public ZonedDateTime getScheduledStartTime() {
        return scheduledStartTime;
    }

    public void setScheduledStartTime(ZonedDateTime scheduledStartTime) {
        this.scheduledStartTime = scheduledStartTime;
    }

    public ZonedDateTime getScheduledEndTime() {
        return scheduledEndTime;
    }

    public void setScheduledEndTime(ZonedDateTime scheduledEndTime) {
        this.scheduledEndTime = scheduledEndTime;
    }

    public Long getScheduledLength() {
        return scheduledLength;
    }

    public void setScheduledLength(Long scheduledLength) {
        this.scheduledLength = scheduledLength;
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

    public Long getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(Long playlistId) {
        this.playlistId = playlistId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getParentBlockId() {
        return parentBlockId;
    }

    public void setParentBlockId(Long parentBlockId) {
        this.parentBlockId = parentBlockId;
    }

    public List<SchBlockPT> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<SchBlockPT> blocks) {
        this.blocks = blocks;
    }

    public List<SchEmissionPT> getEmissions() {
        return emissions;
    }

    public void setEmissions(List<SchEmissionPT> emissions) {
        this.emissions = emissions;
    }

    public SchBlockPT id(Long id) {
        this.id = id;
        return this;
    }

    public SchBlockPT seq(Integer seq) {
        this.seq = seq;
        return this;
    }

    public SchBlockPT name(String name) {
        this.name = name;
        return this;
    }

    public SchBlockPT type(SchBlockTypeEnum type) {
        this.type = type;
        return this;
    }

    public SchBlockPT startType(SchStartTypeEnum startType) {
        this.startType = startType;
        return this;
    }

    public SchBlockPT relativeDelay(Long relativeDelay) {
        this.relativeDelay = relativeDelay;
        return this;
    }

    public SchBlockPT scheduledStartTime(ZonedDateTime scheduledStartTime) {
        this.scheduledStartTime = scheduledStartTime;
        return this;
    }

    public SchBlockPT scheduledEndTime(ZonedDateTime scheduledEndTime) {
        this.scheduledEndTime = scheduledEndTime;
        return this;
    }

    public SchBlockPT scheduledLength(Long scheduledLength) {
        this.scheduledLength = scheduledLength;
        return this;
    }

    public SchBlockPT startTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public SchBlockPT endTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public SchBlockPT length(Long length) {
        this.length = length;
        return this;
    }

    public SchBlockPT dimYear(Integer dimYear) {
        this.dimYear = dimYear;
        return this;
    }

    public SchBlockPT dimMonth(Integer dimMonth) {
        this.dimMonth = dimMonth;
        return this;
    }

    public SchBlockPT dimDay(Integer dimDay) {
        this.dimDay = dimDay;
        return this;
    }

    public SchBlockPT dimHour(Integer dimHour) {
        this.dimHour = dimHour;
        return this;
    }

    public SchBlockPT dimMinute(Integer dimMinute) {
        this.dimMinute = dimMinute;
        return this;
    }

    public SchBlockPT dimSecond(Integer dimSecond) {
        this.dimSecond = dimSecond;
        return this;
    }

    public SchBlockPT playlistId(Long playlistId) {
        this.playlistId = playlistId;
        return this;
    }

    public SchBlockPT templateId(Long templateId) {
        this.templateId = templateId;
        return this;
    }

    public SchBlockPT parentBlockId(Long parentBlockId) {
        this.parentBlockId = parentBlockId;
        return this;
    }

    public SchBlockPT blocks(List<SchBlockPT> blocks) {
        this.blocks = blocks;
        return this;
    }

    public SchBlockPT addBlock(SchBlockPT block) {
        this.blocks.add(block);
        return this;
    }

    public SchBlockPT emissions(List<SchEmissionPT> emissions) {
        this.emissions = emissions;
        return this;
    }

    public SchBlockPT addEmission(SchEmissionPT emission) {
        this.emissions.add(emission);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchBlockPT)) return false;

        SchBlockPT that = (SchBlockPT) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getSeq() != null ? !getSeq().equals(that.getSeq()) : that.getSeq() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getType() != that.getType()) return false;
        if (getStartType() != that.getStartType()) return false;
        if (getRelativeDelay() != null ? !getRelativeDelay().equals(that.getRelativeDelay()) : that.getRelativeDelay() != null)
            return false;
        if (getScheduledStartTime() != null ? !getScheduledStartTime().equals(that.getScheduledStartTime()) : that.getScheduledStartTime() != null)
            return false;
        if (getScheduledEndTime() != null ? !getScheduledEndTime().equals(that.getScheduledEndTime()) : that.getScheduledEndTime() != null)
            return false;
        if (getScheduledLength() != null ? !getScheduledLength().equals(that.getScheduledLength()) : that.getScheduledLength() != null)
            return false;
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
        if (getPlaylistId() != null ? !getPlaylistId().equals(that.getPlaylistId()) : that.getPlaylistId() != null)
            return false;
        if (getTemplateId() != null ? !getTemplateId().equals(that.getTemplateId()) : that.getTemplateId() != null)
            return false;
        if (getParentBlockId() != null ? !getParentBlockId().equals(that.getParentBlockId()) : that.getParentBlockId() != null)
            return false;
        if (getBlocks() != null ? !getBlocks().equals(that.getBlocks()) : that.getBlocks() != null) return false;
        return getEmissions() != null ? getEmissions().equals(that.getEmissions()) : that.getEmissions() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getSeq() != null ? getSeq().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + (getStartType() != null ? getStartType().hashCode() : 0);
        result = 31 * result + (getRelativeDelay() != null ? getRelativeDelay().hashCode() : 0);
        result = 31 * result + (getScheduledStartTime() != null ? getScheduledStartTime().hashCode() : 0);
        result = 31 * result + (getScheduledEndTime() != null ? getScheduledEndTime().hashCode() : 0);
        result = 31 * result + (getScheduledLength() != null ? getScheduledLength().hashCode() : 0);
        result = 31 * result + (getStartTime() != null ? getStartTime().hashCode() : 0);
        result = 31 * result + (getEndTime() != null ? getEndTime().hashCode() : 0);
        result = 31 * result + (getLength() != null ? getLength().hashCode() : 0);
        result = 31 * result + (getDimYear() != null ? getDimYear().hashCode() : 0);
        result = 31 * result + (getDimMonth() != null ? getDimMonth().hashCode() : 0);
        result = 31 * result + (getDimDay() != null ? getDimDay().hashCode() : 0);
        result = 31 * result + (getDimHour() != null ? getDimHour().hashCode() : 0);
        result = 31 * result + (getDimMinute() != null ? getDimMinute().hashCode() : 0);
        result = 31 * result + (getDimSecond() != null ? getDimSecond().hashCode() : 0);
        result = 31 * result + (getPlaylistId() != null ? getPlaylistId().hashCode() : 0);
        result = 31 * result + (getTemplateId() != null ? getTemplateId().hashCode() : 0);
        result = 31 * result + (getParentBlockId() != null ? getParentBlockId().hashCode() : 0);
        result = 31 * result + (getBlocks() != null ? getBlocks().hashCode() : 0);
        result = 31 * result + (getEmissions() != null ? getEmissions().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SchBlockPT{");
        sb.append("id=").append(id);
        sb.append(", seq=").append(seq);
        sb.append(", name='").append(name).append('\'');
        sb.append(", type=").append(type);
        sb.append(", startType=").append(startType);
        sb.append(", relativeDelay=").append(relativeDelay);
        sb.append(", scheduledStartTime=").append(scheduledStartTime);
        sb.append(", scheduledEndTime=").append(scheduledEndTime);
        sb.append(", scheduledLength=").append(scheduledLength);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", length=").append(length);
        sb.append(", dimYear=").append(dimYear);
        sb.append(", dimMonth=").append(dimMonth);
        sb.append(", dimDay=").append(dimDay);
        sb.append(", dimHour=").append(dimHour);
        sb.append(", dimMinute=").append(dimMinute);
        sb.append(", dimSecond=").append(dimSecond);
        sb.append(", playlistId=").append(playlistId);
        sb.append(", templateId=").append(templateId);
        sb.append(", parentBlockId=").append(parentBlockId);
        sb.append(", blocks=").append(blocks);
        sb.append(", emissions=").append(emissions);
        sb.append('}');
        return sb.toString();
    }
}
