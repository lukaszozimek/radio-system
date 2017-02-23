package io.protone.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import io.protone.domain.enumeration.LibImageSizeEnum;

/**
 * A DTO for the LibImageObject entity.
 */
public class LibImageObjectDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer width;

    @NotNull
    private Integer height;

    @NotNull
    private LibImageSizeEnum imageSize;

    private Long cloudObjectId;

    private Long imageItemId;

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
    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
    public LibImageSizeEnum getImageSize() {
        return imageSize;
    }

    public void setImageSize(LibImageSizeEnum imageSize) {
        this.imageSize = imageSize;
    }

    public Long getCloudObjectId() {
        return cloudObjectId;
    }

    public void setCloudObjectId(Long libCloudObjectId) {
        this.cloudObjectId = libCloudObjectId;
    }

    public Long getImageItemId() {
        return imageItemId;
    }

    public void setImageItemId(Long libImageItemId) {
        this.imageItemId = libImageItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LibImageObjectDTO libImageObjectDTO = (LibImageObjectDTO) o;

        if ( ! Objects.equals(id, libImageObjectDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LibImageObjectDTO{" +
            "id=" + id +
            ", width='" + width + "'" +
            ", height='" + height + "'" +
            ", imageSize='" + imageSize + "'" +
            '}';
    }
}
