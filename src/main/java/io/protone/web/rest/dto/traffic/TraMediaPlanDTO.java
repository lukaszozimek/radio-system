package io.protone.web.rest.dto.traffic;

import io.protone.web.rest.dto.traffic.thin.TraCustomerThinDTO;

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
    private Long mediaItemId;

    @NotNull
    private TraCustomerThinDTO traCustomerThinDTO;

    public Set<TraMediaPlanPlaylistDTO> getMediaPlanPlaylistDTOS() {
        return mediaPlanPlaylistDTOS;
    }

    public void setMediaPlanPlaylistDTOS(Set<TraMediaPlanPlaylistDTO> mediaPlanPlaylistDTOS) {
        this.mediaPlanPlaylistDTOS = mediaPlanPlaylistDTOS;
    }

    private Set<TraMediaPlanPlaylistDTO> mediaPlanPlaylistDTOS = new HashSet<>();

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

    public Long getMediaItemId() {
        return mediaItemId;
    }

    public void setMediaItemId(Long mediaItemId) {
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

        TraMediaPlanDTO that = (TraMediaPlanDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (mediaItemId != null ? !mediaItemId.equals(that.mediaItemId) : that.mediaItemId != null) return false;
        if (traCustomerThinDTO != null ? !traCustomerThinDTO.equals(that.traCustomerThinDTO) : that.traCustomerThinDTO != null) return false;
        return mediaPlanPlaylistDTOS != null ? mediaPlanPlaylistDTOS.equals(that.mediaPlanPlaylistDTOS) : that.mediaPlanPlaylistDTOS == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (mediaItemId != null ? mediaItemId.hashCode() : 0);
        result = 31 * result + (traCustomerThinDTO != null ? traCustomerThinDTO.hashCode() : 0);
        result = 31 * result + (mediaPlanPlaylistDTOS != null ? mediaPlanPlaylistDTOS.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TraMediaPlanDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", mediaItemId=" + mediaItemId +
            ", traCustomerThinDTO=" + traCustomerThinDTO +
            ", playlists=" + mediaPlanPlaylistDTOS +
            '}';
    }
}
