package io.protone.traffic.api.dto;


import com.google.common.base.Objects;
import io.protone.library.api.dto.thin.LibMediaItemThinDTO;
import io.protone.traffic.api.dto.thin.TraOrderThinDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by lukaszozimek on 10/06/2017.
 */
public class TraEmissionLogDescriptorDTO implements Serializable {

    @NotNull
    @Valid
    private TraOrderThinDTO order;

    @NotNull
    @Valid
    private LibMediaItemThinDTO libMediaItemThinDTO;

    private boolean firstPostion;

    private boolean lasPosition;

    public TraOrderThinDTO getOrder() {
        return order;
    }

    public void setOrder(TraOrderThinDTO traOrderThinDTO) {
        this.order = traOrderThinDTO;
    }

    public TraEmissionLogDescriptorDTO order(TraOrderThinDTO traOrderThinDTO) {
        this.order = traOrderThinDTO;
        return this;
    }


    public LibMediaItemThinDTO getLibMediaItemThinDTO() {
        return libMediaItemThinDTO;
    }

    public void setLibMediaItemThinDTO(LibMediaItemThinDTO libMediaItemThinDTO) {
        this.libMediaItemThinDTO = libMediaItemThinDTO;
    }

    public TraEmissionLogDescriptorDTO libMediaItemThinDTO(LibMediaItemThinDTO libMediaItemThinDTO) {
        this.libMediaItemThinDTO = libMediaItemThinDTO;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TraEmissionLogDescriptorDTO)) return false;
        TraEmissionLogDescriptorDTO that = (TraEmissionLogDescriptorDTO) o;
        return Objects.equal(getOrder(), that.getOrder()) &&
                Objects.equal(getLibMediaItemThinDTO(), that.getLibMediaItemThinDTO());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getOrder(), getLibMediaItemThinDTO());
    }

    @Override
    public String toString() {
        return "TraEmissionLogDescriptorDTO{" +
                "order=" + order +
                ", libMediaItemThinDTO=" + libMediaItemThinDTO +
                '}';
    }

    public boolean isFirstPostion() {
        return firstPostion;
    }

    public void setFirstPostion(boolean firstPostion) {
        this.firstPostion = firstPostion;
    }

    public boolean isLasPosition() {
        return lasPosition;
    }

    public void setLasPosition(boolean lasPosition) {
        this.lasPosition = lasPosition;
    }
}
