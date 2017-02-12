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

    private Long stageId;

    private Long keeperId;

    private Long contactId;

    private Long accountId;

    private Long leadId;

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

    public Long getStageId() {
        return stageId;
    }

    public void setStageId(Long crmStageId) {
        this.stageId = crmStageId;
    }

    public Long getKeeperId() {
        return keeperId;
    }

    public void setKeeperId(Long corUserId) {
        this.keeperId = corUserId;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long crmContactId) {
        this.contactId = crmContactId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long crmAccountId) {
        this.accountId = crmAccountId;
    }

    public Long getLeadId() {
        return leadId;
    }

    public void setLeadId(Long crmLeadId) {
        this.leadId = crmLeadId;
    }

    public Long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Long corNetworkId) {
        this.networkId = corNetworkId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CrmOpportunityDTO crmOpportunityDTO = (CrmOpportunityDTO) o;

        if ( ! Objects.equals(id, crmOpportunityDTO.id)) { return false; }

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
