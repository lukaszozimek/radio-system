package io.protone.scheduler.domain;

import io.protone.core.domain.AbstractAuditingEntity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A TimeParams.
 */
@Embeddable
public class SchTimeParams implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "start_time")
    private ZonedDateTime startTime;

    @Column(name = "end_time")
    private ZonedDateTime endTime;

    @Column(name = "relative_delay")
    private Integer relativeDelay;


    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public SchTimeParams startTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public SchTimeParams endTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public Integer getRelativeDelay() {
        return relativeDelay;
    }

    public void setRelativeDelay(Integer relativeDelay) {
        this.relativeDelay = relativeDelay;
    }

    public SchTimeParams relativeDelay(Integer relativeDelay) {
        this.relativeDelay = relativeDelay;
        return this;
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
