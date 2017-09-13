package io.protone.core.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull

    @Column(name = "value", nullable = false)
    private String value;

    @ManyToOne
    private CorPropertyKey propertyKey;

    @ManyToOne
    private CorItem libItemPropertyValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public CorPropertyValue value(String value) {
        this.value = value;
        return this;
    }

    public CorPropertyKey getPropertyKey() {
        return propertyKey;
    }

    public void setPropertyKey(CorPropertyKey corPropertyKey) {
        this.propertyKey = corPropertyKey;
    }

    public CorPropertyValue propertyKey(CorPropertyKey corPropertyKey) {
        this.propertyKey = corPropertyKey;
        return this;
    }

    public CorItem getLibItemPropertyValue() {
        return libItemPropertyValue;
    }

    public void setLibItemPropertyValue(CorItem libMediaItem) {
        this.libItemPropertyValue = libMediaItem;
    }

    public CorPropertyValue libItemPropertyValue(CorItem libMediaItem) {
        this.libItemPropertyValue = libMediaItem;
        return this;
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
