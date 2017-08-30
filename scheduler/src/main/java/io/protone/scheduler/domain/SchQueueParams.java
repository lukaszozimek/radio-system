package io.protone.scheduler.domain;

import io.protone.scheduler.domain.enumeration.ObjectTypeEnum;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

/**
 * A QueueParams.
 */
@Embeddable
public class SchQueueParams implements Serializable {


    @Column(name = "previous_id")
    private Long previousId;

    @Column(name = "sequence")
    private Long seq;

    @Enumerated(EnumType.STRING)
    @Column(name = "previous_type")
    private ObjectTypeEnum previousType;

    @Column(name = "next_id")
    private Long nextId;

    @Enumerated(EnumType.STRING)
    @Column(name = "next_type")
    private ObjectTypeEnum nextType;



    public Long getPreviousId() {
        return previousId;
    }

    public void setPreviousId(Long previousId) {
        this.previousId = previousId;
    }

    public SchQueueParams previousId(Long previousId) {
        this.previousId = previousId;
        return this;
    }

    public ObjectTypeEnum getPreviousType() {
        return previousType;
    }

    public void setPreviousType(ObjectTypeEnum previousType) {
        this.previousType = previousType;
    }

    public SchQueueParams previousType(ObjectTypeEnum previousType) {
        this.previousType = previousType;
        return this;
    }

    public Long getNextId() {
        return nextId;
    }

    public void setNextId(Long nextId) {
        this.nextId = nextId;
    }

    public SchQueueParams nextId(Long nextId) {
        this.nextId = nextId;
        return this;
    }

    public ObjectTypeEnum getNextType() {
        return nextType;
    }

    public void setNextType(ObjectTypeEnum nextType) {
        this.nextType = nextType;
    }

    public SchQueueParams nextType(ObjectTypeEnum nextType) {
        this.nextType = nextType;
        return this;
    }


    @Override
    public String toString() {
        return "QueueParams{" +
                ", previousId='" + getPreviousId() + "'" +
                ", previousType='" + getPreviousType() + "'" +
                ", nextId='" + getNextId() + "'" +
                ", nextType='" + getNextType() + "'" +
                "}";
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }
}
