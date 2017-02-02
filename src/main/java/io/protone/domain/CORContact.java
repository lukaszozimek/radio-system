package io.protone.domain;

import io.protone.domain.enumeration.CORContactTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CORContact.
 */
@Entity
@Table(name = "cor_contact")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CORContact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "contact", length = 100, nullable = false)
    private String contact;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "contact_type", nullable = false)
    private CORContactTypeEnum contactType;

    @ManyToOne
    private CORPerson cORPerson;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public CORContact contact(String contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public CORContactTypeEnum getContactType() {
        return contactType;
    }

    public CORContact contactType(CORContactTypeEnum contactType) {
        this.contactType = contactType;
        return this;
    }

    public void setContactType(CORContactTypeEnum contactType) {
        this.contactType = contactType;
    }

    public CORPerson getCORPerson() {
        return cORPerson;
    }

    public CORContact cORPerson(CORPerson cORPerson) {
        this.cORPerson = cORPerson;
        return this;
    }

    public void setCORPerson(CORPerson cORPerson) {
        this.cORPerson = cORPerson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CORContact cORContact = (CORContact) o;
        if (cORContact.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cORContact.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CORContact{" +
            "id=" + id +
            ", contact='" + contact + "'" +
            ", contactType='" + contactType + "'" +
            '}';
    }
}
