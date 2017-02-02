package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import io.protone.domain.enumeration.LIBArtistTypeEnum;

/**
 * A LIBArtist.
 */
@Entity
@Table(name = "lib_artist")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LIBArtist implements Serializable {

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
    private LIBArtistTypeEnum type;

    @Column(name = "description")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public LIBArtist name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LIBArtistTypeEnum getType() {
        return type;
    }

    public LIBArtist type(LIBArtistTypeEnum type) {
        this.type = type;
        return this;
    }

    public void setType(LIBArtistTypeEnum type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public LIBArtist description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LIBArtist lIBArtist = (LIBArtist) o;
        if (lIBArtist.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, lIBArtist.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LIBArtist{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", type='" + type + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
