package io.protone.scheduler.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A SchEmissionTemplate.
 */
@Entity
@Table(name = "sch_emission_template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchEmissionTemplate extends SchTimeParams implements Serializable {

    private static final long serialVersionUID = 1L;

    @PodamExclude
    @ManyToOne
    private SchClockTemplate clock;

    @PodamExclude
    @ManyToOne
    private SchEventTemplate eventTemplate;

    @PodamExclude
    @ManyToOne
    private LibMediaItem mediaItem;

    @Column(name = "instance")
    private Boolean isInstance = false;

    @PodamExclude
    @OneToMany(mappedBy = "emission")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SchAttachmentTemplate> attachments = new HashSet<>();

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


    public SchEmissionTemplate seq(Long seq) {
        super.setSequence(seq);
        return this;
    }


    public Boolean isInstance() {
        return isInstance;
    }

    public void setInstance(Boolean instance) {
        isInstance = instance;
    }

    public SchClockTemplate getClock() {
        return clock;
    }

    public void setClock(SchClockTemplate clock) {
        this.clock = clock;
    }

    public SchEmissionTemplate clock(SchClockTemplate clock) {
        this.clock = clock;
        return this;
    }

    public LibMediaItem getMediaItem() {
        return mediaItem;
    }

    public void setMediaItem(LibMediaItem mediaItem) {
        this.mediaItem = mediaItem;
    }

    public SchEmissionTemplate mediaItem(LibMediaItem mediaItem) {
        this.mediaItem = mediaItem;
        return this;
    }


    public Set<SchAttachmentTemplate> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<SchAttachmentTemplate> attachments) {
        this.attachments = attachments;
    }

    public SchEmissionTemplate attachments(Set<SchAttachmentTemplate> attachments) {
        this.attachments = attachments;
        return this;
    }

    public SchEmissionTemplate addAttachment(SchAttachmentTemplate attachment) {
        this.attachments.add(attachment);
        attachment.setEmission(this);
        return this;
    }

    public SchEmissionTemplate removeAttachment(SchAttachment attachment) {
        this.attachments.remove(attachment);
        attachment.setEmission(null);
        return this;
    }


    public SchEmissionTemplate length(Long length) {
        this.length = length;
        return this;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork network) {
        this.network = network;
    }

    public SchEmissionTemplate network(CorNetwork network) {
        this.network = network;
        return this;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel channel) {
        this.channel = channel;
    }

    public SchEmissionTemplate channel(CorChannel channel) {
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
        SchEmissionTemplate emission = (SchEmissionTemplate) o;
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


    public SchEventTemplate getEventTemplate() {
        return eventTemplate;
    }

    public void setEventTemplate(SchEventTemplate eventTemplate) {
        this.eventTemplate = eventTemplate;
    }

    public SchEmissionTemplate schEventTemplate(SchEventTemplate schEventConfiguration) {
        this.eventTemplate = schEventConfiguration;
        return this;
    }
}
