package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * TraCustomerAdvertismentsPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class TraCustomerAdvertismentsPT   {
  @JsonProperty("area")
  private CoreAreaPT area = null;

  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("idNumber1")
  private String idNumber1 = null;

  @JsonProperty("idNumber2")
  private String idNumber2 = null;

  @JsonProperty("industry")
  private ConfIndustryPT industry = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("networkId")
  private Long networkId = null;

  @JsonProperty("paymentDate")
  private Integer paymentDate = null;

  @JsonProperty("range")
  private CoreRangePT range = null;

  @JsonProperty("size")
  private CoreSizePT size = null;

  @JsonProperty("vatNumber")
  private String vatNumber = null;

  @JsonProperty("mediaitem")
  private List<LibItemPT> mediaitem = new ArrayList<LibItemPT>();

  public TraCustomerAdvertismentsPT area(CoreAreaPT area) {
    this.area = area;
    return this;
  }

   /**
   * Get area
   * @return area
  **/
  @ApiModelProperty(value = "")
  public CoreAreaPT getArea() {
    return area;
  }

  public void setArea(CoreAreaPT area) {
    this.area = area;
  }

  public TraCustomerAdvertismentsPT id(Long id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public TraCustomerAdvertismentsPT idNumber1(String idNumber1) {
    this.idNumber1 = idNumber1;
    return this;
  }

   /**
   * Get idNumber1
   * @return idNumber1
  **/
  @ApiModelProperty(value = "")
  public String getIdNumber1() {
    return idNumber1;
  }

  public void setIdNumber1(String idNumber1) {
    this.idNumber1 = idNumber1;
  }

  public TraCustomerAdvertismentsPT idNumber2(String idNumber2) {
    this.idNumber2 = idNumber2;
    return this;
  }

   /**
   * Get idNumber2
   * @return idNumber2
  **/
  @ApiModelProperty(value = "")
  public String getIdNumber2() {
    return idNumber2;
  }

  public void setIdNumber2(String idNumber2) {
    this.idNumber2 = idNumber2;
  }

  public TraCustomerAdvertismentsPT industry(ConfIndustryPT industry) {
    this.industry = industry;
    return this;
  }

   /**
   * Get industry
   * @return industry
  **/
  @ApiModelProperty(value = "")
  public ConfIndustryPT getIndustry() {
    return industry;
  }

  public void setIndustry(ConfIndustryPT industry) {
    this.industry = industry;
  }

  public TraCustomerAdvertismentsPT name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(value = "")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public TraCustomerAdvertismentsPT networkId(Long networkId) {
    this.networkId = networkId;
    return this;
  }

   /**
   * Get networkId
   * @return networkId
  **/
  @ApiModelProperty(value = "")
  public Long getNetworkId() {
    return networkId;
  }

  public void setNetworkId(Long networkId) {
    this.networkId = networkId;
  }

  public TraCustomerAdvertismentsPT paymentDate(Integer paymentDate) {
    this.paymentDate = paymentDate;
    return this;
  }

   /**
   * Get paymentDate
   * @return paymentDate
  **/
  @ApiModelProperty(value = "")
  public Integer getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(Integer paymentDate) {
    this.paymentDate = paymentDate;
  }

  public TraCustomerAdvertismentsPT range(CoreRangePT range) {
    this.range = range;
    return this;
  }

   /**
   * Get range
   * @return range
  **/
  @ApiModelProperty(value = "")
  public CoreRangePT getRange() {
    return range;
  }

  public void setRange(CoreRangePT range) {
    this.range = range;
  }

  public TraCustomerAdvertismentsPT size(CoreSizePT size) {
    this.size = size;
    return this;
  }

   /**
   * Get size
   * @return size
  **/
  @ApiModelProperty(value = "")
  public CoreSizePT getSize() {
    return size;
  }

  public void setSize(CoreSizePT size) {
    this.size = size;
  }

  public TraCustomerAdvertismentsPT vatNumber(String vatNumber) {
    this.vatNumber = vatNumber;
    return this;
  }

   /**
   * Get vatNumber
   * @return vatNumber
  **/
  @ApiModelProperty(value = "")
  public String getVatNumber() {
    return vatNumber;
  }

  public void setVatNumber(String vatNumber) {
    this.vatNumber = vatNumber;
  }

  public TraCustomerAdvertismentsPT mediaitem(List<LibItemPT> mediaitem) {
    this.mediaitem = mediaitem;
    return this;
  }

  public TraCustomerAdvertismentsPT addMediaitemItem(LibItemPT mediaitemItem) {
    this.mediaitem.add(mediaitemItem);
    return this;
  }

   /**
   * Get mediaitem
   * @return mediaitem
  **/
  @ApiModelProperty(value = "")
  public List<LibItemPT> getMediaitem() {
    return mediaitem;
  }

  public void setMediaitem(List<LibItemPT> mediaitem) {
    this.mediaitem = mediaitem;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TraCustomerAdvertismentsPT traCustomerAdvertismentsPT = (TraCustomerAdvertismentsPT) o;
    return Objects.equals(this.area, traCustomerAdvertismentsPT.area) &&
        Objects.equals(this.id, traCustomerAdvertismentsPT.id) &&
        Objects.equals(this.idNumber1, traCustomerAdvertismentsPT.idNumber1) &&
        Objects.equals(this.idNumber2, traCustomerAdvertismentsPT.idNumber2) &&
        Objects.equals(this.industry, traCustomerAdvertismentsPT.industry) &&
        Objects.equals(this.name, traCustomerAdvertismentsPT.name) &&
        Objects.equals(this.networkId, traCustomerAdvertismentsPT.networkId) &&
        Objects.equals(this.paymentDate, traCustomerAdvertismentsPT.paymentDate) &&
        Objects.equals(this.range, traCustomerAdvertismentsPT.range) &&
        Objects.equals(this.size, traCustomerAdvertismentsPT.size) &&
        Objects.equals(this.vatNumber, traCustomerAdvertismentsPT.vatNumber) &&
        Objects.equals(this.mediaitem, traCustomerAdvertismentsPT.mediaitem);
  }

  @Override
  public int hashCode() {
    return Objects.hash(area, id, idNumber1, idNumber2, industry, name, networkId, paymentDate, range, size, vatNumber, mediaitem);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TraCustomerAdvertismentsPT {\n");

    sb.append("    area: ").append(toIndentedString(area)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    idNumber1: ").append(toIndentedString(idNumber1)).append("\n");
    sb.append("    idNumber2: ").append(toIndentedString(idNumber2)).append("\n");
    sb.append("    industry: ").append(toIndentedString(industry)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    networkId: ").append(toIndentedString(networkId)).append("\n");
    sb.append("    paymentDate: ").append(toIndentedString(paymentDate)).append("\n");
    sb.append("    range: ").append(toIndentedString(range)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
    sb.append("    vatNumber: ").append(toIndentedString(vatNumber)).append("\n");
    sb.append("    mediaitem: ").append(toIndentedString(mediaitem)).append("\n");
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

