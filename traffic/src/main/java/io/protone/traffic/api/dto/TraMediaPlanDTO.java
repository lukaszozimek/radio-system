package io.protone.traffic.api.dto;


import io.protone.library.api.dto.thin.LibMediaItemThinDTO;
import io.protone.traffic.api.dto.thin.TraCustomerThinDTO;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lukaszozimek on 10/06/2017.
 */
public class TraMediaPlanDTO implements Serializable {

    private Long id;
    @NotNull
    private String name;

    @NotNull
    private LibMediaItemThinDTO mediaItemId;

    @NotNull
    private TraCustomerThinDTO traCustomerThinDTO;

    private Set<TraMediaPlanPlaylistDateDTO> traMediaPlanPlaylistDateDTOS = new HashSet<>();
    private Set<TraMediaPlanBlockDTO> traMediaPlanBlockDTOS = new HashSet<>();

    private Set<TraMediaPlanEmissionDTO> traMediaPlanEmissionDTOS = new HashSet<>();


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

    public Set<TraMediaPlanPlaylistDateDTO> getTraMediaPlanPlaylistDateDTOS() {
        return traMediaPlanPlaylistDateDTOS;
    }

    public void setTraMediaPlanPlaylistDateDTOS(Set<TraMediaPlanPlaylistDateDTO> traMediaPlanPlaylistDateDTOS) {
        this.traMediaPlanPlaylistDateDTOS = traMediaPlanPlaylistDateDTOS;
    }

    public Set<TraMediaPlanBlockDTO> getTraMediaPlanBlockDTOS() {
        return traMediaPlanBlockDTOS;
    }

    public void setTraMediaPlanBlockDTOS(Set<TraMediaPlanBlockDTO> traMediaPlanBlockDTOS) {
        this.traMediaPlanBlockDTOS = traMediaPlanBlockDTOS;
    }

    public Set<TraMediaPlanEmissionDTO> getTraMediaPlanEmissionDTOS() {
        return traMediaPlanEmissionDTOS;
    }

    public void setTraMediaPlanEmissionDTOS(Set<TraMediaPlanEmissionDTO> traMediaPlanEmissionDTOS) {
        this.traMediaPlanEmissionDTOS = traMediaPlanEmissionDTOS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TraMediaPlanDTO that = (TraMediaPlanDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (mediaItemId != null ? !mediaItemId.equals(that.mediaItemId) : that.mediaItemId != null) return false;
        if (traCustomerThinDTO != null ? !traCustomerThinDTO.equals(that.traCustomerThinDTO) : that.traCustomerThinDTO != null)
            return false;
        if (traMediaPlanPlaylistDateDTOS != null ? !traMediaPlanPlaylistDateDTOS.equals(that.traMediaPlanPlaylistDateDTOS) : that.traMediaPlanPlaylistDateDTOS != null)
            return false;
        if (traMediaPlanBlockDTOS != null ? !traMediaPlanBlockDTOS.equals(that.traMediaPlanBlockDTOS) : that.traMediaPlanBlockDTOS != null)
            return false;
        return traMediaPlanEmissionDTOS != null ? traMediaPlanEmissionDTOS.equals(that.traMediaPlanEmissionDTOS) : that.traMediaPlanEmissionDTOS == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (mediaItemId != null ? mediaItemId.hashCode() : 0);
        result = 31 * result + (traCustomerThinDTO != null ? traCustomerThinDTO.hashCode() : 0);
        result = 31 * result + (traMediaPlanPlaylistDateDTOS != null ? traMediaPlanPlaylistDateDTOS.hashCode() : 0);
        result = 31 * result + (traMediaPlanBlockDTOS != null ? traMediaPlanBlockDTOS.hashCode() : 0);
        result = 31 * result + (traMediaPlanEmissionDTOS != null ? traMediaPlanEmissionDTOS.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TraMediaPla{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mediaItemId=" + mediaItemId +
                ", traCustomerThinDTO=" + traCustomerThinDTO +
                ", traMediaPlanPlaylistDateDTOS=" + traMediaPlanPlaylistDateDTOS +
                ", traMediaPlanBlockDTOS=" + traMediaPlanBlockDTOS +
                ", traMediaPlanEmissionDTOS=" + traMediaPlanEmissionDTOS +
                '}';
    }
}
