package io.protone.traffic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.protone.core.domain.AbstractAuditingEntity;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorDictionary;
import io.protone.crm.domain.CrmAccount;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A TraCampaign.
 */
@Entity
@Table(name = "tra_campaign", uniqueConstraints =
@UniqueConstraint(columnNames = {"short_name", "channel_id"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TraCampaign extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @PodamExclude
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "short_name", nullable = false, unique = true)
    private String shortName;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "prize")
    private Long prize;

    @ManyToOne
    @PodamExclude
    private CrmAccount customer;

    @ManyToOne
    @PodamExclude
    private CorChannel channel;

    @ManyToOne
    @PodamExclude
    private CorDictionary status;

    @OneToMany(mappedBy = "campaign")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @PodamExclude
    private Set<TraOrder> orders = new HashSet<>();

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

    public TraCampaign name(String name) {
        this.name = name;
        return this;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public TraCampaign shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public TraCampaign startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public TraCampaign endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public Long getPrize() {
        return prize;
    }

    public void setPrize(Long prize) {
        this.prize = prize;
    }

    public TraCampaign prize(Long prize) {
        this.prize = prize;
        return this;
    }

    public CrmAccount getCustomer() {
        return customer;
    }

    public void setCustomer(CrmAccount crmAccount) {
        this.customer = crmAccount;
    }

    public TraCampaign customer(CrmAccount crmAccount) {
        this.customer = crmAccount;
        return this;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel corNetwork) {
        this.channel = corNetwork;
    }

    public TraCampaign channel(CorChannel corNetwork) {
        this.channel = corNetwork;
        return this;
    }

    public CorDictionary getStatus() {
        return status;
    }

    public void setStatus(CorDictionary corDictionary) {
        this.status = corDictionary;
    }

    public TraCampaign status(CorDictionary corDictionary) {
        this.status = corDictionary;
        return this;
    }


    public Set<TraOrder> getOrders() {
        return orders;
    }

    public void setOrders(Set<TraOrder> traOrders) {
        this.orders = traOrders;
    }

    public TraCampaign orders(Set<TraOrder> traOrders) {
        this.orders = traOrders;
        return this;
    }

    public TraCampaign addOrders(TraOrder traOrder) {
        this.orders.add(traOrder);
        traOrder.setCampaign(this);
        return this;
    }

    public TraCampaign removeOrders(TraOrder traOrder) {
        this.orders.remove(traOrder);
        traOrder.setCampaign(null);
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
        TraCampaign traCampaign = (TraCampaign) o;
        if (traCampaign.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, traCampaign.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TraCampaign{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", prize=" + prize +
                ", customer=" + customer +
                ", organization=" + channel +
                ", status=" + status +
                ", orders=" + orders +
                '}';
    }
}
