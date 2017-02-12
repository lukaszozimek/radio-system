package io.protone.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CrmContact entity.
 */
public class CrmContactDTO implements Serializable {

    private Long id;

    private String shortName;

    private String externalId1;

    private String externalId2;

    private LocalDate paymentDate;

    private String name;

    private Integer paymentDelay;

    private String vatNumber;

    private Long addresId;

    private Long countryId;

    private Long personId;

    private Long networkId;

    private Long rangeId;

    private Long sizeId;

    private Long industryId;

    private Long areaId;

    private Long keeperId;

    private Long statusId;

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
    public String getExternalId1() {
        return externalId1;
    }

    public void setExternalId1(String externalId1) {
        this.externalId1 = externalId1;
    }
    public String getExternalId2() {
        return externalId2;
    }

    public void setExternalId2(String externalId2) {
        this.externalId2 = externalId2;
    }
    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getPaymentDelay() {
        return paymentDelay;
    }

    public void setPaymentDelay(Integer paymentDelay) {
        this.paymentDelay = paymentDelay;
    }
    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public Long getAddresId() {
        return addresId;
    }

    public void setAddresId(Long corAddressId) {
        this.addresId = corAddressId;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long corCountryId) {
        this.countryId = corCountryId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long corPersonId) {
        this.personId = corPersonId;
    }

    public Long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Long corNetworkId) {
        this.networkId = corNetworkId;
    }

    public Long getRangeId() {
        return rangeId;
    }

    public void setRangeId(Long corRangeId) {
        this.rangeId = corRangeId;
    }

    public Long getSizeId() {
        return sizeId;
    }

    public void setSizeId(Long corSizeId) {
        this.sizeId = corSizeId;
    }

    public Long getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Long traIndustryId) {
        this.industryId = traIndustryId;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long corAreaId) {
        this.areaId = corAreaId;
    }

    public Long getKeeperId() {
        return keeperId;
    }

    public void setKeeperId(Long corUserId) {
        this.keeperId = corUserId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long crmContactStatusId) {
        this.statusId = crmContactStatusId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CrmContactDTO crmContactDTO = (CrmContactDTO) o;

        if ( ! Objects.equals(id, crmContactDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CrmContactDTO{" +
            "id=" + id +
            ", shortName='" + shortName + "'" +
            ", externalId1='" + externalId1 + "'" +
            ", externalId2='" + externalId2 + "'" +
            ", paymentDate='" + paymentDate + "'" +
            ", name='" + name + "'" +
            ", paymentDelay='" + paymentDelay + "'" +
            ", vatNumber='" + vatNumber + "'" +
            '}';
    }
}
