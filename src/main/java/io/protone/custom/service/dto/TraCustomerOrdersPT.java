package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * TraCustomerOrdersPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class TraCustomerOrdersPT   {
  @JsonProperty("paid")
  private Boolean paid = null;

  @JsonProperty("paymentDay")
  private String paymentDay = null;

  @JsonProperty("order")
  private TraOrderPT order = null;

  public TraCustomerOrdersPT paid(Boolean paid) {
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

  public TraCustomerOrdersPT paymentDay(String paymentDay) {
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

  public TraCustomerOrdersPT order(TraOrderPT order) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TraCustomerOrdersPT traCustomerOrdersPT = (TraCustomerOrdersPT) o;
    return Objects.equals(this.paid, traCustomerOrdersPT.paid) &&
        Objects.equals(this.paymentDay, traCustomerOrdersPT.paymentDay) &&
        Objects.equals(this.order, traCustomerOrdersPT.order);
  }

  @Override
  public int hashCode() {
    return Objects.hash(paid, paymentDay, order);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TraCustomerOrdersPT {\n");

    sb.append("    paid: ").append(toIndentedString(paid)).append("\n");
    sb.append("    paymentDay: ").append(toIndentedString(paymentDay)).append("\n");
    sb.append("    order: ").append(toIndentedString(order)).append("\n");
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

