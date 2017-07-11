package io.protone.crm.api.dto.thin;


import io.protone.core.api.dto.CorDictionaryDTO;
import io.protone.core.api.dto.CoreAddressDTO;
import io.protone.core.api.dto.thin.CoreUserThinDTO;
import io.protone.crm.api.dto.CrmCustomerPersonDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * CrmContactDTO
 */

public class CrmContactThinDTO implements Serializable {
    private Long id = null;

    @NotNull
    private String name = null;

    @NotNull
    private String shortName = null;

    private String externalId1 = null;

    private String externalId2 = null;

    private CorDictionaryDTO area = null;

    private CorDictionaryDTO industry = null;

    private Integer paymentDelay = null;

    private CorDictionaryDTO range = null;

    private CorDictionaryDTO size = null;

    private String vatNumber = null;

    private CoreAddressDTO addres = null;

    private CoreUserThinDTO account = null;

    private CrmCustomerPersonDTO person;


    private String publicUrl = null;


    public CrmContactThinDTO area(CorDictionaryDTO area) {
        this.area = area;
        return this;
    }


    /**
     * Get area
     *
     * @return area
     **/
    @ApiModelProperty(value = "")
    public CorDictionaryDTO getArea() {
        return area;
    }

    public void setArea(CorDictionaryDTO area) {
        this.area = area;
    }

    public CrmContactThinDTO id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     **/
    @ApiModelProperty(value = "")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CrmContactThinDTO idNumber1(String idNumber1) {
        this.externalId1 = idNumber1;
        return this;
    }

    @ApiModelProperty(value = "")
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public CrmContactThinDTO shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    @ApiModelProperty(value = "")
    public Integer getPaymentDelay() {
        return paymentDelay;
    }

    public void setPaymentDelay(Integer paymentDelay) {
        this.paymentDelay = paymentDelay;
    }

    public CrmContactThinDTO paymentDelay(Integer paymentDelay) {
        this.paymentDelay = paymentDelay;
        return this;
    }

    /**
     * Get externalId1
     *
     * @return externalId1
     **/
    @ApiModelProperty(value = "")
    public String getExternalId1() {
        return externalId1;
    }

    public void setExternalId1(String externalId1) {
        this.externalId1 = externalId1;
    }

    public CrmContactThinDTO idNumber2(String idNumber2) {
        this.externalId2 = idNumber2;
        return this;
    }

    /**
     * Get externalId2
     *
     * @return externalId2
     **/
    @ApiModelProperty(value = "")
    public String getExternalId2() {
        return externalId2;
    }

    public void setExternalId2(String externalId2) {
        this.externalId2 = externalId2;
    }

    public CrmContactThinDTO industry(CorDictionaryDTO industry) {
        this.industry = industry;
        return this;
    }

    /**
     * Get industry
     *
     * @return industry
     **/
    @ApiModelProperty(value = "")
    public CorDictionaryDTO getIndustry() {
        return industry;
    }

    public void setIndustry(CorDictionaryDTO industry) {
        this.industry = industry;
    }

    public CrmContactThinDTO name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get name
     *
     * @return name
     **/
    @ApiModelProperty(required = true, value = "")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CrmContactThinDTO range(CorDictionaryDTO range) {
        this.range = range;
        return this;
    }

    /**
     * Get range
     *
     * @return range
     **/
    @ApiModelProperty(value = "")
    public CorDictionaryDTO getRange() {
        return range;
    }

    public void setRange(CorDictionaryDTO range) {
        this.range = range;
    }

    public CrmContactThinDTO size(CorDictionaryDTO size) {
        this.size = size;
        return this;
    }

    /**
     * Get size
     *
     * @return size
     **/
    @ApiModelProperty(value = "")
    public CorDictionaryDTO getSize() {
        return size;
    }

    public void setSize(CorDictionaryDTO size) {
        this.size = size;
    }

    public CrmContactThinDTO vatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
        return this;
    }

    /**
     * Get vatNumber
     *
     * @return vatNumber
     **/
    @ApiModelProperty(value = "")
    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public CrmContactThinDTO addres(CoreAddressDTO adress) {
        this.addres = adress;
        return this;
    }

    /**
     * Get addres
     *
     * @return addres
     **/
    @ApiModelProperty(value = "")
    public CoreAddressDTO getAddres() {
        return addres;
    }

    public void setAddres(CoreAddressDTO addres) {
        this.addres = addres;
    }

    public CrmContactThinDTO account(CoreUserThinDTO account) {
        this.account = account;
        return this;
    }

    /**
     * Get account
     *
     * @return account
     **/
    @ApiModelProperty(value = "")
    public CoreUserThinDTO getAccount() {
        return account;
    }

    public void setAccount(CoreUserThinDTO account) {
        this.account = account;
    }

    public CrmContactThinDTO person(CrmCustomerPersonDTO person) {
        this.person = person;
        return this;
    }

    public String getPublicUrl() {
        return publicUrl;
    }

    public CrmContactThinDTO avatar(String publicUrl) {
        this.publicUrl = publicUrl;
        return this;
    }

    public void setPublicUrl(String publicUrl) {
        this.publicUrl = publicUrl;
    }

    /**
     * Get person
     *
     * @return person
     **/
    @ApiModelProperty(value = "")
    public CrmCustomerPersonDTO getPerson() {
        return person;
    }

    public void setPerson(CrmCustomerPersonDTO person) {
        this.person = person;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CrmContactThinDTO crmContactDTO = (CrmContactThinDTO) o;
        return Objects.equals(this.area, crmContactDTO.area) &&
                Objects.equals(this.id, crmContactDTO.id) &&
                Objects.equals(this.externalId1, crmContactDTO.externalId1) &&
                Objects.equals(this.externalId2, crmContactDTO.externalId2) &&
                Objects.equals(this.industry, crmContactDTO.industry) &&
                Objects.equals(this.name, crmContactDTO.name) &&
                Objects.equals(this.range, crmContactDTO.range) &&
                Objects.equals(this.size, crmContactDTO.size) &&
                Objects.equals(this.vatNumber, crmContactDTO.vatNumber) &&
                Objects.equals(this.addres, crmContactDTO.addres) &&
                Objects.equals(this.account, crmContactDTO.account) &&
                Objects.equals(this.person, crmContactDTO.person) &&
                Objects.equals(this.shortName, crmContactDTO.shortName) &&
                Objects.equals(this.publicUrl, crmContactDTO.publicUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(area, id, externalId1, externalId2, industry, name, range, size, vatNumber, addres, account, person, shortName, publicUrl);
    }

    @Override
    public String toString() {
        return "CrmContactThinDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", externalId1='" + externalId1 + '\'' +
                ", externalId2='" + externalId2 + '\'' +
                ", area=" + area +
                ", industry=" + industry +
                ", paymentDelay=" + paymentDelay +
                ", range=" + range +
                ", size=" + size +
                ", vatNumber='" + vatNumber + '\'' +
                ", addres=" + addres +
                ", account=" + account +
                ", person=" + person +
                ", publicUrl='" + publicUrl + '\'' +
                '}';
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }


}

