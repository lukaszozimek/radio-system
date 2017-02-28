package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
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

    @NotNull
    @Size(max = 15)
    @Column(name = "idx", length = 15, nullable = false)
    private String idx;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @ManyToOne
    private LibLibrary library;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdx() {
        return idx;
    }

    public LibImageItem idx(String idx) {
        this.idx = idx;
        return this;
    }

    public void setIdx(String idx) {
        this.idx = idx;
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
            ", idx='" + idx + "'" +
            ", name='" + name + "'" +
            '}';
    }
}
