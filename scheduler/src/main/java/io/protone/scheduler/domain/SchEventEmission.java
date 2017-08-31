package io.protone.scheduler.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.protone.core.domain.AbstractAuditingEntity;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.domain.LibMediaItem;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Emission.
 */
@Entity
@Table(name = "sch_event_emission")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchEventEmission extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "seq")
    private Long seq;

    @PodamExclude
    @ManyToOne
    private SchClockConfiguration clock;

    @PodamExclude
    @ManyToOne
    private LibMediaItem mediaItem;

    @Embedded
    private SchQueueParams queueParams;

    @Embedded
    private SchTimeParams timeParams;

    @PodamExclude
    @OneToMany(mappedBy = "emission")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SchEventEmissionAttachment> attachments = new HashSet<>();

    @PodamExclude
    @ManyToOne
    private SchEvent schEvent = null;

    @ManyToOne
    @PodamExclude
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

    public SchEventEmission seq(Long seq) {
        this.seq = seq;
        return this;
    }


    public SchClockConfiguration getClock() {
        return clock;
    }

    public void setClock(SchClockConfiguration clock) {
        this.clock = clock;
    }

    public SchEventEmission clock(SchClockConfiguration clock) {
        this.clock = clock;
        return this;
    }

    public LibMediaItem getMediaItem() {
        return mediaItem;
    }

    public void setMediaItem(LibMediaItem mediaItem) {
        this.mediaItem = mediaItem;
    }

    public SchEventEmission mediaItem(LibMediaItem mediaItem) {
        this.mediaItem = mediaItem;
        return this;
    }

    public SchQueueParams getQueueParams() {
        return queueParams;
    }

    public void setQueueParams(SchQueueParams queueParams) {
        this.queueParams = queueParams;
    }

    public SchEventEmission queueParams(SchQueueParams queueParams) {
        this.queueParams = queueParams;
        return this;
    }

    public SchTimeParams getTimeParams() {
        return timeParams;
    }

    public void setTimeParams(SchTimeParams timeParams) {
        this.timeParams = timeParams;
    }

    public SchEventEmission timeParams(SchTimeParams timeParams) {
        this.timeParams = timeParams;
        return this;
    }

    public Set<SchEventEmissionAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<SchEventEmissionAttachment> attachments) {
        this.attachments = attachments;
    }

    public SchEventEmission attachments(Set<SchEventEmissionAttachment> attachments) {
        this.attachments = attachments;
        return this;
    }

    public SchEventEmission addAttachment(SchEventEmissionAttachment attachment) {
        this.attachments.add(attachment);
        attachment.setEmission(this);
        return this;
    }

    public SchEventEmission removeAttachment(SchEventEmissionAttachment attachment) {
        this.attachments.remove(attachment);
        attachment.setEmission(null);
        return this;
    }

    public SchEvent getSchEvent() {
        return schEvent;
    }

    public void setSchEvent(SchEvent schEvent) {
        this.schEvent = schEvent;
    }

    public SchEventEmission event(SchEvent schEvent) {
        this.schEvent = schEvent;
        return this;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public SchEventEmission network(CorNetwork network) {
        this.network = network;
        return this;
    }

    public void setNetwork(CorNetwork network) {
        this.network = network;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public SchEventEmission channel(CorChannel channel) {
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
        SchEventEmission emission = (SchEventEmission) o;
        if (emission.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), emission.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Emission{" +
                "id=" + getId() +
                ", seq='" + getSeq() + "'" +
                "}";
    }


}
