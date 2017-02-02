package io.protone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CRMLead.
 */
@Entity
@Table(name = "crm_lead")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CRMLead implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "shortname")
    private String shortname;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(unique = true)
    private CRMLeadStatus leadStatus;

    @OneToOne
    @JoinColumn(unique = true)
    private CRMLeadSource leadSource;

    @OneToOne
    @JoinColumn(unique = true)
    private CORAddress addres;

    @OneToOne
    @JoinColumn(unique = true)
    private CORUser keeper;

    @OneToOne
    @JoinColumn(unique = true)
    private TRAIndustry industry;

    @OneToOne
    @JoinColumn(unique = true)
    private CORArea area;

    @OneToOne
    @JoinColumn(unique = true)
    private CORPerson person;

    @OneToOne
    @JoinColumn(unique = true)
    private CORNetwork network;

    @OneToMany(mappedBy = "cRMLead")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CRMTask> tasks = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CRMLead name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortname() {
        return shortname;
    }

    public CRMLead shortname(String shortname) {
        this.shortname = shortname;
        return this;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getDescription() {
        return description;
    }

    public CRMLead description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CRMLeadStatus getLeadStatus() {
        return leadStatus;
    }

    public CRMLead leadStatus(CRMLeadStatus cRMLeadStatus) {
        this.leadStatus = cRMLeadStatus;
        return this;
    }

    public void setLeadStatus(CRMLeadStatus cRMLeadStatus) {
        this.leadStatus = cRMLeadStatus;
    }

    public CRMLeadSource getLeadSource() {
        return leadSource;
    }

    public CRMLead leadSource(CRMLeadSource cRMLeadSource) {
        this.leadSource = cRMLeadSource;
        return this;
    }

    public void setLeadSource(CRMLeadSource cRMLeadSource) {
        this.leadSource = cRMLeadSource;
    }

    public CORAddress getAddres() {
        return addres;
    }

    public CRMLead addres(CORAddress cORAddress) {
        this.addres = cORAddress;
        return this;
    }

    public void setAddres(CORAddress cORAddress) {
        this.addres = cORAddress;
    }

    public CORUser getKeeper() {
        return keeper;
    }

    public CRMLead keeper(CORUser cORUser) {
        this.keeper = cORUser;
        return this;
    }

    public void setKeeper(CORUser cORUser) {
        this.keeper = cORUser;
    }

    public TRAIndustry getIndustry() {
        return industry;
    }

    public CRMLead industry(TRAIndustry tRAIndustry) {
        this.industry = tRAIndustry;
        return this;
    }

    public void setIndustry(TRAIndustry tRAIndustry) {
        this.industry = tRAIndustry;
    }

    public CORArea getArea() {
        return area;
    }

    public CRMLead area(CORArea cORArea) {
        this.area = cORArea;
        return this;
    }

    public void setArea(CORArea cORArea) {
        this.area = cORArea;
    }

    public CORPerson getPerson() {
        return person;
    }

    public CRMLead person(CORPerson cORPerson) {
        this.person = cORPerson;
        return this;
    }

    public void setPerson(CORPerson cORPerson) {
        this.person = cORPerson;
    }

    public CORNetwork getNetwork() {
        return network;
    }

    public CRMLead network(CORNetwork cORNetwork) {
        this.network = cORNetwork;
        return this;
    }

    public void setNetwork(CORNetwork cORNetwork) {
        this.network = cORNetwork;
    }

    public Set<CRMTask> getTasks() {
        return tasks;
    }

    public CRMLead tasks(Set<CRMTask> cRMTasks) {
        this.tasks = cRMTasks;
        return this;
    }

    public CRMLead addTasks(CRMTask cRMTask) {
        tasks.add(cRMTask);
        cRMTask.setCRMLead(this);
        return this;
    }

    public CRMLead removeTasks(CRMTask cRMTask) {
        tasks.remove(cRMTask);
        cRMTask.setCRMLead(null);
        return this;
    }

    public void setTasks(Set<CRMTask> cRMTasks) {
        this.tasks = cRMTasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CRMLead cRMLead = (CRMLead) o;
        if (cRMLead.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cRMLead.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CRMLead{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", shortname='" + shortname + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
