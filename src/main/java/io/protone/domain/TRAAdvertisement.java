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

    @OneToOne
    @JoinColumn(unique = true)
    private LIBMediaItem mediaItem;

    @ManyToOne
    private TRACustomer customer;

    @ManyToOne
    private TRAIndustry industry;

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

    public LIBMediaItem getMediaItem() {
        return mediaItem;
    }

    public TRAAdvertisement mediaItem(LIBMediaItem lIBMediaItem) {
        this.mediaItem = lIBMediaItem;
        return this;
    }

    public void setMediaItem(LIBMediaItem lIBMediaItem) {
        this.mediaItem = lIBMediaItem;
    }

    public TRACustomer getCustomer() {
        return customer;
    }

    public TRAAdvertisement customer(TRACustomer tRACustomer) {
        this.customer = tRACustomer;
        return this;
    }

    public void setCustomer(TRACustomer tRACustomer) {
        this.customer = tRACustomer;
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
