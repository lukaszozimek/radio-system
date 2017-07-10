package io.protone.crm.domain;

import io.protone.core.domain.CorNetwork;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Created by lukaszozimek on 28/06/2017.
 */
@Entity
@Table(name = "crm_discount")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CrmDiscount {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "valid_to")
    private LocalDate validTo;

    @Column(name = "discount")
    private Long discount;

    @ManyToOne
    private CorNetwork network;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public CrmDiscount validFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public CrmDiscount validTo(LocalDate validTo) {
        this.validTo = validTo;
        return this;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public CrmDiscount discount(Long discount) {
        this.discount = discount;
        return this;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public CrmDiscount network(CorNetwork corNetwork) {
        this.network = corNetwork;
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
        CrmDiscount traDiscount = (CrmDiscount) o;
        if (traDiscount.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, traDiscount.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TraDiscount{" +
            "id=" + id +
            ", validFrom='" + validFrom + "'" +
            ", validTo='" + validTo + "'" +
            ", discount='" + discount + "'" +
            '}';
    }
}
