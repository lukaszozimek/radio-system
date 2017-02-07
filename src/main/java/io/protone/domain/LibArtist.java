package io.protone.domain;

import io.protone.domain.enumeration.LibArtistTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A LibArtist.
 */
@Entity
@Table(name = "lib_artist")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LibArtist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private LibArtistTypeEnum type;

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

    public LibArtist name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LibArtistTypeEnum getType() {
        return type;
    }

    public LibArtist type(LibArtistTypeEnum type) {
        this.type = type;
        return this;
    }

    public void setType(LibArtistTypeEnum type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public LibArtist description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public LibArtist network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LibArtist libArtist = (LibArtist) o;
        if (libArtist.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, libArtist.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LibArtist{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", type='" + type + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
