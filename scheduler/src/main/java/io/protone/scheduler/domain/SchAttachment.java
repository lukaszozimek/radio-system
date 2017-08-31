package io.protone.scheduler.domain;

import io.protone.core.domain.AbstractAuditingEntity;
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

/**
 * A Attachment.
 */
@Entity
@Table(name = "sch_attachment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchAttachment extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

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

    public SchAttachment network(CorNetwork network) {
        this.network = network;
        return this;
    }

    public void setNetwork(CorNetwork network) {
        this.network = network;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public SchAttachment channel(CorChannel channel) {
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
}
