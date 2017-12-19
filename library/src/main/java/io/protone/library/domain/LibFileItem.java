package io.protone.library.domain;


import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorItem;
import io.protone.library.domain.enumeration.LibFileTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A LibFileItem.
 */
@Entity
@Table(name = "lib_file_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LibFileItem extends CorItem implements Serializable {

    private static final long serialVersionUID = 1L;


    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private LibFileTypeEnum type;

    @ManyToOne
    private LibFileLibrary library;

    @OneToOne
    @JoinColumn(unique = true)
    private LibCloudObject cloudObject;


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

    public LibFileLibrary getLibrary() {
        return library;
    }

    public void setLibrary(LibFileLibrary libLibrary) {
        this.library = libLibrary;
    }

    public LibFileItem library(LibFileLibrary libLibrary) {
        this.library = libLibrary;
        return this;
    }

    public LibCloudObject getCloudObject() {
        return cloudObject;
    }

    public void setCloudObject(LibCloudObject cloudObject) {
        this.cloudObject = cloudObject;
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

    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel channel) {
        this.channel = channel;
    }

    public LibFileItem channel(CorChannel channel) {
        this.channel = channel;
        return this;
    }

}
