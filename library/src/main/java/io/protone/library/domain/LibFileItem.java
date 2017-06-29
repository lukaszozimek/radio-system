package io.protone.library.domain;


import io.protone.library.domain.enumeration.LibFileTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A LibFileItem.
 */
@Entity
@Table(name = "lib_file_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LibFileItem implements Serializable {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private LibFileTypeEnum type;

    @ManyToOne
    private LibLibrary library;

    @ManyToOne
    private LibFileItem parentFile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public LibFileItem idx(String idx) {
        this.idx = idx;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LibFileItem name(String name) {
        this.name = name;
        return this;
    }

    public LibFileTypeEnum getType() {
        return type;
    }

    public void setType(LibFileTypeEnum type) {
        this.type = type;
    }

    public LibFileItem type(LibFileTypeEnum type) {
        this.type = type;
        return this;
    }

    public LibLibrary getLibrary() {
        return library;
    }

    public void setLibrary(LibLibrary libLibrary) {
        this.library = libLibrary;
    }

    public LibFileItem library(LibLibrary libLibrary) {
        this.library = libLibrary;
        return this;
    }

    public LibFileItem getParentFile() {
        return parentFile;
    }

    public void setParentFile(LibFileItem libFileItem) {
        this.parentFile = libFileItem;
    }

    public LibFileItem parentFile(LibFileItem libFileItem) {
        this.parentFile = libFileItem;
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
        LibFileItem libFileItem = (LibFileItem) o;
        if (libFileItem.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, libFileItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LibFileItem{" +
            "id=" + id +
            ", idx='" + idx + "'" +
            ", name='" + name + "'" +
            ", type='" + type + "'" +
            '}';
    }
}
