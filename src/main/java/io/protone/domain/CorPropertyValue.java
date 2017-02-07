package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CorPropertyValue.
 */
@Entity
@Table(name = "cor_property_value")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CorPropertyValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "value", length = 100, nullable = false)
    private String value;

    @ManyToOne
    private CorPropertyKey propertyKey;

    @ManyToOne
    private LibMediaItem value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public CorPropertyValue value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public CorPropertyKey getPropertyKey() {
        return propertyKey;
    }

    public CorPropertyValue propertyKey(CorPropertyKey corPropertyKey) {
        this.propertyKey = corPropertyKey;
        return this;
    }

    public void setPropertyKey(CorPropertyKey corPropertyKey) {
        this.propertyKey = corPropertyKey;
    }

    public LibMediaItem getValue() {
        return value;
    }

    public CorPropertyValue value(LibMediaItem libMediaItem) {
        this.value = libMediaItem;
        return this;
    }

    public void setValue(LibMediaItem libMediaItem) {
        this.value = libMediaItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CorPropertyValue corPropertyValue = (CorPropertyValue) o;
        if (corPropertyValue.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, corPropertyValue.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CorPropertyValue{" +
            "id=" + id +
            ", value='" + value + "'" +
            '}';
    }
}
