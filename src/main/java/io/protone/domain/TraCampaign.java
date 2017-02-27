package io.protone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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
@Table(name = "tra_campaign")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TraCampaign implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "prize")
    private Long prize;

    @ManyToOne
    private CrmAccount customer;

    @ManyToOne
    private CorNetwork network;

    @ManyToOne
    private TraCampaingStatus status;

    @ManyToOne
    private TraPrice price;

    @ManyToOne
    private TraOrder orders;

    @OneToMany(mappedBy = "campaings")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SchEmission> emissions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TraCampaign name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public TraCampaign startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public TraCampaign endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getPrize() {
        return prize;
    }

    public TraCampaign prize(Long prize) {
        this.prize = prize;
        return this;
    }

    public void setPrize(Long prize) {
        this.prize = prize;
    }

    public CrmAccount getCustomer() {
        return customer;
    }

    public TraCampaign customer(CrmAccount crmAccount) {
        this.customer = crmAccount;
        return this;
    }

    public void setCustomer(CrmAccount crmAccount) {
        this.customer = crmAccount;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public TraCampaign network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public TraCampaingStatus getStatus() {
        return status;
    }

    public TraCampaign status(TraCampaingStatus traCampaingStatus) {
        this.status = traCampaingStatus;
        return this;
    }

    public void setStatus(TraCampaingStatus traCampaingStatus) {
        this.status = traCampaingStatus;
    }

    public TraPrice getPrice() {
        return price;
    }

    public TraCampaign price(TraPrice traPrice) {
        this.price = traPrice;
        return this;
    }

    public void setPrice(TraPrice traPrice) {
        this.price = traPrice;
    }

    public TraOrder getOrders() {
        return orders;
    }

    public TraCampaign orders(TraOrder traOrder) {
        this.orders = traOrder;
        return this;
    }

    public void setOrders(TraOrder traOrder) {
        this.orders = traOrder;
    }

    public Set<SchEmission> getEmissions() {
        return emissions;
    }

    public TraCampaign emissions(Set<SchEmission> schEmissions) {
        this.emissions = schEmissions;
        return this;
    }

    public TraCampaign addEmissions(SchEmission schEmission) {
        this.emissions.add(schEmission);
        schEmission.setCampaings(this);
        return this;
    }

    public TraCampaign removeEmissions(SchEmission schEmission) {
        this.emissions.remove(schEmission);
        schEmission.setCampaings(null);
        return this;
    }

    public void setEmissions(Set<SchEmission> schEmissions) {
        this.emissions = schEmissions;
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
            ", name='" + name + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", prize='" + prize + "'" +
            '}';
    }
}
