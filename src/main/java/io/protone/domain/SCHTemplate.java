package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import io.protone.domain.enumeration.SCHBlockTypeEnum;

import io.protone.domain.enumeration.SCHStartTypeEnum;

/**
 * A SCHTemplate.
 */
@Entity
@Table(name = "sch_template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SCHTemplate implements Serializable {

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
    private SCHBlockTypeEnum type;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSeq() {
        return seq;
    }

    public SCHTemplate seq(Integer seq) {
        this.seq = seq;
        return this;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getName() {
        return name;
    }

    public SCHTemplate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SCHBlockTypeEnum getType() {
        return type;
    }

    public SCHTemplate type(SCHBlockTypeEnum type) {
        this.type = type;
        return this;
    }

    public void setType(SCHBlockTypeEnum type) {
        this.type = type;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public SCHTemplate startTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public SCHStartTypeEnum getStartType() {
        return startType;
    }

    public SCHTemplate startType(SCHStartTypeEnum startType) {
        this.startType = startType;
        return this;
    }

    public void setStartType(SCHStartTypeEnum startType) {
        this.startType = startType;
    }

    public Long getRelativeDelay() {
        return relativeDelay;
    }

    public SCHTemplate relativeDelay(Long relativeDelay) {
        this.relativeDelay = relativeDelay;
        return this;
    }

    public void setRelativeDelay(Long relativeDelay) {
        this.relativeDelay = relativeDelay;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public SCHTemplate endTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getLength() {
        return length;
    }

    public SCHTemplate length(Long length) {
        this.length = length;
        return this;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SCHTemplate sCHTemplate = (SCHTemplate) o;
        if (sCHTemplate.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, sCHTemplate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SCHTemplate{" +
            "id=" + id +
            ", seq='" + seq + "'" +
            ", name='" + name + "'" +
            ", type='" + type + "'" +
            ", startTime='" + startTime + "'" +
            ", startType='" + startType + "'" +
            ", relativeDelay='" + relativeDelay + "'" +
            ", endTime='" + endTime + "'" +
            ", length='" + length + "'" +
            '}';
    }
}
