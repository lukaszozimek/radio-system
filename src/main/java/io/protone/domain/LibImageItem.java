package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A LibImageItem.
 */
@Entity
@Table(name = "lib_image_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LibImageItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "public_url")
    private String publicUrl;

    @ManyToOne
    private LibLibrary library;

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

    public LibImageItem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublicUrl() {
        return publicUrl;
    }

    public LibImageItem publicUrl(String publicUrl) {
        this.publicUrl = publicUrl;
        return this;
    }

    public void setPublicUrl(String publicUrl) {
        this.publicUrl = publicUrl;
    }

    public LibLibrary getLibrary() {
        return library;
    }

    public LibImageItem library(LibLibrary libLibrary) {
        this.library = libLibrary;
        return this;
    }

    public void setLibrary(LibLibrary libLibrary) {
        this.library = libLibrary;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public LibImageItem network(CorNetwork corNetwork) {
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
        LibImageItem libImageItem = (LibImageItem) o;
        if (libImageItem.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, libImageItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LibImageItem{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", publicUrl='" + publicUrl + "'" +
            '}';
    }
}
