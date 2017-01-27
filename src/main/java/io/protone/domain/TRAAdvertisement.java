package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TRAAdvertisement.
 */
@Entity
@Table(name = "tra_advertisement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TRAAdvertisement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
    @ManyToOne
    private CORNetwork network;

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

    public TRAAdvertisement name(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TRAAdvertisement description(String description) {
        this.description = description;
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
        TRAAdvertisement tRAAdvertisement = (TRAAdvertisement) o;
        if (tRAAdvertisement.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tRAAdvertisement.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TRAAdvertisement{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            '}';
    }

    public CORNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CORNetwork network) {
        this.network = network;
    }

    public TRAAdvertisement network(CORNetwork cORNetwork) {
        this.network = cORNetwork;
        return this;
    }
}
