package io.protone.scheduler.domain;

import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A TimeParams.
 */
@MappedSuperclass
public abstract class SchTimeParams extends SchBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Transient
    protected Long seq;

    @Column(name = "start_time")
    protected LocalDateTime startTime;

    @Column(name = "end_time")
    protected LocalDateTime endTime;

    @Column(name = "relative_delay")
    private Long relativeDelay;


    @Column(name = "length")
    protected Long length;

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public SchTimeParams startTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public SchTimeParams endTime(LocalDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public Long getRelativeDelay() {
        return relativeDelay;
    }

    public void setRelativeDelay(Long relativeDelay) {
        this.relativeDelay = relativeDelay;
    }

    public SchTimeParams relativeDelay(Long relativeDelay) {
        this.relativeDelay = relativeDelay;
        return this;
    }

    public abstract Long getSequence();

    public abstract void setSequence(Long sequence);


    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "TimeParams{" +
                ", startTime='" + getStartTime() + "'" +
                ", endTime='" + getEndTime() + "'" +
                ", relativeDelay='" + getRelativeDelay() + "'" +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchTimeParams)) return false;
        SchTimeParams that = (SchTimeParams) o;
        return Objects.equal(getSequence(), that.getSequence()) &&
                Objects.equal(getStartTime(), that.getStartTime()) &&
                Objects.equal(getEndTime(), that.getEndTime()) &&
                Objects.equal(getRelativeDelay(), that.getRelativeDelay()) &&
                Objects.equal(getLength(), that.getLength());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getSequence(), getStartTime(), getEndTime(), getRelativeDelay(), getLength());
    }
}
