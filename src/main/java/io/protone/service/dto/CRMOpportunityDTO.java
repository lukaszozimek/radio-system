package io.protone.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the CrmOpportunity entity.
 */
public class CrmOpportunityDTO implements Serializable {

    private Long id;

    private String name;

    private LocalDate lastTry;

    private LocalDate closeDate;

    private Integer probability;


    private Long networkId;

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
    public LocalDate getLastTry() {
        return lastTry;
    }

    public void setLastTry(LocalDate lastTry) {
        this.lastTry = lastTry;
    }
    public LocalDate getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
    }
    public Integer getProbability() {
        return probability;
    }

    public void setProbability(Integer probability) {
        this.probability = probability;
    }

    public Long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Long cORNetworkId) {
        this.networkId = cORNetworkId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CrmOpportunityDTO cRMOpportunityDTO = (CrmOpportunityDTO) o;

        if ( ! Objects.equals(id, cRMOpportunityDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CrmOpportunityDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", lastTry='" + lastTry + "'" +
            ", closeDate='" + closeDate + "'" +
            ", probability='" + probability + "'" +
            '}';
    }
}
