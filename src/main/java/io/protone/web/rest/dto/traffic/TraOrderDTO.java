package io.protone.web.rest.dto.traffic;

import io.protone.custom.service.dto.SchEmissionPT;
import io.protone.web.rest.dto.cor.CorDictionaryDTO;
import io.protone.web.rest.dto.traffic.thin.TraAdvertisementThinDTO;
import io.protone.web.rest.dto.traffic.thin.TraCustomerThinDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * TraOrderDTO
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class TraOrderDTO {

    private Long calculatedPrize = null;

    private Long campaignId = null;

    @NotNull
    private TraCustomerThinDTO customerId = null;

    private LocalDate endDate = null;

    private Long id = null;

    @NotNull
    private String name = null;

    private LocalDate startDate = null;

    @NotNull
    private TraAdvertisementThinDTO advertismentId = null;

    private CorDictionaryDTO statusId = null;

    private List<SchEmissionPT> schEmissionPT = new ArrayList<SchEmissionPT>();

    private Long invoiceId = null;

    public TraOrderDTO calculatedPrize(Long calculatedPrize) {
        this.calculatedPrize = calculatedPrize;
        return this;
    }

    /**
     * Get calculatedPrize
     *
     * @return calculatedPrize
     **/
    @ApiModelProperty(value = "")
    public Long getCalculatedPrize() {
        return calculatedPrize;
    }

    public void setCalculatedPrize(Long calculatedPrize) {
        this.calculatedPrize = calculatedPrize;
    }

    public TraOrderDTO traCampaign(Long campaignId) {
        this.campaignId = campaignId;
        return this;
    }

    /**
     * Get campaignId
     *
     * @return campaignId
     **/
    @ApiModelProperty(value = "")
    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }


    public TraOrderDTO customerId(TraCustomerThinDTO customerId) {
        this.customerId = customerId;
        return this;
    }

    /**
     * Get customerId
     *
     * @return customerId
     **/
    @ApiModelProperty(value = "")
    public TraCustomerThinDTO getCustomerId() {
        return customerId;
    }

    public void setCustomerId(TraCustomerThinDTO customerId) {
        this.customerId = customerId;
    }

    public TraOrderDTO endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    /**
     * Get endDate
     *
     * @return endDate
     **/
    @ApiModelProperty(value = "")
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public TraOrderDTO id(Long id) {
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

    public TraOrderDTO name(String name) {
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

    public TraOrderDTO startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    /**
     * Get trainvoice
     *
     * @return status
     **/
    @ApiModelProperty(required = true, value = "")
    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long traInvoicePT) {
        this.invoiceId = traInvoicePT;
    }

    public TraOrderDTO invoiceId(Long traInvoicePT) {
        this.invoiceId = traInvoicePT;
        return this;
    }

    /**
     * Get status
     *
     * @return status
     **/
    @ApiModelProperty(required = true, value = "")
    public CorDictionaryDTO getStatusId() {
        return statusId;
    }

    public void setStatusId(CorDictionaryDTO traOrderStatus) {
        this.statusId = traOrderStatus;
    }

    public TraOrderDTO statusId(CorDictionaryDTO traOrderStatus) {
        this.statusId = traOrderStatus;
        return this;
    }

    /**
     * Get status
     *
     * @return status
     **/
    @ApiModelProperty(required = true, value = "")
    public TraAdvertisementThinDTO getAdvertismentId() {
        return advertismentId;
    }

    public void setAdvertismentId(TraAdvertisementThinDTO advertisment) {
        this.advertismentId = advertisment;
    }

    public TraOrderDTO advertismentId(TraAdvertisementThinDTO advertisment) {
        this.advertismentId = advertisment;
        return this;
    }

    /**
     * Get emissions
     *
     * @return status
     **/
    @ApiModelProperty(required = true, value = "")
    public List<SchEmissionPT> getEmissions() {
        return schEmissionPT;
    }

    public void setEmissions(List<SchEmissionPT> emissions) {
        this.schEmissionPT = emissions;
    }

    public TraOrderDTO addEmission(SchEmissionPT emission) {
        this.schEmissionPT.add(emission);
        return this;
    }

    public TraOrderDTO removeEmission(SchEmissionPT emission) {
        this.schEmissionPT.remove(emission);
        return this;
    }

    public TraOrderDTO emissions(List<SchEmissionPT> emissions) {
        this.schEmissionPT = emissions;
        return this;
    }

    /**
     * Get startDate
     *
     * @return startDate
     **/
    @ApiModelProperty(value = "")
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TraOrderDTO traOrderDTO = (TraOrderDTO) o;
        return Objects.equals(this.calculatedPrize, traOrderDTO.calculatedPrize) &&
            Objects.equals(this.campaignId, traOrderDTO.campaignId) &&
            Objects.equals(this.customerId, traOrderDTO.customerId) &&
            Objects.equals(this.endDate, traOrderDTO.endDate) &&
            Objects.equals(this.id, traOrderDTO.id) &&
            Objects.equals(this.name, traOrderDTO.name) &&
            Objects.equals(this.startDate, traOrderDTO.startDate) &&
            Objects.equals(this.schEmissionPT, traOrderDTO.schEmissionPT) &&
            Objects.equals(this.advertismentId, traOrderDTO.advertismentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(calculatedPrize, campaignId, customerId, endDate, id, name, startDate, schEmissionPT, advertismentId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TraOrderDTO {\n");

        sb.append("    calculatedPrize: ").append(toIndentedString(calculatedPrize)).append("\n");
        sb.append("    campaignId: ").append(toIndentedString(campaignId)).append("\n");
        sb.append("    customerId: ").append(toIndentedString(customerId)).append("\n");
        sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
        sb.append("    emissions: ").append(toIndentedString(schEmissionPT)).append("\n");
        sb.append("    advertisment: ").append(toIndentedString(advertismentId)).append("\n");

        sb.append("}");
        return sb.toString();
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

