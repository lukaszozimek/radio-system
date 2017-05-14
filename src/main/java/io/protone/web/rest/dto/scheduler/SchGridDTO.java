package io.protone.web.rest.dto.scheduler;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.SchClockThinDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.*;
/**
 * SchGridDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class SchGridDTO   {
  @JsonProperty("clocks")
  private Map<String, SchClockThinDTO> clocks = new HashMap<String, SchClockThinDTO>();

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

  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("shortName")
  private String shortName = null;

  public SchGridDTO clocks(Map<String, SchClockThinDTO> clocks) {
    this.clocks = clocks;
    return this;
  }

  public SchGridDTO putClocksItem(String key, SchClockThinDTO clocksItem) {
    this.clocks.put(key, clocksItem);
    return this;
  }

   /**
   * Get clocks
   * @return clocks
  **/
  @ApiModelProperty(value = "")
  public Map<String, SchClockThinDTO> getClocks() {
    return clocks;
  }

  public void setClocks(Map<String, SchClockThinDTO> clocks) {
    this.clocks = clocks;
  }

  public SchGridDTO dayOfWeek(DayOfWeekEnum dayOfWeek) {
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

  public SchGridDTO id(Long id) {
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

  public SchGridDTO name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull
  @Size(min=0,max=100)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public SchGridDTO shortName(String shortName) {
    this.shortName = shortName;
    return this;
  }

   /**
   * Get shortName
   * @return shortName
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull
  @Size(min=0,max=3)
  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SchGridDTO schGridDTO = (SchGridDTO) o;
    return Objects.equals(this.clocks, schGridDTO.clocks) &&
        Objects.equals(this.dayOfWeek, schGridDTO.dayOfWeek) &&
        Objects.equals(this.id, schGridDTO.id) &&
        Objects.equals(this.name, schGridDTO.name) &&
        Objects.equals(this.shortName, schGridDTO.shortName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clocks, dayOfWeek, id, name, shortName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SchGridDTO {\n");

    sb.append("    clocks: ").append(toIndentedString(clocks)).append("\n");
    sb.append("    dayOfWeek: ").append(toIndentedString(dayOfWeek)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    shortName: ").append(toIndentedString(shortName)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

