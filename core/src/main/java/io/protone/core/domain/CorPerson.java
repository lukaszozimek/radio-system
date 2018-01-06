package io.protone.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A CorPerson.
 */
@Entity
@Table(name = "cor_person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CorPerson extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @PodamExclude
    private Long id;

    @Size(max = 100)
    @Column(name = "first_name", length = 100, nullable = false)
    private String firstName;


    @Size(max = 100)
    @Column(name = "last_name", length = 100, nullable = false)
    private String lastName;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @PodamExclude
    private CorChannel channel;

    @ManyToOne
    @PodamExclude
    private CorItem author;

    @ManyToOne
    @PodamExclude
    private CorItem composer;

    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CorContact> contacts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public CorPerson firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public CorPerson lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CorPerson description(String description) {
        this.description = description;
        return this;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel corNetwork) {
        this.channel = corNetwork;
    }

    public CorPerson channel(CorChannel corNetwork) {
        this.channel = corNetwork;
        return this;
    }

    public CorItem getAuthor() {
        return author;
    }

    public void setAuthor(CorItem libMediaItem) {
        this.author = libMediaItem;
    }

    public CorPerson author(CorItem libMediaItem) {
        this.author = libMediaItem;
        return this;
    }

    public CorItem getComposer() {
        return composer;
    }

    public void setComposer(CorItem libMediaItem) {
        this.composer = libMediaItem;
    }

    public CorPerson composer(CorItem libMediaItem) {
        this.composer = libMediaItem;
        return this;
    }

    public Set<CorContact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<CorContact> corContacts) {
        this.contacts = corContacts;
    }

    public CorPerson contacts(Set<CorContact> corContacts) {
        this.contacts = corContacts;
        return this;
    }

    public CorPerson addContacts(CorContact corContact) {
        this.contacts.add(corContact);
        corContact.setPerson(this);
        return this;
    }

    public CorPerson removeContacts(CorContact corContact) {
        this.contacts.remove(corContact);
        corContact.setPerson(null);
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
        CorPerson corPerson = (CorPerson) o;
        if (corPerson.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, corPerson.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CorPerson{" +
                "id=" + id +
                ", firstName='" + firstName + "'" +
                ", lastName='" + lastName + "'" +
                ", description='" + description + "'" +
                '}';
    }
}
