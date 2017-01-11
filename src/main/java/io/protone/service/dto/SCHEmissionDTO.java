package io.protone.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.protone.domain.enumeration.SCHStartTypeEnum;

/**
 * A DTO for the SCHEmission entity.
 */
public class SCHEmissionDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer seq;

    @NotNull
    private ZonedDateTime startTime;

    @NotNull
    private SCHStartTypeEnum startType;

    private Long relativeDelay;

    @NotNull
    private ZonedDateTime endTime;

    @NotNull
    private Long length;

    @NotNull
    private Boolean finished;


    private Long blockId;
    
    private Long mediaItemId;
    
    private Long templateId;
    
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
    public SCHStartTypeEnum getStartType() {
        return startType;
    }

    public void setStartType(SCHStartTypeEnum startType) {
        this.startType = startType;
    }
    public Long getRelativeDelay() {
        return relativeDelay;
    }

    public void setRelativeDelay(Long relativeDelay) {
        this.relativeDelay = relativeDelay;
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
    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long sCHBlockId) {
        this.blockId = sCHBlockId;
    }

    public Long getMediaItemId() {
        return mediaItemId;
    }

    public void setMediaItemId(Long lIBMediaItemId) {
        this.mediaItemId = lIBMediaItemId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long sCHTemplateId) {
        this.templateId = sCHTemplateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SCHEmissionDTO sCHEmissionDTO = (SCHEmissionDTO) o;

        if ( ! Objects.equals(id, sCHEmissionDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SCHEmissionDTO{" +
            "id=" + id +
            ", seq='" + seq + "'" +
            ", startTime='" + startTime + "'" +
            ", startType='" + startType + "'" +
            ", relativeDelay='" + relativeDelay + "'" +
            ", endTime='" + endTime + "'" +
            ", length='" + length + "'" +
            ", finished='" + finished + "'" +
            '}';
    }
}
