package io.protone.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CrmAccount entity.
 */
public class CrmAccountDTO implements Serializable {

    private Long id;

    private String shortName;

    private String externalId1;

    private String externalId2;

    private String name;

    private Integer paymentDelay;

    private String vatNumber;

    private Long personId;

    private Long addresId;

    private Long networkId;

    private Long discountId;

    private Long keeperId;

    private Long countryId;

    private Long rangeId;

    private Long sizeId;

    private Long industryId;

    private Long areaId;

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

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long corPersonId) {
        this.personId = corPersonId;
    }

    public Long getAddresId() {
        return addresId;
    }

    public void setAddresId(Long corAddressId) {
        this.addresId = corAddressId;
    }

    public Long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Long corNetworkId) {
        this.networkId = corNetworkId;
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long traDiscountId) {
        this.discountId = traDiscountId;
    }

    public Long getKeeperId() {
        return keeperId;
    }

    public void setKeeperId(Long corUserId) {
        this.keeperId = corUserId;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long corCountryId) {
        this.countryId = corCountryId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CrmAccountDTO crmAccountDTO = (CrmAccountDTO) o;

        if ( ! Objects.equals(id, crmAccountDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CrmAccountDTO{" +
            "id=" + id +
            ", shortName='" + shortName + "'" +
            ", externalId1='" + externalId1 + "'" +
            ", externalId2='" + externalId2 + "'" +
            ", name='" + name + "'" +
            ", paymentDelay='" + paymentDelay + "'" +
            ", vatNumber='" + vatNumber + "'" +
            '}';
    }
}
