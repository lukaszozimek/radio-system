package io.protone.traffic.api.dto;


import io.protone.library.api.dto.thin.LibMediaItemThinDTO;
import io.protone.traffic.api.dto.thin.TraOrderThinDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by lukaszozimek on 10/06/2017.
 */
public class TraMediaPlanDescriptorDTO implements Serializable {

    @NotNull
    @Valid
    private TraMediaPlanTemplateDTO traMediaPlanTemplateDTO;

    @NotNull
    @Valid
    private TraOrderThinDTO order;


    @NotNull
    @Valid
    private LibMediaItemThinDTO libMediaItemThinDTO;

    public TraOrderThinDTO getOrder() {
        return order;
    }

    public void setOrder(TraOrderThinDTO traOrderThinDTO) {
        this.order = traOrderThinDTO;
    }

    public TraMediaPlanDescriptorDTO order(TraOrderThinDTO traOrderThinDTO) {
        this.order = traOrderThinDTO;
        return this;
    }


    public TraMediaPlanTemplateDTO getTraMediaPlanTemplateDTO() {
        return traMediaPlanTemplateDTO;
    }

    public void setTraMediaPlanTemplateDTO(TraMediaPlanTemplateDTO traMediaPlanTemplateDTO) {
        this.traMediaPlanTemplateDTO = traMediaPlanTemplateDTO;
    }

    public LibMediaItemThinDTO getLibMediaItemThinDTO() {
        return libMediaItemThinDTO;
    }

    public void setLibMediaItemThinDTO(LibMediaItemThinDTO libMediaItemThinDTO) {
        this.libMediaItemThinDTO = libMediaItemThinDTO;
    }
    public TraMediaPlanDescriptorDTO  libMediaItemThinDTO(LibMediaItemThinDTO libMediaItemThinDTO){
        this.libMediaItemThinDTO = libMediaItemThinDTO;
        return this;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TraMediaPlanDescriptorDTO that = (TraMediaPlanDescriptorDTO) o;

        if (traMediaPlanTemplateDTO != null ? !traMediaPlanTemplateDTO.equals(that.traMediaPlanTemplateDTO) : that.traMediaPlanTemplateDTO != null)
            return false;
        if (order != null ? !order.equals(that.order) : that.order != null) return false;
        return libMediaItemThinDTO != null ? libMediaItemThinDTO.equals(that.libMediaItemThinDTO) : that.libMediaItemThinDTO == null;
    }

    @Override
    public int hashCode() {
        int result = traMediaPlanTemplateDTO != null ? traMediaPlanTemplateDTO.hashCode() : 0;
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (libMediaItemThinDTO != null ? libMediaItemThinDTO.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TraMediaPlanDescriptorDTO{" +
                "traMediaPlanTemplateDTO=" + traMediaPlanTemplateDTO +
                ", order=" + order +
                ", libMediaItemThinDTO=" + libMediaItemThinDTO +
                '}';
    }
}
