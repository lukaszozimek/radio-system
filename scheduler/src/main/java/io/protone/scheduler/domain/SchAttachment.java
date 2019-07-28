package io.protone.scheduler.domain;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.domain.LibMediaItem;
import io.protone.scheduler.domain.enumeration.AttachmentTypeEnum;
import io.protone.scheduler.domain.enumeration.FadeTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamLongValue;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import static io.protone.scheduler.domain.SchDiscriminators.ATTACHMENT;

/**
 * A Attachment.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(ATTACHMENT)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchAttachment extends SchEmission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "sequence")
    protected Long sequence;

    @Enumerated(EnumType.STRING)
    @Column(name = "attachment_type")
    private AttachmentTypeEnum attachmentType;

    @Column(name = "fade_start")
    private Long fadeStart;

    @PodamLongValue
    @Column(name = "fade_in_length")
    private Long fadeInLength;

    @Column(name = "volume_level")
    private Long volumeLevel;

    @Column(name = "fade_out_length")
    private Long fadeOutLength;

    @Enumerated(EnumType.STRING)
    @Column(name = "fade_type")
    private FadeTypeEnum fadeType;

    @PodamExclude
    @ManyToOne
    private LibMediaItem mediaItem;

    @PodamExclude
    @ManyToOne
    private SchEmission emission;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AttachmentTypeEnum getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(AttachmentTypeEnum attachmentType) {
        this.attachmentType = attachmentType;
    }

    public SchAttachment attachmentType(AttachmentTypeEnum attachementType) {
        this.attachmentType = attachementType;
        return this;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public Long getFadeStart() {
        return fadeStart;
    }

    public void setFadeStart(Long fadeStart) {
        this.fadeStart = fadeStart;
    }

    public SchAttachment fadeStart(Long fadeStart) {
        this.fadeStart = fadeStart;
        return this;
    }

    public Long getFadeInLength() {
        return fadeInLength;
    }

    public void setFadeInLength(Long fadeInLength) {
        this.fadeInLength = fadeInLength;
    }

    public SchAttachment fadeInLength(Long fadeInLength) {
        this.fadeInLength = fadeInLength;
        return this;
    }

    public Long getVolumeLevel() {
        return volumeLevel;
    }

    public void setVolumeLevel(Long volumeLevel) {
        this.volumeLevel = volumeLevel;
    }

    public SchAttachment volumeLevel(Long volumeLevel) {
        this.volumeLevel = volumeLevel;
        return this;
    }

    public Long getFadeOutLength() {
        return fadeOutLength;
    }

    public void setFadeOutLength(Long fadeOutLength) {
        this.fadeOutLength = fadeOutLength;
    }

    public SchAttachment fadeOutLength(Long fadeOutLength) {
        this.fadeOutLength = fadeOutLength;
        return this;
    }

    public FadeTypeEnum getFadeType() {
        return fadeType;
    }

    public void setFadeType(FadeTypeEnum fadeType) {
        this.fadeType = fadeType;
    }

    public SchAttachment fadeType(FadeTypeEnum fadeType) {
        this.fadeType = fadeType;
        return this;
    }

    public LibMediaItem getMediaItem() {
        return mediaItem;
    }

    public void setMediaItem(LibMediaItem mediaItem) {
        this.mediaItem = mediaItem;
    }

    public SchAttachment mediaItem(LibMediaItem mediaItem) {
        this.mediaItem = mediaItem;
        return this;
    }

    public SchEmission getEmission() {
        return emission;
    }

    public void setEmission(SchEmission emission) {
        this.emission = emission;
    }

    public SchAttachment emission(SchEmission emission) {
        this.emission = emission;
        return this;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork network) {
        this.network = network;
    }

    public SchAttachment network(CorNetwork network) {
        this.network = network;
        return this;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel channel) {
        this.channel = channel;
    }

    public SchAttachment channel(CorChannel channel) {
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
        SchAttachment attachment = (SchAttachment) o;
        if (attachment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), attachment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "id=" + getId() +
                ", attachmentType='" + getAttachmentType() + "'" +
                ", fadeStart='" + getFadeStart() + "'" +
                ", fadeInLength='" + getFadeInLength() + "'" +
                ", volumeLevel='" + getVolumeLevel() + "'" +
                ", fadeOutLength='" + getFadeOutLength() + "'" +
                ", fadeType='" + getFadeType() + "'" +
                "}";
    }


    public SchAttachment sequence(Long sequence) {
        this.sequence = sequence;
        return this;
    }
}
