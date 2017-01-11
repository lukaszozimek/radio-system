package io.protone.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the TRACustomer entity.
 */
public class TRACustomerDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String name;

    private Long size;

    private Long range;

    private String area;

    private String vatNumber;

    private String idNumber1;

    private String idNumber2;

    private Integer paymentDate;


    private Long industryId;
    
    private Long networkId;
    
    private Long accountId;
    
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
    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
    public Long getRange() {
        return range;
    }

    public void setRange(Long range) {
        this.range = range;
    }
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }
    public String getIdNumber1() {
        return idNumber1;
    }

    public void setIdNumber1(String idNumber1) {
        this.idNumber1 = idNumber1;
    }
    public String getIdNumber2() {
        return idNumber2;
    }

    public void setIdNumber2(String idNumber2) {
        this.idNumber2 = idNumber2;
    }
    public Integer getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Integer paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Long getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Long tRAIndustryId) {
        this.industryId = tRAIndustryId;
    }

    public Long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Long cORNetworkId) {
        this.networkId = cORNetworkId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long userId) {
        this.accountId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TRACustomerDTO tRACustomerDTO = (TRACustomerDTO) o;

        if ( ! Objects.equals(id, tRACustomerDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TRACustomerDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", size='" + size + "'" +
            ", range='" + range + "'" +
            ", area='" + area + "'" +
            ", vatNumber='" + vatNumber + "'" +
            ", idNumber1='" + idNumber1 + "'" +
            ", idNumber2='" + idNumber2 + "'" +
            ", paymentDate='" + paymentDate + "'" +
            '}';
    }
}
