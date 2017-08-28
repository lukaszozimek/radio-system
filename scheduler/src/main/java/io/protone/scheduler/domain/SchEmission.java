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
@Table(name = "sch_emission")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchEmission extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "seq")
    private Long seq;

    @PodamExclude
    @ManyToOne
    private SchPlaylist playlist;

    @PodamExclude
    @ManyToOne
    private SchClock clock;

    @PodamExclude
    @OneToOne
    @JoinColumn(unique = true)
    private LibMediaItem mediaItem;

    @Embedded
    private SchQueueParams queueParams;

    @Embedded
    private SchTimeParams timeParams;

    @PodamExclude
    @OneToMany(mappedBy = "emission")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SchAttachment> attachments = new HashSet<>();

    @PodamExclude
    @ManyToOne
    private SchBlock block = null;

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

    public SchEmission seq(Long seq) {
        this.seq = seq;
        return this;
    }

    public SchPlaylist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(SchPlaylist playlist) {
        this.playlist = playlist;
    }

    public SchEmission playlist(SchPlaylist playlist) {
        this.playlist = playlist;
        return this;
    }

    public SchClock getClock() {
        return clock;
    }

    public void setClock(SchClock clock) {
        this.clock = clock;
    }

    public SchEmission clock(SchClock clock) {
        this.clock = clock;
        return this;
    }

    public LibMediaItem getMediaItem() {
        return mediaItem;
    }

    public void setMediaItem(LibMediaItem mediaItem) {
        this.mediaItem = mediaItem;
    }

    public SchEmission mediaItem(LibMediaItem mediaItem) {
        this.mediaItem = mediaItem;
        return this;
    }

    public SchQueueParams getQueueParams() {
        return queueParams;
    }

    public void setQueueParams(SchQueueParams queueParams) {
        this.queueParams = queueParams;
    }

    public SchEmission queueParams(SchQueueParams queueParams) {
        this.queueParams = queueParams;
        return this;
    }

    public SchTimeParams getTimeParams() {
        return timeParams;
    }

    public void setTimeParams(SchTimeParams timeParams) {
        this.timeParams = timeParams;
    }

    public SchEmission timeParams(SchTimeParams timeParams) {
        this.timeParams = timeParams;
        return this;
    }

    public Set<SchAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<SchAttachment> attachments) {
        this.attachments = attachments;
    }

    public SchEmission attachments(Set<SchAttachment> attachments) {
        this.attachments = attachments;
        return this;
    }

    public SchEmission addAttachment(SchAttachment attachment) {
        this.attachments.add(attachment);
        attachment.setEmission(this);
        return this;
    }

    public SchEmission removeAttachment(SchAttachment attachment) {
        this.attachments.remove(attachment);
        attachment.setEmission(null);
        return this;
    }

    public SchBlock getBlock() {
        return null; //block;
    }

    public void setBlock(SchBlock block) {
        //this.block = block;
    }

    public SchEmission block(SchBlock block) {
        this.block = block;
        return this;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public SchEmission network(CorNetwork network) {
        this.network = network;
        return this;
    }

    public void setNetwork(CorNetwork network) {
        this.network = network;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public SchEmission channel(CorChannel channel) {
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
        SchEmission emission = (SchEmission) o;
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
