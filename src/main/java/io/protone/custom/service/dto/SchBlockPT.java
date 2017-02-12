package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * SchBlockPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class SchBlockPT   {
  @JsonProperty("channelId")
  private Long channelId = null;

  @JsonProperty("dimDay")
  private Integer dimDay = null;

  @JsonProperty("dimHour")
  private Integer dimHour = null;

  @JsonProperty("dimMinute")
  private Integer dimMinute = null;

  @JsonProperty("dimMonth")
  private Integer dimMonth = null;

  @JsonProperty("dimSecond")
  private Integer dimSecond = null;

  @JsonProperty("dimYear")
  private Integer dimYear = null;

  @JsonProperty("endTime")
  private String endTime = null;

  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("length")
  private Long length = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("parentBlockId")
  private Long parentBlockId = null;

  @JsonProperty("relativeDelay")
  private Long relativeDelay = null;

  @JsonProperty("seq")
  private Integer seq = null;

  @JsonProperty("startTime")
  private String startTime = null;

  /**
   * Gets or Sets startType
   */
  public enum StartTypeEnum {
    FREE("ST_FREE"),

    RELATIVE("ST_RELATIVE"),

    ABSOLUTE("ST_ABSOLUTE");

    private String value;

    StartTypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static StartTypeEnum fromValue(String text) {
      for (StartTypeEnum b : StartTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("startType")
  private StartTypeEnum startType = null;

  @JsonProperty("templateId")
  private Long templateId = null;

  /**
   * Gets or Sets type
   */
  public enum TypeEnum {
    BAND("BT_BAND"),

    HOUR("BT_HOUR"),

    NEWS("BT_NEWS"),

    COMMERCIAL("BT_COMMERCIAL"),

    PROGRAM("BT_PROGRAM"),

    LIVE("BT_LIVE"),

    DAY("BT_DAY"),

    OTHER("BT_OTHER");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeEnum fromValue(String text) {
      for (TypeEnum b : TypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("type")
  private TypeEnum type = null;

  @JsonProperty("emission")
  private List<SchEmissionPT> emission = new ArrayList<SchEmissionPT>();

  @JsonProperty("price")
  private CorePricePT price = null;

  public SchBlockPT channelId(Long channelId) {
    this.channelId = channelId;
    return this;
  }

   /**
   * Get channelId
   * @return channelId
  **/
  @ApiModelProperty(value = "")
  public Long getChannelId() {
    return channelId;
  }

  public void setChannelId(Long channelId) {
    this.channelId = channelId;
  }

  public SchBlockPT dimDay(Integer dimDay) {
    this.dimDay = dimDay;
    return this;
  }

   /**
   * Get dimDay
   * @return dimDay
  **/
  @ApiModelProperty(required = true, value = "")
  public Integer getDimDay() {
    return dimDay;
  }

  public void setDimDay(Integer dimDay) {
    this.dimDay = dimDay;
  }

  public SchBlockPT dimHour(Integer dimHour) {
    this.dimHour = dimHour;
    return this;
  }

   /**
   * Get dimHour
   * @return dimHour
  **/
  @ApiModelProperty(required = true, value = "")
  public Integer getDimHour() {
    return dimHour;
  }

  public void setDimHour(Integer dimHour) {
    this.dimHour = dimHour;
  }

  public SchBlockPT dimMinute(Integer dimMinute) {
    this.dimMinute = dimMinute;
    return this;
  }

   /**
   * Get dimMinute
   * @return dimMinute
  **/
  @ApiModelProperty(required = true, value = "")
  public Integer getDimMinute() {
    return dimMinute;
  }

  public void setDimMinute(Integer dimMinute) {
    this.dimMinute = dimMinute;
  }

  public SchBlockPT dimMonth(Integer dimMonth) {
    this.dimMonth = dimMonth;
    return this;
  }

   /**
   * Get dimMonth
   * @return dimMonth
  **/
  @ApiModelProperty(required = true, value = "")
  public Integer getDimMonth() {
    return dimMonth;
  }

  public void setDimMonth(Integer dimMonth) {
    this.dimMonth = dimMonth;
  }

  public SchBlockPT dimSecond(Integer dimSecond) {
    this.dimSecond = dimSecond;
    return this;
  }

   /**
   * Get dimSecond
   * @return dimSecond
  **/
  @ApiModelProperty(required = true, value = "")
  public Integer getDimSecond() {
    return dimSecond;
  }

  public void setDimSecond(Integer dimSecond) {
    this.dimSecond = dimSecond;
  }

  public SchBlockPT dimYear(Integer dimYear) {
    this.dimYear = dimYear;
    return this;
  }

   /**
   * Get dimYear
   * @return dimYear
  **/
  @ApiModelProperty(required = true, value = "")
  public Integer getDimYear() {
    return dimYear;
  }

  public void setDimYear(Integer dimYear) {
    this.dimYear = dimYear;
  }

  public SchBlockPT endTime(String endTime) {
    this.endTime = endTime;
    return this;
  }

   /**
   * Get endTime
   * @return endTime
  **/
  @ApiModelProperty(required = true, value = "")
  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public SchBlockPT id(Long id) {
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

  public SchBlockPT length(Long length) {
    this.length = length;
    return this;
  }

   /**
   * Get length
   * @return length
  **/
  @ApiModelProperty(required = true, value = "")
  public Long getLength() {
    return length;
  }

  public void setLength(Long length) {
    this.length = length;
  }

  public SchBlockPT name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(required = true, value = "")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public SchBlockPT parentBlockId(Long parentBlockId) {
    this.parentBlockId = parentBlockId;
    return this;
  }

   /**
   * Get parentBlockId
   * @return parentBlockId
  **/
  @ApiModelProperty(value = "")
  public Long getParentBlockId() {
    return parentBlockId;
  }

  public void setParentBlockId(Long parentBlockId) {
    this.parentBlockId = parentBlockId;
  }

  public SchBlockPT relativeDelay(Long relativeDelay) {
    this.relativeDelay = relativeDelay;
    return this;
  }

   /**
   * Get relativeDelay
   * @return relativeDelay
  **/
  @ApiModelProperty(value = "")
  public Long getRelativeDelay() {
    return relativeDelay;
  }

  public void setRelativeDelay(Long relativeDelay) {
    this.relativeDelay = relativeDelay;
  }

  public SchBlockPT seq(Integer seq) {
    this.seq = seq;
    return this;
  }

   /**
   * Get seq
   * @return seq
  **/
  @ApiModelProperty(required = true, value = "")
  public Integer getSeq() {
    return seq;
  }

  public void setSeq(Integer seq) {
    this.seq = seq;
  }

  public SchBlockPT startTime(String startTime) {
    this.startTime = startTime;
    return this;
  }

   /**
   * Get startTime
   * @return startTime
  **/
  @ApiModelProperty(required = true, value = "")
  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public SchBlockPT startType(StartTypeEnum startType) {
    this.startType = startType;
    return this;
  }

   /**
   * Get startType
   * @return startType
  **/
  @ApiModelProperty(required = true, value = "")
  public StartTypeEnum getStartType() {
    return startType;
  }

  public void setStartType(StartTypeEnum startType) {
    this.startType = startType;
  }

  public SchBlockPT templateId(Long templateId) {
    this.templateId = templateId;
    return this;
  }

   /**
   * Get templateId
   * @return templateId
  **/
  @ApiModelProperty(value = "")
  public Long getTemplateId() {
    return templateId;
  }

  public void setTemplateId(Long templateId) {
    this.templateId = templateId;
  }

  public SchBlockPT type(TypeEnum type) {
    this.type = type;
    return this;
  }

   /**
   * Get type
   * @return type
  **/
  @ApiModelProperty(required = true, value = "")
  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public SchBlockPT emission(List<SchEmissionPT> emission) {
    this.emission = emission;
    return this;
  }

  public SchBlockPT addEmissionItem(SchEmissionPT emissionItem) {
    this.emission.add(emissionItem);
    return this;
  }

   /**
   * Get emission
   * @return emission
  **/
  @ApiModelProperty(value = "")
  public List<SchEmissionPT> getEmission() {
    return emission;
  }

  public void setEmission(List<SchEmissionPT> emission) {
    this.emission = emission;
  }

  public SchBlockPT price(CorePricePT price) {
    this.price = price;
    return this;
  }

   /**
   * Get price
   * @return price
  **/
  @ApiModelProperty(value = "")
  public CorePricePT getPrice() {
    return price;
  }

  public void setPrice(CorePricePT price) {
    this.price = price;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SchBlockPT schBlockPT = (SchBlockPT) o;
    return Objects.equals(this.channelId, schBlockPT.channelId) &&
        Objects.equals(this.dimDay, schBlockPT.dimDay) &&
        Objects.equals(this.dimHour, schBlockPT.dimHour) &&
        Objects.equals(this.dimMinute, schBlockPT.dimMinute) &&
        Objects.equals(this.dimMonth, schBlockPT.dimMonth) &&
        Objects.equals(this.dimSecond, schBlockPT.dimSecond) &&
        Objects.equals(this.dimYear, schBlockPT.dimYear) &&
        Objects.equals(this.endTime, schBlockPT.endTime) &&
        Objects.equals(this.id, schBlockPT.id) &&
        Objects.equals(this.length, schBlockPT.length) &&
        Objects.equals(this.name, schBlockPT.name) &&
        Objects.equals(this.parentBlockId, schBlockPT.parentBlockId) &&
        Objects.equals(this.relativeDelay, schBlockPT.relativeDelay) &&
        Objects.equals(this.seq, schBlockPT.seq) &&
        Objects.equals(this.startTime, schBlockPT.startTime) &&
        Objects.equals(this.startType, schBlockPT.startType) &&
        Objects.equals(this.templateId, schBlockPT.templateId) &&
        Objects.equals(this.type, schBlockPT.type) &&
        Objects.equals(this.emission, schBlockPT.emission) &&
        Objects.equals(this.price, schBlockPT.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(channelId, dimDay, dimHour, dimMinute, dimMonth, dimSecond, dimYear, endTime, id, length, name, parentBlockId, relativeDelay, seq, startTime, startType, templateId, type, emission, price);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SchBlockPT {\n");

    sb.append("    channelId: ").append(toIndentedString(channelId)).append("\n");
    sb.append("    dimDay: ").append(toIndentedString(dimDay)).append("\n");
    sb.append("    dimHour: ").append(toIndentedString(dimHour)).append("\n");
    sb.append("    dimMinute: ").append(toIndentedString(dimMinute)).append("\n");
    sb.append("    dimMonth: ").append(toIndentedString(dimMonth)).append("\n");
    sb.append("    dimSecond: ").append(toIndentedString(dimSecond)).append("\n");
    sb.append("    dimYear: ").append(toIndentedString(dimYear)).append("\n");
    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    length: ").append(toIndentedString(length)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    parentBlockId: ").append(toIndentedString(parentBlockId)).append("\n");
    sb.append("    relativeDelay: ").append(toIndentedString(relativeDelay)).append("\n");
    sb.append("    seq: ").append(toIndentedString(seq)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    startType: ").append(toIndentedString(startType)).append("\n");
    sb.append("    templateId: ").append(toIndentedString(templateId)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    emission: ").append(toIndentedString(emission)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
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

