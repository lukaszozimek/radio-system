package io.protone.domain;

import io.protone.domain.enumeration.SchBlockTypeEnum;
import io.protone.domain.enumeration.SchStartTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A SchPlaylist.
 */
@Entity
@Table(name = "sch_playlist")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchPlaylist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "seq", nullable = false)
    private Integer seq;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private SchBlockTypeEnum type;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private ZonedDateTime startTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "start_type", nullable = false)
    private SchStartTypeEnum startType;

    @Column(name = "relative_delay")
    private Long relativeDelay;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private ZonedDateTime endTime;

    @NotNull
    @Column(name = "length", nullable = false)
    private Long length;

    @NotNull
    @Column(name = "dim_year", nullable = false)
    private Integer dimYear;

    @NotNull
    @Column(name = "dim_month", nullable = false)
    private Integer dimMonth;

    @NotNull
    @Column(name = "dim_day", nullable = false)
    private Integer dimDay;

    @NotNull
    @Column(name = "dim_hour", nullable = false)
    private Integer dimHour;

    @NotNull
    @Column(name = "dim_minute", nullable = false)
    private Integer dimMinute;

    @NotNull
    @Column(name = "dim_second", nullable = false)
    private Integer dimSecond;

    @ManyToOne
    private CorChannel channel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSeq() {
        return seq;
    }

    public SchPlaylist seq(Integer seq) {
        this.seq = seq;
        return this;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getName() {
        return name;
    }

    public SchPlaylist name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SchBlockTypeEnum getType() {
        return type;
    }

    public SchPlaylist type(SchBlockTypeEnum type) {
        this.type = type;
        return this;
    }

    public void setType(SchBlockTypeEnum type) {
        this.type = type;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public SchPlaylist startTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public SchStartTypeEnum getStartType() {
        return startType;
    }

    public SchPlaylist startType(SchStartTypeEnum startType) {
        this.startType = startType;
        return this;
    }

    public void setStartType(SchStartTypeEnum startType) {
        this.startType = startType;
    }

    public Long getRelativeDelay() {
        return relativeDelay;
    }

    public SchPlaylist relativeDelay(Long relativeDelay) {
        this.relativeDelay = relativeDelay;
        return this;
    }

    public void setRelativeDelay(Long relativeDelay) {
        this.relativeDelay = relativeDelay;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public SchPlaylist endTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getLength() {
        return length;
    }

    public SchPlaylist length(Long length) {
        this.length = length;
        return this;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public Integer getDimYear() {
        return dimYear;
    }

    public SchPlaylist dimYear(Integer dimYear) {
        this.dimYear = dimYear;
        return this;
    }

    public void setDimYear(Integer dimYear) {
        this.dimYear = dimYear;
    }

    public Integer getDimMonth() {
        return dimMonth;
    }

    public SchPlaylist dimMonth(Integer dimMonth) {
        this.dimMonth = dimMonth;
        return this;
    }

    public void setDimMonth(Integer dimMonth) {
        this.dimMonth = dimMonth;
    }

    public Integer getDimDay() {
        return dimDay;
    }

    public SchPlaylist dimDay(Integer dimDay) {
        this.dimDay = dimDay;
        return this;
    }

    public void setDimDay(Integer dimDay) {
        this.dimDay = dimDay;
    }

    public Integer getDimHour() {
        return dimHour;
    }

    public SchPlaylist dimHour(Integer dimHour) {
        this.dimHour = dimHour;
        return this;
    }

    public void setDimHour(Integer dimHour) {
        this.dimHour = dimHour;
    }

    public Integer getDimMinute() {
        return dimMinute;
    }

    public SchPlaylist dimMinute(Integer dimMinute) {
        this.dimMinute = dimMinute;
        return this;
    }

    public void setDimMinute(Integer dimMinute) {
        this.dimMinute = dimMinute;
    }

    public Integer getDimSecond() {
        return dimSecond;
    }

    public SchPlaylist dimSecond(Integer dimSecond) {
        this.dimSecond = dimSecond;
        return this;
    }

    public void setDimSecond(Integer dimSecond) {
        this.dimSecond = dimSecond;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public SchPlaylist channel(CorChannel corChannel) {
        this.channel = corChannel;
        return this;
    }

    public void setChannel(CorChannel corChannel) {
        this.channel = corChannel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchPlaylist schPlaylist = (SchPlaylist) o;
        if (schPlaylist.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, schPlaylist.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SchPlaylist{" +
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
