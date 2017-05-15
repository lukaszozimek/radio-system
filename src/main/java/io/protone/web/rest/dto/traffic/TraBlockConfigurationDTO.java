package io.protone.web.rest.dto.traffic;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
/**
 * TraBlockConfigurationDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class TraBlockConfigurationDTO   {
  @JsonProperty("description")
  private String description = null;

  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("length")
  private Long length = null;

  /**
   * Gets or Sets dayOfWeek
   */
  public enum DayOfWeekEnum {
    MONDAY("MONDAY"),

    TUESDAY("TUESDAY"),

    WEDNESDAY("WEDNESDAY"),

    THURSDAY("THURSDAY"),

    FRIDAY("FRIDAY"),

    SATURDAY("SATURDAY"),

    SUNDAY("SUNDAY");

    private String value;

    DayOfWeekEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static DayOfWeekEnum fromValue(String text) {
      for (DayOfWeekEnum b : DayOfWeekEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("dayOfWeek")
  private DayOfWeekEnum dayOfWeek = null;

  @JsonProperty("seq")
  private Long seq = null;

  public TraBlockConfigurationDTO description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(value = "")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public TraBlockConfigurationDTO id(Long id) {
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

  public TraBlockConfigurationDTO name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public TraBlockConfigurationDTO length(Long length) {
    this.length = length;
    return this;
  }

   /**
   * Get length
   * @return length
  **/
  @ApiModelProperty(value = "")
  public Long getLength() {
    return length;
  }

  public void setLength(Long length) {
    this.length = length;
  }

  public TraBlockConfigurationDTO dayOfWeek(DayOfWeekEnum dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
    return this;
  }

   /**
   * Get dayOfWeek
   * @return dayOfWeek
  **/
  @ApiModelProperty(value = "")
  public DayOfWeekEnum getDayOfWeek() {
    return dayOfWeek;
  }

  public void setDayOfWeek(DayOfWeekEnum dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
  }

  public TraBlockConfigurationDTO seq(Long seq) {
    this.seq = seq;
    return this;
  }

   /**
   * Get seq
   * @return seq
  **/
  @ApiModelProperty(value = "")
  public Long getSeq() {
    return seq;
  }

  public void setSeq(Long seq) {
    this.seq = seq;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TraBlockConfigurationDTO traBlockConfigurationDTO = (TraBlockConfigurationDTO) o;
    return Objects.equals(this.description, traBlockConfigurationDTO.description) &&
        Objects.equals(this.id, traBlockConfigurationDTO.id) &&
        Objects.equals(this.name, traBlockConfigurationDTO.name) &&
        Objects.equals(this.length, traBlockConfigurationDTO.length) &&
        Objects.equals(this.dayOfWeek, traBlockConfigurationDTO.dayOfWeek) &&
        Objects.equals(this.seq, traBlockConfigurationDTO.seq);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, id, name, length, dayOfWeek, seq);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TraBlockConfigurationDTO {\n");

    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    length: ").append(toIndentedString(length)).append("\n");
    sb.append("    dayOfWeek: ").append(toIndentedString(dayOfWeek)).append("\n");
    sb.append("    seq: ").append(toIndentedString(seq)).append("\n");
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

