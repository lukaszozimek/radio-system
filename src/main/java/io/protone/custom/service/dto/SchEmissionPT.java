package io.protone.custom.service.dto;


import io.protone.custom.service.dto.thin.SchLibItemThinPT;

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

    private ZonedDateTime startTime;

    private ZonedDateTime endTime;

    private ZonedDateTime scheduledStartTime;

    private ZonedDateTime scheduledEndTime;

    private SchLibItemThinPT mediaItem;

    private Long blockId;

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

    public SchLibItemThinPT getMediaItem() {
        return mediaItem;
    }

    public void setMediaItem(SchLibItemThinPT mediaItem) {
        this.mediaItem = mediaItem;
    }

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
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

    public SchEmissionPT id(Long id) {
        this.id = id;
        return this;
    }

    public SchEmissionPT seq(Integer seq) {
        this.seq = seq;
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


    public SchEmissionPT mediaItem(SchLibItemThinPT mediaItem) {
        this.mediaItem = mediaItem;
        return this;
    }

    public SchEmissionPT blockId(Long blockId) {
        this.blockId = blockId;
        return this;
    }


    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getSeq() != null ? getSeq().hashCode() : 0);
        result = 31 * result + (getStartTime() != null ? getStartTime().hashCode() : 0);
        result = 31 * result + (getEndTime() != null ? getEndTime().hashCode() : 0);
        result = 31 * result + (getMediaItem() != null ? getMediaItem().hashCode() : 0);
        result = 31 * result + (getBlockId() != null ? getBlockId().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SchEmissionPT{");
        sb.append("id=").append(id);
        sb.append(", seq=").append(seq);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", mediaItem=").append(mediaItem);
        sb.append(", blockId=").append(blockId);
        sb.append('}');
        return sb.toString();
    }


}
