package io.protone.scheduler.domain;

import io.protone.core.domain.AbstractAuditingEntity;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.domain.enumeration.ObjectTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A QueueParams.
 */
@Entity
@Table(name = "sch_queue_params")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchQueueParams  extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "seq")
    private Long seq;

    @Column(name = "previous_id")
    private Long previousId;

    @Enumerated(EnumType.STRING)
    @Column(name = "previous_type")
    private ObjectTypeEnum previousType;

    @Column(name = "next_id")
    private Long nextId;

    @Enumerated(EnumType.STRING)
    @Column(name = "next_type")
    private ObjectTypeEnum nextType;

    @ManyToOne
    private CorNetwork network;

    @ManyToOne
    @PodamExclude
    private CorChannel channel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public SchQueueParams seq(Long seq) {
        this.seq = seq;
        return this;
    }

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
    public CorNetwork getNetwork() {
        return network;
    }

    public SchQueueParams network(CorNetwork network) {
        this.network = network;
        return this;
    }

    public void setNetwork(CorNetwork network) {
        this.network = network;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public SchQueueParams channel(CorChannel channel) {
        this.channel = channel;
        return this;
    }

    public void setChannel(CorChannel channel) {
        this.channel = channel;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchQueueParams queueParams = (SchQueueParams) o;
        if (queueParams.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), queueParams.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QueueParams{" +
                "id=" + getId() +
                ", seq='" + getSeq() + "'" +
                ", previousId='" + getPreviousId() + "'" +
                ", previousType='" + getPreviousType() + "'" +
                ", nextId='" + getNextId() + "'" +
                ", nextType='" + getNextType() + "'" +
                "}";
    }
}
