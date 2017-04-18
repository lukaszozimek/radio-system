package io.protone.custom.service.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.protone.custom.service.dto.thin.SchEventThinPT;
import io.protone.domain.enumeration.SchStartTypeEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A DTO for the SchTemplate entity.
 */
public class SchEventPT implements Serializable {

    private Long id;

    @NotNull
    private Integer seq;

    @NotNull
    @Size(max = 100)
    private String name;
///Stop Time do zastanowienia się
    private SchStartTypeEnum startType;

    private SchEventThinPT.SchEventTypeEnum schEventType;

    private Long relativeDelay;

    private LocalDateTime scheduledStartTime;

    private LocalDateTime scheduledEndTime;

    private Long scheduledLength = 0L;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Long parentBlockId;

    private List<SchBlockPT> blocks = new ArrayList<>();

    private List<SchEmissionPT> emissions = new ArrayList<>();

    public enum SchEventTypeEnum {
        MUSIC_IMPORT("ET_MUSIC_IMPORT"),

        COMMERCIAL_IMPORT("ET_COMMERCIAL_IMPORT"),

        OTHER("ET_OTHER");

        private String value;

        SchEventTypeEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static SchEventPT.SchEventTypeEnum fromValue(String text) {
            for (SchEventPT.SchEventTypeEnum b : SchEventPT.SchEventTypeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

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

    public LocalDateTime getScheduledStartTime() {
        return scheduledStartTime;
    }

    public void setScheduledStartTime(LocalDateTime scheduledStartTime) {
        this.scheduledStartTime = scheduledStartTime;
    }

    public LocalDateTime getScheduledEndTime() {
        return scheduledEndTime;
    }

    public void setScheduledEndTime(LocalDateTime scheduledEndTime) {
        this.scheduledEndTime = scheduledEndTime;
    }

    public Long getScheduledLength() {
        return scheduledLength;
    }

    public void setScheduledLength(Long scheduledLength) {
        this.scheduledLength = scheduledLength;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }



    public void setLength(Long length) {
        this.scheduledLength = length;
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

    public SchEventPT id(Long id) {
        this.id = id;
        return this;
    }

    public SchEventPT seq(Integer seq) {
        this.seq = seq;
        return this;
    }

    public SchEventPT name(String name) {
        this.name = name;
        return this;
    }

    public SchEventPT startType(SchStartTypeEnum startType) {
        this.startType = startType;
        return this;
    }

    public SchEventPT relativeDelay(Long relativeDelay) {
        this.relativeDelay = relativeDelay;
        return this;
    }

    public SchEventPT scheduledStartTime(LocalDateTime scheduledStartTime) {
        this.scheduledStartTime = scheduledStartTime;
        return this;
    }

    public SchEventPT scheduledEndTime(LocalDateTime scheduledEndTime) {
        this.scheduledEndTime = scheduledEndTime;
        return this;
    }

    public SchEventPT scheduledLength(Long scheduledLength) {
        this.scheduledLength = scheduledLength;
        return this;
    }

    public SchEventPT startTime(LocalDateTime startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public SchEventPT endTime(LocalDateTime endTime) {
        this.endTime = endTime;
        return this;
    }




    public SchEventPT parentBlockId(Long parentBlockId) {
        this.parentBlockId = parentBlockId;
        return this;
    }

    public SchEventPT blocks(List<SchBlockPT> blocks) {
        this.blocks = blocks;
        Long totalLength = 0L;
        for (SchBlockPT block : blocks)
        for (SchEmissionPT emission : this.getEmissions())
            totalLength += emission.getMediaItem().getLength();
        return this;
    }

    public SchEventPT addBlock(SchBlockPT block) {
        this.blocks.add(block);
        return this;
    }

    public SchEventPT emissions(List<SchEmissionPT> emissions) {
        this.emissions = emissions;
        Long totalLength = 0L;
        for (SchBlockPT block : this.getBlocks())
        for (SchEmissionPT emission : emissions)
            totalLength += emission.getMediaItem().getLength();
        return this;
    }

    public SchEventPT addEmission(SchEmissionPT emission) {
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
        result = 31 * result + (getStartType() != null ? getStartType().hashCode() : 0);
        result = 31 * result + (getRelativeDelay() != null ? getRelativeDelay().hashCode() : 0);
        result = 31 * result + (getScheduledStartTime() != null ? getScheduledStartTime().hashCode() : 0);
        result = 31 * result + (getScheduledEndTime() != null ? getScheduledEndTime().hashCode() : 0);
        result = 31 * result + (getScheduledLength() != null ? getScheduledLength().hashCode() : 0);
        result = 31 * result + (getStartTime() != null ? getStartTime().hashCode() : 0);
        result = 31 * result + (getEndTime() != null ? getEndTime().hashCode() : 0);
        result = 31 * result + (getParentBlockId() != null ? getParentBlockId().hashCode() : 0);
        result = 31 * result + (getBlocks() != null ? getBlocks().hashCode() : 0);
        result = 31 * result + (getEmissions() != null ? getEmissions().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SchEventPT{");
        sb.append("id=").append(id);
        sb.append(", seq=").append(seq);
        sb.append(", name='").append(name).append('\'');
        sb.append(", startType=").append(startType);
        sb.append(", relativeDelay=").append(relativeDelay);
        sb.append(", scheduledStartTime=").append(scheduledStartTime);
        sb.append(", scheduledEndTime=").append(scheduledEndTime);
        sb.append(", scheduledLength=").append(scheduledLength);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", parentBlockId=").append(parentBlockId);
        sb.append(", blocks=").append(blocks);
        sb.append(", emissions=").append(emissions);
        sb.append('}');
        return sb.toString();
    }

    public SchEventThinPT.SchEventTypeEnum getSchEventType() {
        return schEventType;
    }

    public void setSchEventType(SchEventThinPT.SchEventTypeEnum schEventType) {
        this.schEventType = schEventType;
    }

}
