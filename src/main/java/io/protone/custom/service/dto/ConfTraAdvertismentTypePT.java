package io.protone.custom.service.dto;

import io.protone.service.dto.TraAdvertismentTypeDTO;

import java.util.Objects;

/**
 * Created by lukaszozimek on 23/03/2017.
 */
public class ConfTraAdvertismentTypePT {

    private Long id;

    private String name;

    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConfTraAdvertismentTypePT traAdvertismentTypeDTO = (ConfTraAdvertismentTypePT) o;

        if (!Objects.equals(id, traAdvertismentTypeDTO.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TraAdvertismentTypeDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
