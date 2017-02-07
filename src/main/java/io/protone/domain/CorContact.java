package io.protone.domain;

import io.protone.domain.enumeration.CorContactTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CorContact.
 */
@Entity
@Table(name = "cor_contact")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CorContact implements Serializable {

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
    private CorContactTypeEnum contactType;

    @ManyToOne
    private CorNetwork network;

    @ManyToOne
    private CorPerson person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public CorContact contact(String contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public CorContactTypeEnum getContactType() {
        return contactType;
    }

    public CorContact contactType(CorContactTypeEnum contactType) {
        this.contactType = contactType;
        return this;
    }

    public void setContactType(CorContactTypeEnum contactType) {
        this.contactType = contactType;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public CorContact network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public CorPerson getPerson() {
        return person;
    }

    public CorContact person(CorPerson corPerson) {
        this.person = corPerson;
        return this;
    }

    public void setPerson(CorPerson corPerson) {
        this.person = corPerson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CorContact corContact = (CorContact) o;
        if (corContact.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, corContact.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CorContact{" +
            "id=" + id +
            ", contact='" + contact + "'" +
            ", contactType='" + contactType + "'" +
            '}';
    }
}
