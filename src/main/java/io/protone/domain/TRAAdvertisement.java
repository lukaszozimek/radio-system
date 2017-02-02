package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TRAAdvertisement.
 */
@Entity
@Table(name = "tra_advertisement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TRAAdvertisement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private CRMAccount cRMAccount;

    @OneToOne
    @JoinColumn(unique = true)
    private LIBMediaItem libitem;

    @OneToOne
    @JoinColumn(unique = true)
    private CRMAccount customer;

    @OneToOne
    @JoinColumn(unique = true)
    private TRAIndustry industry;

    @OneToOne
    @JoinColumn(unique = true)
    private CORNetwork network;

    @OneToOne
    @JoinColumn(unique = true)
    private TRAAdvertismentType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TRAAdvertisement name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public TRAAdvertisement description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CRMAccount getCRMAccount() {
        return cRMAccount;
    }

    public TRAAdvertisement cRMAccount(CRMAccount cRMAccount) {
        this.cRMAccount = cRMAccount;
        return this;
    }

    public void setCRMAccount(CRMAccount cRMAccount) {
        this.cRMAccount = cRMAccount;
    }

    public LIBMediaItem getLibitem() {
        return libitem;
    }

    public TRAAdvertisement libitem(LIBMediaItem lIBMediaItem) {
        this.libitem = lIBMediaItem;
        return this;
    }

    public void setLibitem(LIBMediaItem lIBMediaItem) {
        this.libitem = lIBMediaItem;
    }

    public CRMAccount getCustomer() {
        return customer;
    }

    public TRAAdvertisement customer(CRMAccount cRMAccount) {
        this.customer = cRMAccount;
        return this;
    }

    public void setCustomer(CRMAccount cRMAccount) {
        this.customer = cRMAccount;
    }

    public TRAIndustry getIndustry() {
        return industry;
    }

    public TRAAdvertisement industry(TRAIndustry tRAIndustry) {
        this.industry = tRAIndustry;
        return this;
    }

    public void setIndustry(TRAIndustry tRAIndustry) {
        this.industry = tRAIndustry;
    }

    public CORNetwork getNetwork() {
        return network;
    }

    public TRAAdvertisement network(CORNetwork cORNetwork) {
        this.network = cORNetwork;
        return this;
    }

    public void setNetwork(CORNetwork cORNetwork) {
        this.network = cORNetwork;
    }

    public TRAAdvertismentType getType() {
        return type;
    }

    public TRAAdvertisement type(TRAAdvertismentType tRAAdvertismentType) {
        this.type = tRAAdvertismentType;
        return this;
    }

    public void setType(TRAAdvertismentType tRAAdvertismentType) {
        this.type = tRAAdvertismentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TRAAdvertisement tRAAdvertisement = (TRAAdvertisement) o;
        if (tRAAdvertisement.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tRAAdvertisement.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TRAAdvertisement{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
