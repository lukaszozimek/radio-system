package io.protone.scheduler.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @PodamExclude
    @ManyToOne(cascade = CascadeType.ALL)
    private SchEventTemplate schEventTemplate;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "schEventTemplate")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @PodamExclude
    private List<SchEventTemplate> schEventTemplates = new ArrayList<>();

    @Transient
    @PodamExclude
    private List<SchEmission> emissionsLog = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "eventTemplate")
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

    public SchEventTemplate getSchEventTemplate() {
        return schEventTemplate;
    }

    public void setSchEventTemplate(SchEventTemplate schEventTemplate) {
        this.schEventTemplate = schEventTemplate;
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

    @Override
    public String toString() {
        return "SchEventTemplate{" +
                "sequence=" + sequence +
                ", id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", length=" + length +
                ", name='" + name + '\'' +
                ", instance=" + instance +
                ", shortName='" + shortName + '\'' +
                ", eventCategory=" + eventCategory +
                ", schEventTemplate=" + schEventTemplate +
                ", schEventTemplates=" + schEventTemplates +
                ", emissionsLog=" + emissionsLog +
                ", emissions=" + emissions +
                ", eventType=" + eventType +
                ", schLogConfiguration=" + schLogConfiguration +
                ", network=" + network +
                ", channel=" + channel +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchEventTemplate)) return false;

        SchEventTemplate that = (SchEventTemplate) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (instance != null ? !instance.equals(that.instance) : that.instance != null) return false;
        if (shortName != null ? !shortName.equals(that.shortName) : that.shortName != null) return false;
        if (eventCategory != null ? !eventCategory.equals(that.eventCategory) : that.eventCategory != null)
            return false;
        if (schEventTemplate != null ? !schEventTemplate.equals(that.schEventTemplate) : that.schEventTemplate != null)
            return false;
        if (schEventTemplates != null ? !schEventTemplates.equals(that.schEventTemplates) : that.schEventTemplates != null)
            return false;
        if (emissionsLog != null ? !emissionsLog.equals(that.emissionsLog) : that.emissionsLog != null) return false;
        if (emissions != null ? !emissions.equals(that.emissions) : that.emissions != null) return false;
        if (eventType != that.eventType) return false;
        if (schLogConfiguration != null ? !schLogConfiguration.equals(that.schLogConfiguration) : that.schLogConfiguration != null)
            return false;
        if (network != null ? !network.equals(that.network) : that.network != null) return false;
        return channel != null ? channel.equals(that.channel) : that.channel == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (instance != null ? instance.hashCode() : 0);
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + (eventCategory != null ? eventCategory.hashCode() : 0);
        result = 31 * result + (schEventTemplate != null ? schEventTemplate.hashCode() : 0);
        result = 31 * result + (schEventTemplates != null ? schEventTemplates.hashCode() : 0);
        result = 31 * result + (emissionsLog != null ? emissionsLog.hashCode() : 0);
        result = 31 * result + (emissions != null ? emissions.hashCode() : 0);
        result = 31 * result + (eventType != null ? eventType.hashCode() : 0);
        result = 31 * result + (schLogConfiguration != null ? schLogConfiguration.hashCode() : 0);
        result = 31 * result + (network != null ? network.hashCode() : 0);
        result = 31 * result + (channel != null ? channel.hashCode() : 0);
        return result;
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
}
