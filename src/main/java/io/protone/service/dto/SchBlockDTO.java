package io.protone.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import io.protone.domain.enumeration.SchBlockTypeEnum;
import io.protone.domain.enumeration.SchStartTypeEnum;

/**
 * A DTO for the SchBlock entity.
 */
public class SchBlockDTO implements Serializable {

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

    public void setPlaylistId(Long schPlaylistId) {
        this.playlistId = schPlaylistId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long schTemplateId) {
        this.templateId = schTemplateId;
    }

    public Long getParentBlockId() {
        return parentBlockId;
    }

    public void setParentBlockId(Long schBlockId) {
        this.parentBlockId = schBlockId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SchBlockDTO schBlockDTO = (SchBlockDTO) o;

        if ( ! Objects.equals(id, schBlockDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SchBlockDTO{" +
            "id=" + id +
            ", seq='" + seq + "'" +
            ", name='" + name + "'" +
            ", type='" + type + "'" +
            ", startType='" + startType + "'" +
            ", relativeDelay='" + relativeDelay + "'" +
            ", scheduledStartTime='" + scheduledStartTime + "'" +
            ", scheduledEndTime='" + scheduledEndTime + "'" +
            ", scheduledLength='" + scheduledLength + "'" +
            ", startTime='" + startTime + "'" +
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
