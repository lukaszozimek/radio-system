package io.protone.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TraInvoiceStatus entity.
 */
public class TraInvoiceStatusDTO implements Serializable {

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

        TraInvoiceStatusDTO traInvoiceStatusDTO = (TraInvoiceStatusDTO) o;

        if ( ! Objects.equals(id, traInvoiceStatusDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TraInvoiceStatusDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
