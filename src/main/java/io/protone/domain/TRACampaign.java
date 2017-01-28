package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A TRACampaign.
 */
@Entity
@Table(name = "tra_campaign")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TRACampaign implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private CORNetwork network;

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

    public TRACampaign name(String name) {
        this.name = name;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public TRACampaign startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public TRACampaign endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public Long getPrize() {
        return prize;
    }

    public void setPrize(Long prize) {
        this.prize = prize;
    }

    public TRACampaign prize(Long prize) {
        this.prize = prize;
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
        TRACampaign tRACampaign = (TRACampaign) o;
        if (tRACampaign.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tRACampaign.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TRACampaign{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", prize='" + prize + "'" +
            '}';
    }

    public TRACampaign network(CORNetwork cORNetwork) {
        this.network = cORNetwork;
        return this;
    }

    public CORNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CORNetwork network) {
        this.network = network;
    }
}
