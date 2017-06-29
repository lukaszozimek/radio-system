package io.protone.scheduler.domain;


import io.protone.scheduler.domain.enumeration.SchBlockTypeEnum;
import io.protone.scheduler.domain.enumeration.SchStartTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A SchBlock.
 */
@Entity
@Table(name = "sch_block")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchBlock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "seq", nullable = false)
    private Integer seq;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private SchBlockTypeEnum type;

    @Enumerated(EnumType.STRING)
    @Column(name = "start_type")
    private SchStartTypeEnum startType;

    @Column(name = "relative_delay")
    private Long relativeDelay;

    @Column(name = "scheduled_start_time")
    private ZonedDateTime scheduledStartTime;

    @Column(name = "scheduled_end_time")
    private ZonedDateTime scheduledEndTime;

    @Column(name = "scheduled_length")
    private Long scheduledLength;

    @Column(name = "start_time")
    private ZonedDateTime startTime;

    @Column(name = "end_time")
    private ZonedDateTime endTime;

    @Column(name = "length")
    private Long length;

    @Column(name = "dim_year")
    private Integer dimYear;

    @Column(name = "dim_month")
    private Integer dimMonth;

    @Column(name = "dim_day")
    private Integer dimDay;

    @Column(name = "dim_hour")
    private Integer dimHour;

    @Column(name = "dim_minute")
    private Integer dimMinute;

    @Column(name = "dim_second")
    private Integer dimSecond;

    @ManyToOne
    private SchPlaylist playlist;

    @ManyToOne
    private SchTemplate template;

    @ManyToOne
    private SchBlock parentBlock;

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

    public SchBlock seq(Integer seq) {
        this.seq = seq;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SchBlock name(String name) {
        this.name = name;
        return this;
    }

    public SchBlockTypeEnum getType() {
        return type;
    }

    public void setType(SchBlockTypeEnum type) {
        this.type = type;
    }

    public SchBlock type(SchBlockTypeEnum type) {
        this.type = type;
        return this;
    }

    public SchStartTypeEnum getStartType() {
        return startType;
    }

    public void setStartType(SchStartTypeEnum startType) {
        this.startType = startType;
    }

    public SchBlock startType(SchStartTypeEnum startType) {
        this.startType = startType;
        return this;
    }

    public Long getRelativeDelay() {
        return relativeDelay;
    }

    public void setRelativeDelay(Long relativeDelay) {
        this.relativeDelay = relativeDelay;
    }

    public SchBlock relativeDelay(Long relativeDelay) {
        this.relativeDelay = relativeDelay;
        return this;
    }

    public ZonedDateTime getScheduledStartTime() {
        return scheduledStartTime;
    }

    public void setScheduledStartTime(ZonedDateTime scheduledStartTime) {
        this.scheduledStartTime = scheduledStartTime;
    }

    public SchBlock scheduledStartTime(ZonedDateTime scheduledStartTime) {
        this.scheduledStartTime = scheduledStartTime;
        return this;
    }

    public ZonedDateTime getScheduledEndTime() {
        return scheduledEndTime;
    }

    public void setScheduledEndTime(ZonedDateTime scheduledEndTime) {
        this.scheduledEndTime = scheduledEndTime;
    }

    public SchBlock scheduledEndTime(ZonedDateTime scheduledEndTime) {
        this.scheduledEndTime = scheduledEndTime;
        return this;
    }

    public Long getScheduledLength() {
        return scheduledLength;
    }

    public void setScheduledLength(Long scheduledLength) {
        this.scheduledLength = scheduledLength;
    }

    public SchBlock scheduledLength(Long scheduledLength) {
        this.scheduledLength = scheduledLength;
        return this;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public SchBlock startTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public SchBlock endTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public SchBlock length(Long length) {
        this.length = length;
        return this;
    }

    public Integer getDimYear() {
        return dimYear;
    }

    public void setDimYear(Integer dimYear) {
        this.dimYear = dimYear;
    }

    public SchBlock dimYear(Integer dimYear) {
        this.dimYear = dimYear;
        return this;
    }

    public Integer getDimMonth() {
        return dimMonth;
    }

    public void setDimMonth(Integer dimMonth) {
        this.dimMonth = dimMonth;
    }

    public SchBlock dimMonth(Integer dimMonth) {
        this.dimMonth = dimMonth;
        return this;
    }

    public Integer getDimDay() {
        return dimDay;
    }

    public void setDimDay(Integer dimDay) {
        this.dimDay = dimDay;
    }

    public SchBlock dimDay(Integer dimDay) {
        this.dimDay = dimDay;
        return this;
    }

    public Integer getDimHour() {
        return dimHour;
    }

    public void setDimHour(Integer dimHour) {
        this.dimHour = dimHour;
    }

    public SchBlock dimHour(Integer dimHour) {
        this.dimHour = dimHour;
        return this;
    }

    public Integer getDimMinute() {
        return dimMinute;
    }

    public void setDimMinute(Integer dimMinute) {
        this.dimMinute = dimMinute;
    }

    public SchBlock dimMinute(Integer dimMinute) {
        this.dimMinute = dimMinute;
        return this;
    }

    public Integer getDimSecond() {
        return dimSecond;
    }

    public void setDimSecond(Integer dimSecond) {
        this.dimSecond = dimSecond;
    }

    public SchBlock dimSecond(Integer dimSecond) {
        this.dimSecond = dimSecond;
        return this;
    }

    public SchPlaylist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(SchPlaylist schPlaylist) {
        this.playlist = schPlaylist;
    }

    public SchBlock playlist(SchPlaylist schPlaylist) {
        this.playlist = schPlaylist;
        return this;
    }

    public SchTemplate getTemplate() {
        return template;
    }

    public void setTemplate(SchTemplate schTemplate) {
        this.template = schTemplate;
    }

    public SchBlock template(SchTemplate schTemplate) {
        this.template = schTemplate;
        return this;
    }

    public SchBlock getParentBlock() {
        return parentBlock;
    }

    public void setParentBlock(SchBlock schBlock) {
        this.parentBlock = schBlock;
    }

    public SchBlock parentBlock(SchBlock schBlock) {
        this.parentBlock = schBlock;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchBlock schBlock = (SchBlock) o;
        if (schBlock.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, schBlock.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SchBlock{" +
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
