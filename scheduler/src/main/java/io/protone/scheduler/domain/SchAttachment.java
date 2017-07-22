package io.protone.scheduler.domain;

import io.protone.scheduler.domain.enumeration.AttachmentTypeEnum;
import io.protone.scheduler.domain.enumeration.FadeTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Attachment.
 */
@Entity
@Table(name = "sch_attachment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchAttachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "attachement_type")
    private AttachmentTypeEnum attachementType;

    @Column(name = "fade_start")
    private Long fadeStart;

    @Column(name = "fade_in_length")
    private Long fadeInLength;

    @Column(name = "volume_level")
    private Long volumeLevel;

    @Column(name = "fade_out_length")
    private Long fadeOutLength;

    @Enumerated(EnumType.STRING)
    @Column(name = "fade_type")
    private FadeTypeEnum fadeType;

    @OneToOne
    @JoinColumn(unique = true)
    private SchMediaItem mediaItem;

    @ManyToOne
    private SchEmission emission;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AttachmentTypeEnum getAttachementType() {
        return attachementType;
    }

    public SchAttachment attachementType(AttachmentTypeEnum attachementType) {
        this.attachementType = attachementType;
        return this;
    }

    public void setAttachementType(AttachmentTypeEnum attachementType) {
        this.attachementType = attachementType;
    }

    public Long getFadeStart() {
        return fadeStart;
    }

    public SchAttachment fadeStart(Long fadeStart) {
        this.fadeStart = fadeStart;
        return this;
    }

    public void setFadeStart(Long fadeStart) {
        this.fadeStart = fadeStart;
    }

    public Long getFadeInLength() {
        return fadeInLength;
    }

    public SchAttachment fadeInLength(Long fadeInLength) {
        this.fadeInLength = fadeInLength;
        return this;
    }

    public void setFadeInLength(Long fadeInLength) {
        this.fadeInLength = fadeInLength;
    }

    public Long getVolumeLevel() {
        return volumeLevel;
    }

    public SchAttachment volumeLevel(Long volumeLevel) {
        this.volumeLevel = volumeLevel;
        return this;
    }

    public void setVolumeLevel(Long volumeLevel) {
        this.volumeLevel = volumeLevel;
    }

    public Long getFadeOutLength() {
        return fadeOutLength;
    }

    public SchAttachment fadeOutLength(Long fadeOutLength) {
        this.fadeOutLength = fadeOutLength;
        return this;
    }

    public void setFadeOutLength(Long fadeOutLength) {
        this.fadeOutLength = fadeOutLength;
    }

    public FadeTypeEnum getFadeType() {
        return fadeType;
    }

    public SchAttachment fadeType(FadeTypeEnum fadeType) {
        this.fadeType = fadeType;
        return this;
    }

    public void setFadeType(FadeTypeEnum fadeType) {
        this.fadeType = fadeType;
    }

    public SchMediaItem getMediaItem() {
        return mediaItem;
    }

    public SchAttachment mediaItem(SchMediaItem mediaItem) {
        this.mediaItem = mediaItem;
        return this;
    }

    public void setMediaItem(SchMediaItem mediaItem) {
        this.mediaItem = mediaItem;
    }

    public SchEmission getEmission() {
        return emission;
    }

    public SchAttachment emission(SchEmission emission) {
        this.emission = emission;
        return this;
    }

    public void setEmission(SchEmission emission) {
        this.emission = emission;
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
            ", attachementType='" + getAttachementType() + "'" +
            ", fadeStart='" + getFadeStart() + "'" +
            ", fadeInLength='" + getFadeInLength() + "'" +
            ", volumeLevel='" + getVolumeLevel() + "'" +
            ", fadeOutLength='" + getFadeOutLength() + "'" +
            ", fadeType='" + getFadeType() + "'" +
            "}";
    }
}
