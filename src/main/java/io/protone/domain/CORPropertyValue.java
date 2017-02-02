package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CORPropertyValue.
 */
@Entity
@Table(name = "cor_property_value")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CORPropertyValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "value", length = 100, nullable = false)
    private String value;

    @ManyToOne
    private CORPropertyKey cORPropertyKey;

    @OneToOne
    @JoinColumn(unique = true)
    private CORNetwork network;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public CORPropertyValue value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public CORPropertyKey getCORPropertyKey() {
        return cORPropertyKey;
    }

    public CORPropertyValue cORPropertyKey(CORPropertyKey cORPropertyKey) {
        this.cORPropertyKey = cORPropertyKey;
        return this;
    }

    public void setCORPropertyKey(CORPropertyKey cORPropertyKey) {
        this.cORPropertyKey = cORPropertyKey;
    }

    public CORNetwork getNetwork() {
        return network;
    }

    public CORPropertyValue network(CORNetwork cORNetwork) {
        this.network = cORNetwork;
        return this;
    }

    public void setNetwork(CORNetwork cORNetwork) {
        this.network = cORNetwork;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CORPropertyValue cORPropertyValue = (CORPropertyValue) o;
        if (cORPropertyValue.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cORPropertyValue.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CORPropertyValue{" +
            "id=" + id +
            ", value='" + value + "'" +
            '}';
    }
}
