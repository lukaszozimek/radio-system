package io.protone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CORPropertyKey.
 */
@Entity
@Table(name = "cor_property_key")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CORPropertyKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "key", length = 100, nullable = false)
    private String key;

    @OneToOne
    @JoinColumn(unique = true)
    private CORNetwork network;

    @OneToMany(mappedBy = "cORPropertyKey")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CORPropertyValue> values = new HashSet<>();

    @ManyToOne
    private LIBMediaItem lIBMediaItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public CORPropertyKey key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public CORNetwork getNetwork() {
        return network;
    }

    public CORPropertyKey network(CORNetwork cORNetwork) {
        this.network = cORNetwork;
        return this;
    }

    public void setNetwork(CORNetwork cORNetwork) {
        this.network = cORNetwork;
    }

    public Set<CORPropertyValue> getValues() {
        return values;
    }

    public CORPropertyKey values(Set<CORPropertyValue> cORPropertyValues) {
        this.values = cORPropertyValues;
        return this;
    }

    public CORPropertyKey addValues(CORPropertyValue cORPropertyValue) {
        values.add(cORPropertyValue);
        cORPropertyValue.setCORPropertyKey(this);
        return this;
    }

    public CORPropertyKey removeValues(CORPropertyValue cORPropertyValue) {
        values.remove(cORPropertyValue);
        cORPropertyValue.setCORPropertyKey(null);
        return this;
    }

    public void setValues(Set<CORPropertyValue> cORPropertyValues) {
        this.values = cORPropertyValues;
    }

    public LIBMediaItem getLIBMediaItem() {
        return lIBMediaItem;
    }

    public CORPropertyKey lIBMediaItem(LIBMediaItem lIBMediaItem) {
        this.lIBMediaItem = lIBMediaItem;
        return this;
    }

    public void setLIBMediaItem(LIBMediaItem lIBMediaItem) {
        this.lIBMediaItem = lIBMediaItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CORPropertyKey cORPropertyKey = (CORPropertyKey) o;
        if (cORPropertyKey.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cORPropertyKey.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CORPropertyKey{" +
            "id=" + id +
            ", key='" + key + "'" +
            '}';
    }
}
