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
 * A Emission.
 */
@Entity
@Table(name = "sch_emission_configuration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchEmissionConfiguration extends SchConfigurationTimeParams implements Serializable {

    private static final long serialVersionUID = 1L;


    @PodamExclude
    @ManyToOne
    private SchClockConfiguration clock;

    @PodamExclude
    @ManyToOne
    private LibMediaItem mediaItem;

    @Embedded
    private SchConfigurationTimeParams timeParams;

    @PodamExclude
    @OneToMany(mappedBy = "emission")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SchAttachmentConfiguration> attachments = new HashSet<>();

    @PodamExclude
    @ManyToOne
    private SchEventConfiguration schEventConfiguration = null;

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


    public SchEmissionConfiguration seq(Long seq) {
        super.setSequence(seq);
        return this;
    }


    public SchClockConfiguration getClock() {
        return clock;
    }

    public void setClock(SchClockConfiguration clock) {
        this.clock = clock;
    }

    public SchEmissionConfiguration clock(SchClockConfiguration clock) {
        this.clock = clock;
        return this;
    }

    public LibMediaItem getMediaItem() {
        return mediaItem;
    }

    public void setMediaItem(LibMediaItem mediaItem) {
        this.mediaItem = mediaItem;
    }

    public SchEmissionConfiguration mediaItem(LibMediaItem mediaItem) {
        this.mediaItem = mediaItem;
        return this;
    }

    public SchConfigurationTimeParams getTimeParams() {
        return timeParams;
    }

    public void setTimeParams(SchConfigurationTimeParams timeParams) {
        this.timeParams = timeParams;
    }

    public SchEmissionConfiguration timeParams(SchConfigurationTimeParams timeParams) {
        this.timeParams = timeParams;
        return this;
    }

    public Set<SchAttachmentConfiguration> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<SchAttachmentConfiguration> attachments) {
        this.attachments = attachments;
    }

    public SchEmissionConfiguration attachments(Set<SchAttachmentConfiguration> attachments) {
        this.attachments = attachments;
        return this;
    }

    public SchEmissionConfiguration addAttachment(SchAttachmentConfiguration attachment) {
        this.attachments.add(attachment);
        attachment.setEmission(this);
        return this;
    }

    public SchEmissionConfiguration removeAttachment(SchAttachment attachment) {
        this.attachments.remove(attachment);
        attachment.setEmission(null);
        return this;
    }

    public SchEventConfiguration getSchEventConfiguration() {
        return schEventConfiguration;
    }

    public void setSchEventConfiguration(SchEventConfiguration schEventConfiguration) {
        this.schEventConfiguration = schEventConfiguration;
    }

    public SchEmissionConfiguration schEventConfiguration(SchEventConfiguration schEventConfiguration) {
        this.schEventConfiguration = schEventConfiguration;
        return this;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork network) {
        this.network = network;
    }

    public SchEmissionConfiguration network(CorNetwork network) {
        this.network = network;
        return this;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel channel) {
        this.channel = channel;
    }

    public SchEmissionConfiguration channel(CorChannel channel) {
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
        SchEmissionConfiguration emission = (SchEmissionConfiguration) o;
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
