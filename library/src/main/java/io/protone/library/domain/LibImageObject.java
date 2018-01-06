package io.protone.library.domain;

import io.protone.core.domain.AbstractAuditingEntity;
import io.protone.core.domain.CorNetwork;
import io.protone.library.domain.enumeration.LibImageSizeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A LibImageObject.
 */
@Entity
@Table(name = "lib_image_object")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LibImageObject  extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "image_size")
    private LibImageSizeEnum imageSize;

    @OneToOne
    @JoinColumn(unique = true)
    private LibCloudObject cloudObject;

    @ManyToOne
    private LibMediaItem mediaItem;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public LibImageObject width(Integer width) {
        this.width = width;
        return this;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public LibImageObject height(Integer height) {
        this.height = height;
        return this;
    }

    public LibImageSizeEnum getImageSize() {
        return imageSize;
    }

    public void setImageSize(LibImageSizeEnum imageSize) {
        this.imageSize = imageSize;
    }

    public LibImageObject imageSize(LibImageSizeEnum imageSize) {
        this.imageSize = imageSize;
        return this;
    }

    public LibCloudObject getCloudObject() {
        return cloudObject;
    }

    public void setCloudObject(LibCloudObject libCloudObject) {
        this.cloudObject = libCloudObject;
    }

    public LibImageObject cloudObject(LibCloudObject libCloudObject) {
        this.cloudObject = libCloudObject;
        return this;
    }

    public LibMediaItem getMediaItem() {
        return mediaItem;
    }

    public void setMediaItem(LibMediaItem libMediaItem) {
        this.mediaItem = libMediaItem;
    }

    public LibImageObject mediaItem(LibMediaItem libMediaItem) {
        this.mediaItem = libMediaItem;
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
        LibImageObject libImageObject = (LibImageObject) o;
        if (libImageObject.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, libImageObject.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LibImageObject{" +
            "id=" + id +
            ", width='" + width + "'" +
            ", height='" + height + "'" +
            ", imageSize='" + imageSize + "'" +
            '}';
    }
}
