package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * TraInvoicePT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class TraInvoicePT   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("order")
  private TraOrderPT order = null;

  @JsonProperty("paid")
  private Boolean paid = null;

  @JsonProperty("paymentDay")
  private String paymentDay = null;

  public TraInvoicePT id(Long id) {
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

  public TraInvoicePT order(TraOrderPT order) {
    this.order = order;
    return this;
  }

   /**
   * Get order
   * @return order
  **/
  @ApiModelProperty(value = "")
  public TraOrderPT getOrder() {
    return order;
  }

  public void setOrder(TraOrderPT order) {
    this.order = order;
  }

  public TraInvoicePT paid(Boolean paid) {
    this.paid = paid;
    return this;
  }

   /**
   * Get paid
   * @return paid
  **/
  @ApiModelProperty(value = "")
  public Boolean getPaid() {
    return paid;
  }

  public void setPaid(Boolean paid) {
    this.paid = paid;
  }

  public TraInvoicePT paymentDay(String paymentDay) {
    this.paymentDay = paymentDay;
    return this;
  }

   /**
   * Get paymentDay
   * @return paymentDay
  **/
  @ApiModelProperty(value = "")
  public String getPaymentDay() {
    return paymentDay;
  }

  public void setPaymentDay(String paymentDay) {
    this.paymentDay = paymentDay;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TraInvoicePT traInvoicePT = (TraInvoicePT) o;
    return Objects.equals(this.id, traInvoicePT.id) &&
        Objects.equals(this.order, traInvoicePT.order) &&
        Objects.equals(this.paid, traInvoicePT.paid) &&
        Objects.equals(this.paymentDay, traInvoicePT.paymentDay);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, order, paid, paymentDay);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TraInvoicePT {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    order: ").append(toIndentedString(order)).append("\n");
    sb.append("    paid: ").append(toIndentedString(paid)).append("\n");
    sb.append("    paymentDay: ").append(toIndentedString(paymentDay)).append("\n");
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

