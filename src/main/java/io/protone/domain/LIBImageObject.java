package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import io.protone.domain.enumeration.LIBImageSizeEnum;

/**
 * A LIBImageObject.
 */
@Entity
@Table(name = "lib_image_object")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LIBImageObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "width", nullable = false)
    private Integer width;

    @NotNull
    @Column(name = "height", nullable = false)
    private Integer height;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "image_size", nullable = false)
    private LIBImageSizeEnum imageSize;

    @OneToOne
    @JoinColumn(unique = true)
    private LIBCloudObject cloudObject;

    @ManyToOne
    private LIBImageItem imageItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWidth() {
        return width;
    }

    public LIBImageObject width(Integer width) {
        this.width = width;
        return this;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public LIBImageObject height(Integer height) {
        this.height = height;
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public LIBImageSizeEnum getImageSize() {
        return imageSize;
    }

    public LIBImageObject imageSize(LIBImageSizeEnum imageSize) {
        this.imageSize = imageSize;
        return this;
    }

    public void setImageSize(LIBImageSizeEnum imageSize) {
        this.imageSize = imageSize;
    }

    public LIBCloudObject getCloudObject() {
        return cloudObject;
    }

    public LIBImageObject cloudObject(LIBCloudObject lIBCloudObject) {
        this.cloudObject = lIBCloudObject;
        return this;
    }

    public void setCloudObject(LIBCloudObject lIBCloudObject) {
        this.cloudObject = lIBCloudObject;
    }

    public LIBImageItem getImageItem() {
        return imageItem;
    }

    public LIBImageObject imageItem(LIBImageItem lIBImageItem) {
        this.imageItem = lIBImageItem;
        return this;
    }

    public void setImageItem(LIBImageItem lIBImageItem) {
        this.imageItem = lIBImageItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LIBImageObject lIBImageObject = (LIBImageObject) o;
        if (lIBImageObject.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, lIBImageObject.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LIBImageObject{" +
            "id=" + id +
            ", width='" + width + "'" +
            ", height='" + height + "'" +
            ", imageSize='" + imageSize + "'" +
            '}';
    }
}
