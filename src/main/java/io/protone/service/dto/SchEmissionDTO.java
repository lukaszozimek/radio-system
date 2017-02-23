package io.protone.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the SchEmission entity.
 */
public class SchEmissionDTO implements Serializable {

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

    private Long mediaItemId;

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

    public Long getMediaItemId() {
        return mediaItemId;
    }

    public void setMediaItemId(Long libMediaItemId) {
        this.mediaItemId = libMediaItemId;
    }

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long schBlockId) {
        this.blockId = schBlockId;
    }

    public Long getCampaingsId() {
        return campaingsId;
    }

    public void setCampaingsId(Long traCampaignId) {
        this.campaingsId = traCampaignId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SchEmissionDTO schEmissionDTO = (SchEmissionDTO) o;

        if ( ! Objects.equals(id, schEmissionDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SchEmissionDTO{" +
            "id=" + id +
            ", seq='" + seq + "'" +
            ", name='" + name + "'" +
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
