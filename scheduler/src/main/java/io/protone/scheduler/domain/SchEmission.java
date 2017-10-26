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
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Emission.
 */
@Entity
@Table(name = "sch_emission")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchEmission extends SchTimeParams implements Serializable {

    private static final long serialVersionUID = 1L;

    @PodamExclude
    @ManyToOne
    private SchPlaylist playlist;

    @PodamExclude
    @ManyToOne
    private SchClock clock;

    @PodamExclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    private LibMediaItem mediaItem;

    @Column(name = "finished")
    private boolean finished;

    @Column(name = "wasPlayed")
    private boolean wasPlayed;

    @Column(name = "isPlaying")
    private boolean isPlaying;

    @Column(name = "isRemoved")
    private boolean isRemoved;

    @Column(name = "wasSkiped")
    private boolean wasSkiped;

    @PodamExclude
    @OneToMany(mappedBy = "emission", cascade = CascadeType.MERGE)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SchAttachment> attachments = new HashSet<>();

    @PodamExclude
    @ManyToOne(cascade = CascadeType.ALL)
    private SchBlock block = null;

    @ManyToOne
    @PodamExclude
    private CorNetwork network;

    @ManyToOne
    @PodamExclude
    private CorChannel channel;

    @Transient
    @JsonIgnore
    private String libraryElementShortCut;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SchEmission id(Long id) {
        this.id = id;
        return this;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public SchEmission seq(Long seq) {
        this.sequence = seq;
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

    public SchEmission length(Long length) {
        this.length = length;
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
        return block;
    }

    public void setBlock(SchBlock block) {
        this.block = block;
    }

    public SchEmission block(SchBlock block) {
        this.block = block;
        return this;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork network) {
        this.network = network;
    }

    public SchEmission network(CorNetwork network) {
        this.network = network;
        return this;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel channel) {
        this.channel = channel;
    }

    public SchEmission channel(CorChannel channel) {
        this.channel = channel;
        return this;
    }

    public SchEmission startTime(LocalDateTime startTime) {
        super.setStartTime(startTime);
        return this;
    }

    public SchEmission endTime(LocalDateTime endTime) {
        super.setEndTime(endTime);
        return this;
    }

    public String getLibraryElementShortCut() {
        return libraryElementShortCut;
    }

    public void setLibraryElementShortCut(String libraryElementShortCut) {
        this.libraryElementShortCut = libraryElementShortCut;
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
                ", sequence='" + getSequence() + "'" +
                "}";
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isWasPlayed() {
        return wasPlayed;
    }

    public void setWasPlayed(boolean wasPlayed) {
        this.wasPlayed = wasPlayed;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public void setRemoved(boolean removed) {
        isRemoved = removed;
    }

    public boolean isWasSkiped() {
        return wasSkiped;
    }

    public void setWasSkiped(boolean wasSkiped) {
        this.wasSkiped = wasSkiped;
    }
}
