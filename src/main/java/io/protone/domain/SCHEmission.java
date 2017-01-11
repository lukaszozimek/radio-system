package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import io.protone.domain.enumeration.SCHStartTypeEnum;

/**
 * A SCHEmission.
 */
@Entity
@Table(name = "sch_emission")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SCHEmission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "seq", nullable = false)
    private Integer seq;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private ZonedDateTime startTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "start_type", nullable = false)
    private SCHStartTypeEnum startType;

    @Column(name = "relative_delay")
    private Long relativeDelay;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private ZonedDateTime endTime;

    @NotNull
    @Column(name = "length", nullable = false)
    private Long length;

    @NotNull
    @Column(name = "finished", nullable = false)
    private Boolean finished;

    @ManyToOne
    private SCHBlock block;

    @ManyToOne
    private LIBMediaItem mediaItem;

    @ManyToOne
    private SCHTemplate template;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSeq() {
        return seq;
    }

    public SCHEmission seq(Integer seq) {
        this.seq = seq;
        return this;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public SCHEmission startTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public SCHStartTypeEnum getStartType() {
        return startType;
    }

    public SCHEmission startType(SCHStartTypeEnum startType) {
        this.startType = startType;
        return this;
    }

    public void setStartType(SCHStartTypeEnum startType) {
        this.startType = startType;
    }

    public Long getRelativeDelay() {
        return relativeDelay;
    }

    public SCHEmission relativeDelay(Long relativeDelay) {
        this.relativeDelay = relativeDelay;
        return this;
    }

    public void setRelativeDelay(Long relativeDelay) {
        this.relativeDelay = relativeDelay;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public SCHEmission endTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getLength() {
        return length;
    }

    public SCHEmission length(Long length) {
        this.length = length;
        return this;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public Boolean isFinished() {
        return finished;
    }

    public SCHEmission finished(Boolean finished) {
        this.finished = finished;
        return this;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public SCHBlock getBlock() {
        return block;
    }

    public SCHEmission block(SCHBlock sCHBlock) {
        this.block = sCHBlock;
        return this;
    }

    public void setBlock(SCHBlock sCHBlock) {
        this.block = sCHBlock;
    }

    public LIBMediaItem getMediaItem() {
        return mediaItem;
    }

    public SCHEmission mediaItem(LIBMediaItem lIBMediaItem) {
        this.mediaItem = lIBMediaItem;
        return this;
    }

    public void setMediaItem(LIBMediaItem lIBMediaItem) {
        this.mediaItem = lIBMediaItem;
    }

    public SCHTemplate getTemplate() {
        return template;
    }

    public SCHEmission template(SCHTemplate sCHTemplate) {
        this.template = sCHTemplate;
        return this;
    }

    public void setTemplate(SCHTemplate sCHTemplate) {
        this.template = sCHTemplate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SCHEmission sCHEmission = (SCHEmission) o;
        if (sCHEmission.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, sCHEmission.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SCHEmission{" +
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
