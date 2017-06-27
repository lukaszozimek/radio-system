package io.protone.application.web.rest.dto.traffic.thin;

import io.protone.web.rest.dto.library.thin.LibMediaItemThinDTO;

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
    private LibMediaItemThinDTO mediaItemId;

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

    public LibMediaItemThinDTO getMediaItemId() {
        return mediaItemId;
    }

    public void setMediaItemId(LibMediaItemThinDTO mediaItemId) {
        this.mediaItemId = mediaItemId;
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
        if (mediaItemId != null ? !mediaItemId.equals(that.mediaItemId) : that.mediaItemId != null) return false;
        return traCustomerThinDTO != null ? traCustomerThinDTO.equals(that.traCustomerThinDTO) : that.traCustomerThinDTO == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (mediaItemId != null ? mediaItemId.hashCode() : 0);
        result = 31 * result + (traCustomerThinDTO != null ? traCustomerThinDTO.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TraMediaPlanThinDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", mediaItemId=" + mediaItemId +
            ", traCustomerThinDTO=" + traCustomerThinDTO +
            '}';
    }
}
