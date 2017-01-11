package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * LibraryPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class LibraryPT   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("prefix")
  private String prefix = null;

  @JsonProperty("name")
  private String name = null;

  /**
   * Gets or Sets type
   */
  public enum TypeEnum {
    IMAGE("LT_IMAGE"),

    AUDIO("LT_AUDIO"),

    VIDEO("LT_VIDEO"),

    FILE("LT_FILE");

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

  @JsonProperty("networkId")
  private Long networkId = null;

  @JsonProperty("channels")
  private List<CoreChannelPT> channels = new ArrayList<CoreChannelPT>();

  @JsonProperty("users")
  private List<CoreUserPT> users = new ArrayList<CoreUserPT>();

  @JsonProperty("description")
  private String description = null;

  public LibraryPT id(Long id) {
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

  public LibraryPT prefix(String prefix) {
    this.prefix = prefix;
    return this;
  }

   /**
   * Get prefix
   * @return prefix
  **/
  @ApiModelProperty(required = true, value = "")
  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public LibraryPT name(String name) {
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

  public LibraryPT type(TypeEnum type) {
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

  public LibraryPT networkId(Long networkId) {
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

  public LibraryPT channels(List<CoreChannelPT> channels) {
    this.channels = channels;
    return this;
  }

  public LibraryPT addChannelsItem(CoreChannelPT channelsItem) {
    this.channels.add(channelsItem);
    return this;
  }

   /**
   * Get channels
   * @return channels
  **/
  @ApiModelProperty(value = "")
  public List<CoreChannelPT> getChannels() {
    return channels;
  }

  public void setChannels(List<CoreChannelPT> channels) {
    this.channels = channels;
  }

  public LibraryPT users(List<CoreUserPT> users) {
    this.users = users;
    return this;
  }

  public LibraryPT addUsersItem(CoreUserPT usersItem) {
    this.users.add(usersItem);
    return this;
  }

   /**
   * Get users
   * @return users
  **/
  @ApiModelProperty(value = "")
  public List<CoreUserPT> getUsers() {
    return users;
  }

  public void setUsers(List<CoreUserPT> users) {
    this.users = users;
  }

  public LibraryPT description(String description) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LibraryPT libraryPT = (LibraryPT) o;
    return Objects.equals(this.id, libraryPT.id) &&
        Objects.equals(this.prefix, libraryPT.prefix) &&
        Objects.equals(this.name, libraryPT.name) &&
        Objects.equals(this.type, libraryPT.type) &&
        Objects.equals(this.networkId, libraryPT.networkId) &&
        Objects.equals(this.channels, libraryPT.channels) &&
        Objects.equals(this.users, libraryPT.users) &&
        Objects.equals(this.description, libraryPT.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, prefix, name, type, networkId, channels, users, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LibraryPT {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    prefix: ").append(toIndentedString(prefix)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    networkId: ").append(toIndentedString(networkId)).append("\n");
    sb.append("    channels: ").append(toIndentedString(channels)).append("\n");
    sb.append("    users: ").append(toIndentedString(users)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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

