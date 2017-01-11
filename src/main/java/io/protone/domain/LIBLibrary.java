package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import io.protone.domain.enumeration.LIBCounterTypeEnum;

import io.protone.domain.enumeration.LIBObjectTypeEnum;

/**
 * A LIBLibrary.
 */
@Entity
@Table(name = "lib_library")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LIBLibrary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 1)
    @Column(name = "prefix", length = 1, nullable = false)
    private String prefix;

    @NotNull
    @Column(name = "idx_length", nullable = false)
    private Integer idxLength;

    @NotNull
    @Size(max = 3)
    @Column(name = "shortcut", length = 3, nullable = false)
    private String shortcut;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotNull
    @Column(name = "counter", nullable = false)
    private Long counter;

    @Enumerated(EnumType.STRING)
    @Column(name = "counter_type")
    private LIBCounterTypeEnum counterType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "library_type", nullable = false)
    private LIBObjectTypeEnum libraryType;

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

    public String getPrefix() {
        return prefix;
    }

    public LIBLibrary prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Integer getIdxLength() {
        return idxLength;
    }

    public LIBLibrary idxLength(Integer idxLength) {
        this.idxLength = idxLength;
        return this;
    }

    public void setIdxLength(Integer idxLength) {
        this.idxLength = idxLength;
    }

    public String getShortcut() {
        return shortcut;
    }

    public LIBLibrary shortcut(String shortcut) {
        this.shortcut = shortcut;
        return this;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public String getName() {
        return name;
    }

    public LIBLibrary name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCounter() {
        return counter;
    }

    public LIBLibrary counter(Long counter) {
        this.counter = counter;
        return this;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public LIBCounterTypeEnum getCounterType() {
        return counterType;
    }

    public LIBLibrary counterType(LIBCounterTypeEnum counterType) {
        this.counterType = counterType;
        return this;
    }

    public void setCounterType(LIBCounterTypeEnum counterType) {
        this.counterType = counterType;
    }

    public LIBObjectTypeEnum getLibraryType() {
        return libraryType;
    }

    public LIBLibrary libraryType(LIBObjectTypeEnum libraryType) {
        this.libraryType = libraryType;
        return this;
    }

    public void setLibraryType(LIBObjectTypeEnum libraryType) {
        this.libraryType = libraryType;
    }

    public String getDescription() {
        return description;
    }

    public LIBLibrary description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CORNetwork getNetwork() {
        return network;
    }

    public LIBLibrary network(CORNetwork cORNetwork) {
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
        LIBLibrary lIBLibrary = (LIBLibrary) o;
        if (lIBLibrary.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, lIBLibrary.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LIBLibrary{" +
            "id=" + id +
            ", prefix='" + prefix + "'" +
            ", idxLength='" + idxLength + "'" +
            ", shortcut='" + shortcut + "'" +
            ", name='" + name + "'" +
            ", counter='" + counter + "'" +
            ", counterType='" + counterType + "'" +
            ", libraryType='" + libraryType + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
