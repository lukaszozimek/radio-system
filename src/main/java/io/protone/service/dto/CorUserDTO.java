package io.protone.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CorUser entity.
 */
public class CorUserDTO implements Serializable {

    private Long id;

    private String name;

    private Long networkId;

    private Long channelId;

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

    public Long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Long corNetworkId) {
        this.networkId = corNetworkId;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long corChannelId) {
        this.channelId = corChannelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CorUserDTO corUserDTO = (CorUserDTO) o;

        if ( ! Objects.equals(id, corUserDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CorUserDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
