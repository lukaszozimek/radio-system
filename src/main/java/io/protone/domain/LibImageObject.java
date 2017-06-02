package io.protone.domain;

import io.protone.domain.enumeration.LibImageSizeEnum;
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
public class LibImageObject implements Serializable {

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
    @Column(name = "image_size", nullable = false)
    private LibImageSizeEnum imageSize;

    @OneToOne
    @JoinColumn(unique = true)
    private LibCloudObject cloudObject;

    @ManyToOne
    private CorNetwork network;

    @ManyToOne
    private LibImageItem imageItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWidth() {
        return width;
    }

    public LibImageObject width(Integer width) {
        this.width = width;
        return this;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public LibImageObject height(Integer height) {
        this.height = height;
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public LibImageSizeEnum getImageSize() {
        return imageSize;
    }

    public LibImageObject imageSize(LibImageSizeEnum imageSize) {
        this.imageSize = imageSize;
        return this;
    }

    public void setImageSize(LibImageSizeEnum imageSize) {
        this.imageSize = imageSize;
    }

    public LibCloudObject getCloudObject() {
        return cloudObject;
    }

    public LibImageObject cloudObject(LibCloudObject libCloudObject) {
        this.cloudObject = libCloudObject;
        return this;
    }

    public void setCloudObject(LibCloudObject libCloudObject) {
        this.cloudObject = libCloudObject;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public LibImageObject network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public LibImageItem getImageItem() {
        return imageItem;
    }

    public LibImageObject imageItem(LibImageItem libImageItem) {
        this.imageItem = libImageItem;
        return this;
    }

    public void setImageItem(LibImageItem libImageItem) {
        this.imageItem = libImageItem;
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
