package io.protone.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.protone.domain.enumeration.SCHBlockTypeEnum;
import io.protone.domain.enumeration.SCHStartTypeEnum;

/**
 * A DTO for the SCHBlock entity.
 */
public class SCHBlockDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer seq;

    @NotNull
    @Size(max = 100)
    private String name;

    @NotNull
    private SCHBlockTypeEnum type;

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
    private Integer dimYear;

    @NotNull
    private Integer dimMonth;

    @NotNull
    private Integer dimDay;

    @NotNull
    private Integer dimHour;

    @NotNull
    private Integer dimMinute;

    @NotNull
    private Integer dimSecond;


    private Long parentBlockId;
    
    private Long channelId;
    
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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public SCHBlockTypeEnum getType() {
        return type;
    }

    public void setType(SCHBlockTypeEnum type) {
        this.type = type;
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

    public Long getParentBlockId() {
        return parentBlockId;
    }

    public void setParentBlockId(Long sCHBlockId) {
        this.parentBlockId = sCHBlockId;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long cORChannelId) {
        this.channelId = cORChannelId;
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

        SCHBlockDTO sCHBlockDTO = (SCHBlockDTO) o;

        if ( ! Objects.equals(id, sCHBlockDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SCHBlockDTO{" +
            "id=" + id +
            ", seq='" + seq + "'" +
            ", name='" + name + "'" +
            ", type='" + type + "'" +
            ", startTime='" + startTime + "'" +
            ", startType='" + startType + "'" +
            ", relativeDelay='" + relativeDelay + "'" +
            ", endTime='" + endTime + "'" +
            ", length='" + length + "'" +
            ", dimYear='" + dimYear + "'" +
            ", dimMonth='" + dimMonth + "'" +
            ", dimDay='" + dimDay + "'" +
            ", dimHour='" + dimHour + "'" +
            ", dimMinute='" + dimMinute + "'" +
            ", dimSecond='" + dimSecond + "'" +
            '}';
    }
}
