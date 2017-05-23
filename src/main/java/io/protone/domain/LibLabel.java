package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A LibLabel.
 */
@Entity
@Table(name = "lib_label", uniqueConstraints =
@UniqueConstraint(columnNames = {"name", "network_id"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LibLabel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private CorNetwork network;

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

    public LibLabel name(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LibLabel description(String description) {
        this.description = description;
        return this;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public LibLabel network(CorNetwork corNetwork) {
        this.network = corNetwork;
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
        LibLabel libLabel = (LibLabel) o;
        if (libLabel.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, libLabel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LibLabel{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
