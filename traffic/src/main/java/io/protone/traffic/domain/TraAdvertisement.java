package io.protone.traffic.domain;

import io.protone.core.domain.AbstractAuditingEntity;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorNetwork;
import io.protone.crm.domain.CrmAccount;
import io.protone.library.domain.LibMediaItem;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A TraAdvertisement.
 */
@Entity
@Table(name = "tra_advertisement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TraAdvertisement extends AbstractAuditingEntity implements Serializable {

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

    @PodamExclude
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "lib_media_item_tra_advertisement",
            joinColumns = @JoinColumn(name = "tra_advertisement_id"),
            inverseJoinColumns = @JoinColumn(name = "lib_media_item_id")
    )
    private Set<LibMediaItem> libMediaItems = new HashSet<>();

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

    public TraAdvertisement name(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TraAdvertisement description(String description) {
        this.description = description;
        return this;
    }

    public Set<LibMediaItem> getMediaItem() {
        return libMediaItems;
    }

    public void setMediaItem(Set<LibMediaItem> libMediaItem) {
        this.libMediaItems = libMediaItem;
    }

    public TraAdvertisement mediaItem(Set<LibMediaItem> libMediaItem) {
        this.libMediaItems = libMediaItem;
        return this;
    }

    public CrmAccount getCustomer() {
        return customer;
    }

    public void setCustomer(CrmAccount crmAccount) {
        this.customer = crmAccount;
    }

    public TraAdvertisement customer(CrmAccount crmAccount) {
        this.customer = crmAccount;
        return this;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public TraAdvertisement network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public CorDictionary getIndustry() {
        return industry;
    }

    public void setIndustry(CorDictionary corDictionary) {
        this.industry = corDictionary;
    }

    public TraAdvertisement industry(CorDictionary corDictionary) {
        this.industry = corDictionary;
        return this;
    }

    public CorDictionary getType() {
        return type;
    }

    public void setType(CorDictionary corDictionary) {
        this.type = corDictionary;
    }

    public TraAdvertisement type(CorDictionary corDictionary) {
        this.type = corDictionary;
        return this;
    }

    public Set<LibMediaItem> getLibMediaItems() {
        return libMediaItems;
    }

    public void setLibMediaItems(Set<LibMediaItem> libMediaItems) {
        this.libMediaItems = libMediaItems;
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
