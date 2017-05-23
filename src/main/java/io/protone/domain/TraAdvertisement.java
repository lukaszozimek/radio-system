package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(unique = true)
    @PodamExclude
    private LibMediaItem mediaItem;

    @ManyToOne
    @PodamExclude
    private CrmAccount customer;

    @ManyToOne
    @PodamExclude
    private CorNetwork network;

    @ManyToOne
    @PodamExclude
    private CorDictionary industry;

    @ManyToOne
    @PodamExclude
    private CorDictionary type;

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

    public CorDictionary getIndustry() {
        return industry;
    }

    public TraAdvertisement industry(CorDictionary corDictionary) {
        this.industry = corDictionary;
        return this;
    }

    public void setIndustry(CorDictionary corDictionary) {
        this.industry = corDictionary;
    }

    public CorDictionary getType() {
        return type;
    }

    public TraAdvertisement type(CorDictionary corDictionary) {
        this.type = corDictionary;
        return this;
    }

    public void setType(CorDictionary corDictionary) {
        this.type = corDictionary;
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
