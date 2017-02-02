package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import io.protone.domain.enumeration.LIBFileTypeEnum;

/**
 * A LIBFileItem.
 */
@Entity
@Table(name = "lib_file_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LIBFileItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private LIBFileTypeEnum type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdx() {
        return idx;
    }

    public LIBFileItem idx(String idx) {
        this.idx = idx;
        return this;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getName() {
        return name;
    }

    public LIBFileItem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LIBFileTypeEnum getType() {
        return type;
    }

    public LIBFileItem type(LIBFileTypeEnum type) {
        this.type = type;
        return this;
    }

    public void setType(LIBFileTypeEnum type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LIBFileItem lIBFileItem = (LIBFileItem) o;
        if (lIBFileItem.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, lIBFileItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LIBFileItem{" +
            "id=" + id +
            ", idx='" + idx + "'" +
            ", name='" + name + "'" +
            ", type='" + type + "'" +
            '}';
    }
}
