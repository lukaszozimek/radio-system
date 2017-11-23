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
import java.util.stream.Collectors;

import static io.protone.scheduler.domain.SchDiscriminators.DYSCRYMINATOR_COLUMN;
import static io.protone.scheduler.domain.SchDiscriminators.EVENT_TEMPLATE;
import static javax.persistence.CascadeType.ALL;

/**
 * A SchEventTemplate.
 */
@Entity
@Table(name = "sch_event_template")
@DiscriminatorColumn(name = DYSCRYMINATOR_COLUMN, discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(EVENT_TEMPLATE)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchEventTemplate extends SchTimeParams implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "name")
    protected String name;


    @Column(name = "type", insertable = false, updatable = false, nullable = false)
    protected String type;

    @Column(name = "instance")
    protected Boolean instance = false;

    @Column(name = "short_name", unique = false, nullable = false)
    protected String shortName;

    @ManyToOne
    @PodamExclude
    protected CorDictionary eventCategory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.parentTemplate")
    @PodamExclude
    @OrderBy("pk.sequence")
    protected List<SchEventTemplateEvnetTemplate> schEventTemplates = new ArrayList<>();

    @Transient
    @PodamExclude
    private List<SchEmission> emissionsLog = new ArrayList<>();

    @PodamExclude
    @OneToMany(cascade = ALL, mappedBy = "schEventTemplate", orphanRemoval = true)
    @ElementCollection
    @JsonIgnore
    @OrderBy("sequence")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    protected List<SchEmissionTemplate> emissions = new ArrayList<>();


    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    protected EventTypeEnum eventType;

    @PodamExclude
    @ManyToOne
    protected SchLogConfiguration schLogConfiguration;


    @ManyToOne
    @PodamExclude
    protected CorNetwork network;

    @ManyToOne
    @PodamExclude
    protected CorChannel channel;

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


    public List<SchEventTemplateEvnetTemplate> getSchEventTemplates() {
        return schEventTemplates;
    }

    public void setSchEventTemplates(List<SchEventTemplateEvnetTemplate> schEventTemplates) {
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
        this.emissions.clear();
        if (emissions != null) {
            this.emissions.addAll(schEmissionTemplates);
        }
        return this;
    }

    public SchEventTemplate addEmission(SchEmission schEmission) {
        this.emissionsLog.add(schEmission);
        return this;
    }

    public List<SchEventTemplate> getChilds() {
        return this.schEventTemplates.stream().map(schEventTemplateEvnetTemplate -> schEventTemplateEvnetTemplate.getChild().sequence(schEventTemplateEvnetTemplate.getSequence())).collect(Collectors.toList());
    }

    public SchEventTemplate schEventTemplates(List<SchEventTemplateEvnetTemplate> collect) {
        this.schEventTemplates = collect;
        return this;
    }

    public SchEventTemplate addEmissionTemplate(SchEmissionTemplate schEmissionTemplate) {
        schEmissionTemplate.setSchEventTemplate(this);
        this.emissions.add(schEmissionTemplate);
        return this;
    }

    public SchEventTemplate addEventTemplate(SchEventTemplateEvnetTemplate schEventTemplate) {
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

    public SchEventTemplate sequence(Long sequence) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchEventTemplate)) return false;
        SchEventTemplate that = (SchEventTemplate) o;
        return Objects.equal(getName(), that.getName()) &&
                Objects.equal(getId(), that.getId()) &&
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
        return Objects.hashCode(getId(), getName(), getSequence(), getInstance(), getShortName(), getEventCategory(), getSchEventTemplates(), getEmissionsLog(), getEmissions(), getEventType(), getSchLogConfiguration(), getNetwork(), getChannel());
    }


}
