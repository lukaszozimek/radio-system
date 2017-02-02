package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CORNotification.
 */
@Entity
@Table(name = "cor_notification")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CORNotification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private CORUser cORUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public CORNotification description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CORUser getCORUser() {
        return cORUser;
    }

    public CORNotification cORUser(CORUser cORUser) {
        this.cORUser = cORUser;
        return this;
    }

    public void setCORUser(CORUser cORUser) {
        this.cORUser = cORUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CORNotification cORNotification = (CORNotification) o;
        if (cORNotification.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cORNotification.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CORNotification{" +
            "id=" + id +
            ", description='" + description + "'" +
            '}';
    }
}
