package io.protone.scheduler.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A TimeParams.
 */
@MappedSuperclass
public class SchTimeParams extends SchBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "sequence")
    protected Long sequence;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

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

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }


    @Override
    public String toString() {
        return "TimeParams{" +
                ", startTime='" + getStartTime() + "'" +
                ", endTime='" + getEndTime() + "'" +
                ", relativeDelay='" + getRelativeDelay() + "'" +
                "}";
    }
}
