package io.protone.scheduler.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.domain.LibMediaItem;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
public class SchEventEmission extends SchTimeParams implements Serializable {

    private static final long serialVersionUID = 1L;


    @PodamExclude
    @ManyToOne
    private SchClockConfiguration clock;

    @PodamExclude
    @ManyToOne
    private LibMediaItem mediaItem;

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

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public SchEventEmission seq(Long seq) {
        this.sequence = seq;
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

    public SchEventEmission length(Long length) {
        this.length = length;
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

    public void setNetwork(CorNetwork network) {
        this.network = network;
    }

    public SchEventEmission network(CorNetwork network) {
        this.network = network;
        return this;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel channel) {
        this.channel = channel;
    }

    public SchEventEmission channel(CorChannel channel) {
        this.channel = channel;
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
                ", sequence='" + getSequence() + "'" +
                "}";
    }


}
