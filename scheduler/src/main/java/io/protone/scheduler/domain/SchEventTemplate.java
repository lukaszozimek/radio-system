package io.protone.scheduler.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.domain.enumeration.EventTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A SchEventTemplate.
 */
@Entity
@Table(name = "sch_event_template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchEventTemplate extends SchTimeParams implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "name")
    private String name;

    @Column(name = "instance")
    private Boolean instance = false;

    @Column(name = "short_name", unique = false, nullable = false)
    private String shortName;

    @ManyToOne
    @PodamExclude
    private CorDictionary eventCategory;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "sch_event_sch_event",
            joinColumns = {@JoinColumn(name = "child_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "parent_id", referencedColumnName = "id")})
    @ElementCollection
    @PodamExclude
    private List<SchEventTemplate> schEventTemplates = new ArrayList<>();

    @Transient
    @PodamExclude
    private List<SchEmission> emissionsLog = new ArrayList<>();

    @PodamExclude
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "sch_event_sch_emission",
            joinColumns = {@JoinColumn(name = "emission_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "event_id", referencedColumnName = "id")})
    @ElementCollection
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<SchEmissionTemplate> emissions = new ArrayList<>();


    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private EventTypeEnum eventType;

    @PodamExclude
    @ManyToOne
    private SchLogConfiguration schLogConfiguration;


    @ManyToOne
    @PodamExclude
    private CorNetwork network;

    @ManyToOne
    @PodamExclude
    private CorChannel channel;

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

    public Boolean getInstance() {
        return instance;
    }

    public void setInstance(Boolean instance) {
        this.instance = instance;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public CorDictionary getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(CorDictionary eventCategory) {
        this.eventCategory = eventCategory;
    }


    public List<SchEventTemplate> getSchEventTemplates() {
        return schEventTemplates;
    }

    public void setSchEventTemplates(List<SchEventTemplate> schEventTemplates) {
        this.schEventTemplates = schEventTemplates;
    }

    public List<SchEmission> getEmissionsLog() {
        return emissionsLog;
    }

    public void setEmissionsLog(List<SchEmission> emissionsLog) {
        this.emissionsLog = emissionsLog;
    }

    public List<SchEmissionTemplate> getEmissions() {
        return emissions;
    }

    public void setEmissions(List<SchEmissionTemplate> emissions) {
        this.emissions = emissions;
    }

    public EventTypeEnum getEventType() {
        return eventType;
    }

    public void setEventType(EventTypeEnum eventType) {
        this.eventType = eventType;
    }

    public SchLogConfiguration getSchLogConfiguration() {
        return schLogConfiguration;
    }

    public void setSchLogConfiguration(SchLogConfiguration schLogConfiguration) {
        this.schLogConfiguration = schLogConfiguration;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork network) {
        this.network = network;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel channel) {
        this.channel = channel;
    }


    public SchEventTemplate emissions(List<SchEmissionTemplate> schEmissionTemplates) {
        this.emissions = schEmissionTemplates;
        return this;
    }

    public SchEventTemplate addEmission(SchEmission schEmission) {
        this.emissionsLog.add(schEmission);
        return this;
    }

    public SchEventTemplate schEventTemplates(List<SchEventTemplate> collect) {
        this.schEventTemplates = collect;
        return this;
    }

    public SchEventTemplate addEmissionTemplate(SchEmissionTemplate schEmissionTemplate) {
        this.emissions.add(schEmissionTemplate);
        return this;
    }

    public SchEventTemplate addEventTemplate(SchEventTemplate schEventTemplate) {
        this.schEventTemplates.add(schEventTemplate);
        return this;
    }

    public SchEventTemplate network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public SchEventTemplate channel(CorChannel corChannel) {
        this.channel = corChannel;
        return this;
    }


    public SchEventTemplate name(String updatedName) {
        this.name = updatedName;
        return this;
    }

    public SchEventTemplate shortName(String updatedShortname) {
        this.shortName = updatedShortname;
        return this;
    }

    public SchEventTemplate eventCategory(CorDictionary corDictionary) {
        this.eventCategory = corDictionary;
        return this;
    }

    public SchEventTemplate sequence(long sequence) {
        this.sequence = sequence;
        return this;
    }

    public SchEventTemplate eventType(EventTypeEnum eventType) {
        this.eventType = eventType;
        return this;
    }

    public SchEventTemplate schLogConfiguration(SchLogConfiguration schLogConfiguration) {
        this.schLogConfiguration = schLogConfiguration;
        return this;
    }

    public SchEventTemplate length(long l) {
        this.length = l;
        return this;
    }

    public SchEventTemplate instance(boolean instance) {
        this.instance = instance;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchEventTemplate)) return false;
        SchEventTemplate that = (SchEventTemplate) o;
        return Objects.equal(getName(), that.getName()) &&
                Objects.equal(getInstance(), that.getInstance()) &&
                Objects.equal(getShortName(), that.getShortName()) &&
                Objects.equal(getEventCategory(), that.getEventCategory()) &&
                Objects.equal(getSchEventTemplates(), that.getSchEventTemplates()) &&
                Objects.equal(getEmissionsLog(), that.getEmissionsLog()) &&
                Objects.equal(getEmissions(), that.getEmissions()) &&
                getEventType() == that.getEventType() &&
                Objects.equal(getSchLogConfiguration(), that.getSchLogConfiguration()) &&
                Objects.equal(getNetwork(), that.getNetwork()) &&
                Objects.equal(getChannel(), that.getChannel()) &&
                Objects.equal(getSequence(), that.getSequence());

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName(), getSequence(), getInstance(), getShortName(), getEventCategory(), getSchEventTemplates(), getEmissionsLog(), getEmissions(), getEventType(), getSchLogConfiguration(), getNetwork(), getChannel());
    }


}
