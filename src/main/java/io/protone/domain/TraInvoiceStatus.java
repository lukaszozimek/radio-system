package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TraInvoiceStatus.
 */
@Entity
@Table(name = "tra_invoice_status")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TraInvoiceStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
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

    public TraInvoiceStatus name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public TraInvoiceStatus description(String description) {
        this.description = description;
        return this;
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
        TraInvoiceStatus traInvoiceStatus = (TraInvoiceStatus) o;
        if (traInvoiceStatus.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, traInvoiceStatus.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TraInvoiceStatus{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
