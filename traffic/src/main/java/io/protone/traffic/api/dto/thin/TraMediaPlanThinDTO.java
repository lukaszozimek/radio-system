package io.protone.traffic.api.dto.thin;


import io.protone.library.api.dto.LibFileItemDTO;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by lukaszozimek on 10/06/2017.
 */
public class TraMediaPlanThinDTO implements Serializable {
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private LibFileItemDTO libFileItemDTO;

    @NotNull
    private TraCustomerThinDTO traCustomerThinDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LibFileItemDTO getLibFileItemDTO() {
        return libFileItemDTO;
    }

    public void setLibFileItemDTO(LibFileItemDTO libFileItemDTO) {
        this.libFileItemDTO = libFileItemDTO;
    }

    public TraCustomerThinDTO getTraCustomerThinDTO() {
        return traCustomerThinDTO;
    }

    public void setTraCustomerThinDTO(TraCustomerThinDTO traCustomerThinDTO) {
        this.traCustomerThinDTO = traCustomerThinDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TraMediaPlanThinDTO that = (TraMediaPlanThinDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (libFileItemDTO != null ? !libFileItemDTO.equals(that.libFileItemDTO) : that.libFileItemDTO != null) return false;
        return traCustomerThinDTO != null ? traCustomerThinDTO.equals(that.traCustomerThinDTO) : that.traCustomerThinDTO == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (libFileItemDTO != null ? libFileItemDTO.hashCode() : 0);
        result = 31 * result + (traCustomerThinDTO != null ? traCustomerThinDTO.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TraMediaPlanThinDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", libFileItemDTO=" + libFileItemDTO +
            ", traCustomerThinDTO=" + traCustomerThinDTO +
            '}';
    }
}
