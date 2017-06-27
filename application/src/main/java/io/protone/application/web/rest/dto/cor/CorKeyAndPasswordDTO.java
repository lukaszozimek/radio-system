package io.protone.application.web.rest.dto.cor;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * CorKeyAndPasswordDTO
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class CorKeyAndPasswordDTO {
  @JsonProperty("key")
  private String key = null;

  @JsonProperty("newPassword")
  private String newPassword = null;

  public CorKeyAndPasswordDTO key(String key) {
    this.key = key;
    return this;
  }

   /**
   * Get key
   * @return key
  **/
  @ApiModelProperty(value = "")
  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public CorKeyAndPasswordDTO newPassword(String newPassword) {
    this.newPassword = newPassword;
    return this;
  }

   /**
   * Get newPassword
   * @return newPassword
  **/
  @ApiModelProperty(value = "")
  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CorKeyAndPasswordDTO corKeyAndPasswordDTO = (CorKeyAndPasswordDTO) o;
    return Objects.equals(this.key, corKeyAndPasswordDTO.key) &&
        Objects.equals(this.newPassword, corKeyAndPasswordDTO.newPassword);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, newPassword);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CorKeyAndPasswordDTO {\n");

    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    newPassword: ").append(toIndentedString(newPassword)).append("\n");
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

