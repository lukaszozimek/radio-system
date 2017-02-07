package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TraAdvertisement.
 */
@Entity
@Table(name = "tra_advertisement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TraAdvertisement implements Serializable {

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

    @OneToOne
    @JoinColumn(unique = true)
    private LibMediaItem mediaItem;

    @OneToOne
    @JoinColumn(unique = true)
    private LibMediaItem libitem;

    @OneToOne
    @JoinColumn(unique = true)
    private TraIndustry industry;

    @OneToOne
    @JoinColumn(unique = true)
    private TraAdvertismentType type;

    @ManyToOne
    private CrmAccount network;

    @ManyToOne
    private CorNetwork network;

    @ManyToOne
    private CrmAccount customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TraAdvertisement name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public TraAdvertisement description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LibMediaItem getMediaItem() {
        return mediaItem;
    }

    public TraAdvertisement mediaItem(LibMediaItem libMediaItem) {
        this.mediaItem = libMediaItem;
        return this;
    }

    public void setMediaItem(LibMediaItem libMediaItem) {
        this.mediaItem = libMediaItem;
    }

    public LibMediaItem getLibitem() {
        return libitem;
    }

    public TraAdvertisement libitem(LibMediaItem libMediaItem) {
        this.libitem = libMediaItem;
        return this;
    }

    public void setLibitem(LibMediaItem libMediaItem) {
        this.libitem = libMediaItem;
    }

    public TraIndustry getIndustry() {
        return industry;
    }

    public TraAdvertisement industry(TraIndustry traIndustry) {
        this.industry = traIndustry;
        return this;
    }

    public void setIndustry(TraIndustry traIndustry) {
        this.industry = traIndustry;
    }

    public TraAdvertismentType getType() {
        return type;
    }

    public TraAdvertisement type(TraAdvertismentType traAdvertismentType) {
        this.type = traAdvertismentType;
        return this;
    }

    public void setType(TraAdvertismentType traAdvertismentType) {
        this.type = traAdvertismentType;
    }

    public CrmAccount getNetwork() {
        return network;
    }

    public TraAdvertisement network(CrmAccount crmAccount) {
        this.network = crmAccount;
        return this;
    }

    public void setNetwork(CrmAccount crmAccount) {
        this.network = crmAccount;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public TraAdvertisement network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public CrmAccount getCustomer() {
        return customer;
    }

    public TraAdvertisement customer(CrmAccount crmAccount) {
        this.customer = crmAccount;
        return this;
    }

    public void setCustomer(CrmAccount crmAccount) {
        this.customer = crmAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TraAdvertisement traAdvertisement = (TraAdvertisement) o;
        if (traAdvertisement.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, traAdvertisement.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TraAdvertisement{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
