package io.protone.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.protone.core.domain.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A CrmLead.
 */
@Entity
@Table(name = "crm_lead", uniqueConstraints =
@UniqueConstraint(columnNames = {"shortname", "channel_id"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CrmLead extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @PodamExclude
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "shortname", unique = true, nullable = false)
    private String shortname;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(unique = true)
    @PodamExclude
    private CorPerson person;

    @OneToOne
    @JoinColumn(unique = true)
    @PodamExclude
    private CorAddress addres;

    @ManyToOne
    @PodamExclude
    private CorDictionary leadStatus;

    @ManyToOne
    @PodamExclude
    private CorDictionary leadSource;

    @ManyToOne
    @PodamExclude
    private CorUser keeper;

    @ManyToOne
    @PodamExclude
    private CorDictionary industry;

    @ManyToOne
    @PodamExclude
    private CorDictionary area;

    @ManyToOne
    @PodamExclude
    private CorChannel channel;

    @OneToMany(mappedBy = "lead", fetch = FetchType.EAGER)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @PodamExclude
    private Set<CrmTask> tasks = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CrmLead name(String name) {
        this.name = name;
        return this;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public CrmLead shortname(String shortname) {
        this.shortname = shortname;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CrmLead description(String description) {
        this.description = description;
        return this;
    }

    public CorPerson getPerson() {
        return person;
    }

    public void setPerson(CorPerson corPerson) {
        this.person = corPerson;
    }

    public CrmLead person(CorPerson corPerson) {
        this.person = corPerson;
        return this;
    }

    public CorAddress getAddres() {
        return addres;
    }

    public void setAddres(CorAddress corAddress) {
        this.addres = corAddress;
    }

    public CrmLead addres(CorAddress corAddress) {
        this.addres = corAddress;
        return this;
    }

    public CorDictionary getLeadStatus() {
        return leadStatus;
    }

    public void setLeadStatus(CorDictionary corDictionary) {
        this.leadStatus = corDictionary;
    }

    public CrmLead leadStatus(CorDictionary corDictionary) {
        this.leadStatus = corDictionary;
        return this;
    }

    public CorDictionary getLeadSource() {
        return leadSource;
    }

    public void setLeadSource(CorDictionary corDictionary) {
        this.leadSource = corDictionary;
    }

    public CrmLead leadSource(CorDictionary corDictionary) {
        this.leadSource = corDictionary;
        return this;
    }

    public CorUser getKeeper() {
        return keeper;
    }

    public void setKeeper(CorUser corUser) {
        this.keeper = corUser;
    }

    public CrmLead keeper(CorUser corUser) {
        this.keeper = corUser;
        return this;
    }

    public CorDictionary getIndustry() {
        return industry;
    }

    public void setIndustry(CorDictionary corDictionary) {
        this.industry = corDictionary;
    }

    public CrmLead industry(CorDictionary corDictionary) {
        this.industry = corDictionary;
        return this;
    }

    public CorDictionary getArea() {
        return area;
    }

    public void setArea(CorDictionary corDictionary) {
        this.area = corDictionary;
    }

    public CrmLead area(CorDictionary corDictionary) {
        this.area = corDictionary;
        return this;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel corNetwork) {
        this.channel = corNetwork;
    }

    public CrmLead channel(CorChannel corChannel) {
        this.channel = corChannel;
        return this;
    }

    public Set<CrmTask> getTasks() {
        return tasks;
    }

    public void setTasks(Set<CrmTask> crmTasks) {
        this.tasks = crmTasks;
    }

    public CrmLead tasks(Set<CrmTask> crmTasks) {
        this.tasks = crmTasks;
        return this;
    }

    public CrmLead addTasks(CrmTask crmTask) {
        this.tasks.add(crmTask);
        crmTask.setLead(this);
        return this;
    }

    public CrmLead removeTasks(CrmTask crmTask) {
        this.tasks.remove(crmTask);
        crmTask.setLead(null);
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
        CrmLead crmLead = (CrmLead) o;
        if (crmLead.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, crmLead.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CrmLead{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", shortname='" + shortname + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
