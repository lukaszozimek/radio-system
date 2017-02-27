package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CorAuthorities.
 */
@Entity
@Table(name = "cor_authorities")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CorAuthorities implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    private CorUser authorities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CorAuthorities name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CorUser getAuthorities() {
        return authorities;
    }

    public CorAuthorities authorities(CorUser corUser) {
        this.authorities = corUser;
        return this;
    }

    public void setAuthorities(CorUser corUser) {
        this.authorities = corUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CorAuthorities corAuthorities = (CorAuthorities) o;
        if (corAuthorities.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, corAuthorities.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CorAuthorities{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
