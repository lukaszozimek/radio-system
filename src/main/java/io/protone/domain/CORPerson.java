package io.protone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CORPerson.
 */
@Entity
@Table(name = "cor_person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CORPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "first_name", length = 100, nullable = false)
    private String firstName;

    @NotNull
    @Size(max = 100)
    @Column(name = "last_name", length = 100, nullable = false)
    private String lastName;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(unique = true)
    private CORNetwork network;

    @OneToMany(mappedBy = "cORPerson")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CORContact> personContacts = new HashSet<>();

    @ManyToOne
    private LIBMediaItem mediaItem;

    @ManyToOne
    private LIBMediaItem lIBMediaItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public CORPerson firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public CORPerson lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public CORPerson description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CORNetwork getNetwork() {
        return network;
    }

    public CORPerson network(CORNetwork cORNetwork) {
        this.network = cORNetwork;
        return this;
    }

    public void setNetwork(CORNetwork cORNetwork) {
        this.network = cORNetwork;
    }

    public Set<CORContact> getPersonContacts() {
        return personContacts;
    }

    public CORPerson personContacts(Set<CORContact> cORContacts) {
        this.personContacts = cORContacts;
        return this;
    }

    public CORPerson addPersonContacts(CORContact cORContact) {
        personContacts.add(cORContact);
        cORContact.setCORPerson(this);
        return this;
    }

    public CORPerson removePersonContacts(CORContact cORContact) {
        personContacts.remove(cORContact);
        cORContact.setCORPerson(null);
        return this;
    }

    public void setPersonContacts(Set<CORContact> cORContacts) {
        this.personContacts = cORContacts;
    }

    public LIBMediaItem getmediaItem() {
        return mediaItem;
    }

    public CORPerson mediaItem(LIBMediaItem mediaItem) {
        this.mediaItem = mediaItem;
        return this;
    }

    public void setMediaItem(LIBMediaItem mediaItem) {
        this.mediaItem = mediaItem;
    }

    public LIBMediaItem getLIBMediaItem() {
        return mediaItem;
    }

    public CORPerson lIBMediaItem(LIBMediaItem lIBMediaItem) {
        this.mediaItem = lIBMediaItem;
        return this;
    }

    public void setLIBMediaItem(LIBMediaItem lIBMediaItem) {
        this.mediaItem = lIBMediaItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CORPerson cORPerson = (CORPerson) o;
        if (cORPerson.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cORPerson.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CORPerson{" +
            "id=" + id +
            ", firstName='" + firstName + "'" +
            ", lastName='" + lastName + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
