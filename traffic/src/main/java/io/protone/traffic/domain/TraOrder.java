package io.protone.traffic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.protone.core.domain.AbstractAuditingEntity;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorNetwork;
import io.protone.crm.domain.CrmAccount;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A TraOrder.
 */
@Entity
@Table(name = "tra_order", uniqueConstraints =
@UniqueConstraint(columnNames = {"name", "network_id"}))

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TraOrder extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @PodamExclude
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "calculated_prize", precision = 10, scale = 2)
    @PodamExclude
    private BigDecimal calculatedPrize;

    @ManyToOne
    @PodamExclude
    private CrmAccount customer;

    @Column(name = "price", precision = 10, scale = 2)
    @PodamExclude
    private BigDecimal price;

    @ManyToOne
    @PodamExclude
    private CorNetwork network;

    @ManyToOne
    @PodamExclude
    private CorDictionary status;

    @ManyToOne
    @PodamExclude
    private TraAdvertisement advertisment;

    @ManyToOne
    @PodamExclude
    private TraCampaign campaign;

    @ManyToOne
    @PodamExclude
    private TraInvoice invoice;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @PodamExclude
    private Set<TraEmission> emissions = new HashSet<>();

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

    public TraOrder name(String name) {
        this.name = name;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public TraOrder startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public TraOrder endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public BigDecimal getCalculatedPrize() {
        return calculatedPrize;
    }

    public void setCalculatedPrize(BigDecimal calculatedPrize) {
        this.calculatedPrize = calculatedPrize;
    }

    public TraOrder calculatedPrize(BigDecimal calculatedPrize) {
        this.calculatedPrize = calculatedPrize;
        return this;
    }

    public CrmAccount getCustomer() {
        return customer;
    }

    public void setCustomer(CrmAccount crmAccount) {
        this.customer = crmAccount;
    }

    public TraOrder customer(CrmAccount crmAccount) {
        this.customer = crmAccount;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal traPrice) {
        this.price = traPrice;
    }

    public TraOrder price(BigDecimal traPrice) {
        this.price = traPrice;
        return this;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public TraOrder network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public CorDictionary getStatus() {
        return status;
    }

    public void setStatus(CorDictionary corDictionary) {
        this.status = corDictionary;
    }

    public TraOrder status(CorDictionary corDictionary) {
        this.status = corDictionary;
        return this;
    }

    public TraAdvertisement getAdvertisment() {
        return advertisment;
    }

    public void setAdvertisment(TraAdvertisement traAdvertisement) {
        this.advertisment = traAdvertisement;
    }

    public TraOrder advertisment(TraAdvertisement traAdvertisement) {
        this.advertisment = traAdvertisement;
        return this;
    }

    public TraCampaign getCampaign() {
        return campaign;
    }

    public void setCampaign(TraCampaign traCampaign) {
        this.campaign = traCampaign;
    }

    public TraOrder campaign(TraCampaign traCampaign) {
        this.campaign = traCampaign;
        return this;
    }

    public TraInvoice getInvoice() {
        return invoice;
    }

    public void setInvoice(TraInvoice traInvoice) {
        this.invoice = traInvoice;
    }

    public TraOrder invoice(TraInvoice traInvoice) {
        this.invoice = traInvoice;
        return this;
    }

    public Set<TraEmission> getEmissions() {
        return emissions;
    }

    public void setEmissions(Set<TraEmission> traEmissions) {
        this.emissions = traEmissions;
    }

    public TraOrder emissions(Set<TraEmission> traEmissions) {
        this.emissions = traEmissions;
        return this;
    }

    public TraOrder addEmissions(TraEmission traEmission) {
        this.emissions.add(traEmission);
        traEmission.setOrder(this);
        return this;
    }

    public TraOrder removeEmissions(TraEmission traEmission) {
        this.emissions.remove(traEmission);
        traEmission.setOrder(null);
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
        TraOrder traOrder = (TraOrder) o;
        if (traOrder.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, traOrder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TraOrder{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", startDate='" + startDate + "'" +
                ", endDate='" + endDate + "'" +
                ", calculatedPrize='" + calculatedPrize + "'" +
                '}';
    }
}
