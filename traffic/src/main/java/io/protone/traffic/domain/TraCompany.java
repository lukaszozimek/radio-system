package io.protone.traffic.domain;

import io.protone.core.domain.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A TraInvoice.
 */
@Entity
@Table(name = "tra_company")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TraCompany extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @PodamExclude
    private Long id;

    @Column(name = "short_name", nullable = false)
    private String shortName;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "tax_id_1")
    private String taxId1;

    @Column(name = "tax_id_2")
    private String taxId2;

    @OneToOne(fetch = FetchType.EAGER)
    @PodamExclude
    @JoinColumn
    private CorAddress addres;

    @ManyToOne(fetch = FetchType.LAZY)
    @PodamExclude
    private CorChannel channel;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTaxId1() {
        return taxId1;
    }

    public void setTaxId1(String taxId1) {
        this.taxId1 = taxId1;
    }

    public String getTaxId2() {
        return taxId2;
    }

    public void setTaxId2(String taxId2) {
        this.taxId2 = taxId2;
    }

    public CorAddress getAddres() {
        return addres;
    }

    public void setAddres(CorAddress addres) {
        this.addres = addres;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public void setChannel(CorChannel channel) {
        this.channel = channel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TraCompany that = (TraCompany) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (shortName != null ? !shortName.equals(that.shortName) : that.shortName != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (taxId1 != null ? !taxId1.equals(that.taxId1) : that.taxId1 != null) return false;
        if (taxId2 != null ? !taxId2.equals(that.taxId2) : that.taxId2 != null) return false;
        if (addres != null ? !addres.equals(that.addres) : that.addres != null) return false;
        return channel != null ? channel.equals(that.channel) : that.channel == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (taxId1 != null ? taxId1.hashCode() : 0);
        result = 31 * result + (taxId2 != null ? taxId2.hashCode() : 0);
        result = 31 * result + (addres != null ? addres.hashCode() : 0);
        result = 31 * result + (channel != null ? channel.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TraCompany{" +
                "id=" + id +
                ", shortName='" + shortName + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", taxId1='" + taxId1 + '\'' +
                ", taxId2='" + taxId2 + '\'' +
                ", addres=" + addres +
                ", organization=" + channel +
                '}';
    }

    public TraCompany name(String defaultName) {
        this.name = defaultName;
        return this;
    }

    public TraCompany channel(CorChannel channel) {
        this.channel = channel;
        return this;
    }

    public TraCompany taxId1(String updatedTaxId1) {
        this.taxId1 = updatedTaxId1;
        return this;
    }

    public TraCompany taxId2(String updatedTaxId2) {
        this.taxId2 = updatedTaxId2;
        return this;
    }

    public TraCompany description(String updatedDescription) {
        this.description = updatedDescription;
        return this;
    }

    public TraCompany shortName(String updatedShortName) {
        this.shortName = updatedShortName;
        return this;
    }
}
