package io.protone.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.protone.domain.enumeration.LIBImageSizeEnum;

/**
 * A DTO for the LIBImageObject entity.
 */
public class LIBImageObjectDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer width;

    @NotNull
    private Integer height;

    @NotNull
    private LIBImageSizeEnum imageSize;


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
    public LIBImageSizeEnum getImageSize() {
        return imageSize;
    }

    public void setImageSize(LIBImageSizeEnum imageSize) {
        this.imageSize = imageSize;
    }

    public Long getCloudObjectId() {
        return cloudObjectId;
    }

    public void setCloudObjectId(Long lIBCloudObjectId) {
        this.cloudObjectId = lIBCloudObjectId;
    }

    public Long getImageItemId() {
        return imageItemId;
    }

    public void setImageItemId(Long lIBImageItemId) {
        this.imageItemId = lIBImageItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LIBImageObjectDTO lIBImageObjectDTO = (LIBImageObjectDTO) o;

        if ( ! Objects.equals(id, lIBImageObjectDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LIBImageObjectDTO{" +
            "id=" + id +
            ", width='" + width + "'" +
            ", height='" + height + "'" +
            ", imageSize='" + imageSize + "'" +
            '}';
    }
}
