package io.protone.scheduler.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class SchEmission implements Serializable {

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
    private SchMediaItem mediaItem;

    @PodamExclude
    @OneToOne
    @JoinColumn(unique = true)
    private SchQueueParams queueParams;

    @PodamExclude
    @OneToOne
    @JoinColumn(unique = true)
    private SchTimeParams timeParams;

    @PodamExclude
    @OneToMany(mappedBy = "emission")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SchAttachment> attachments = new HashSet<>();

    @PodamExclude
    @ManyToOne
    private SchBlock block = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSeq() {
        return seq;
    }

    public SchEmission seq(Long seq) {
        this.seq = seq;
        return this;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public SchPlaylist getPlaylist() {
        return playlist;
    }

    public SchEmission playlist(SchPlaylist playlist) {
        this.playlist = playlist;
        return this;
    }

    public void setPlaylist(SchPlaylist playlist) {
        this.playlist = playlist;
    }

    public SchClock getClock() {
        return clock;
    }

    public SchEmission clock(SchClock clock) {
        this.clock = clock;
        return this;
    }

    public void setClock(SchClock clock) {
        this.clock = clock;
    }

    public SchMediaItem getMediaItem() {
        return mediaItem;
    }

    public SchEmission mediaItem(SchMediaItem mediaItem) {
        this.mediaItem = mediaItem;
        return this;
    }

    public void setMediaItem(SchMediaItem mediaItem) {
        this.mediaItem = mediaItem;
    }

    public SchQueueParams getQueueParams() {
        return queueParams;
    }

    public SchEmission queueParams(SchQueueParams queueParams) {
        this.queueParams = queueParams;
        return this;
    }

    public void setQueueParams(SchQueueParams queueParams) {
        this.queueParams = queueParams;
    }

    public SchTimeParams getTimeParams() {
        return timeParams;
    }

    public SchEmission timeParams(SchTimeParams timeParams) {
        this.timeParams = timeParams;
        return this;
    }

    public void setTimeParams(SchTimeParams timeParams) {
        this.timeParams = timeParams;
    }

    public Set<SchAttachment> getAttachments() {
        return attachments;
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

    public void setAttachments(Set<SchAttachment> attachments) {
        this.attachments = attachments;
    }

    public SchBlock getBlock() {
        return null; //block;
    }

    public SchEmission block(SchBlock block) {
        this.block = block;
        return this;
    }

    public void setBlock(SchBlock block) {
        //this.block = block;
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
