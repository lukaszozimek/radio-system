package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.protone.domain.enumeration.LibCounterTypeEnum;
import io.protone.domain.enumeration.LibObjectTypeEnum;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * LibraryPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class LibraryPT {
    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("counter")
    private Long counter = 0L;

    @JsonProperty("counterType")
    private LibCounterTypeEnum counterType;

    @JsonProperty("libraryType")
    private LibObjectTypeEnum libraryType;

    @JsonProperty("indexLength")
    private Integer indexLength = 8;

    @JsonProperty("shortcut")
    private String shortcut = null;

    @JsonProperty("prefix")
    private String prefix = null;
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

    public LibraryPT prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    /**
     * Get prefix
     *
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

    public LibraryPT type(TypeEnum type) {
        this.type = type;
        return this;
    }

    /**
     * Get type
     *
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
     *
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
     *
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
     *
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
     *
     * @return description
     **/
    @ApiModelProperty(value = "")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public LibCounterTypeEnum getCounterType() {
        return counterType;
    }

    public void setCounterType(LibCounterTypeEnum counterType) {
        this.counterType = counterType;
    }

    public LibObjectTypeEnum getLibraryType() {
        return libraryType;
    }

    public void setLibraryType(LibObjectTypeEnum libraryType) {
        this.libraryType = libraryType;
    }

    public Integer getIndexLength() {
        return indexLength;
    }

    public void setIndexLength(Integer indexLength) {
        this.indexLength = indexLength;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LibraryPT)) return false;

        LibraryPT libraryPT = (LibraryPT) o;

        if (getId() != null ? !getId().equals(libraryPT.getId()) : libraryPT.getId() != null) return false;
        if (getPrefix() != null ? !getPrefix().equals(libraryPT.getPrefix()) : libraryPT.getPrefix() != null)
            return false;
        if (getName() != null ? !getName().equals(libraryPT.getName()) : libraryPT.getName() != null) return false;
        if (getCounter() != null ? !getCounter().equals(libraryPT.getCounter()) : libraryPT.getCounter() != null)
            return false;
        if (getCounterType() != null ? !getCounterType().equals(libraryPT.getCounterType()) : libraryPT.getCounterType() != null)
            return false;
        if (getLibraryType() != null ? !getLibraryType().equals(libraryPT.getLibraryType()) : libraryPT.getLibraryType() != null)
            return false;
        if (getIndexLength() != null ? !getIndexLength().equals(libraryPT.getIndexLength()) : libraryPT.getIndexLength() != null)
            return false;
        if (getShortcut() != null ? !getShortcut().equals(libraryPT.getShortcut()) : libraryPT.getShortcut() != null)
            return false;
        if (getPrefix() != null ? !getPrefix().equals(libraryPT.getPrefix()) : libraryPT.getPrefix() != null)
            return false;
        if (getType() != libraryPT.getType()) return false;
        if (getNetworkId() != null ? !getNetworkId().equals(libraryPT.getNetworkId()) : libraryPT.getNetworkId() != null)
            return false;
        if (getChannels() != null ? !getChannels().equals(libraryPT.getChannels()) : libraryPT.getChannels() != null)
            return false;
        if (getUsers() != null ? !getUsers().equals(libraryPT.getUsers()) : libraryPT.getUsers() != null) return false;
        if (getDescription() != null ? !getDescription().equals(libraryPT.getDescription()) : libraryPT.getDescription() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getPrefix() != null ? getPrefix().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getCounter() != null ? getCounter().hashCode() : 0);
        result = 31 * result + (getCounterType() != null ? getCounterType().hashCode() : 0);
        result = 31 * result + (getLibraryType() != null ? getLibraryType().hashCode() : 0);
        result = 31 * result + (getIndexLength() != null ? getIndexLength().hashCode() : 0);
        result = 31 * result + (getShortcut() != null ? getShortcut().hashCode() : 0);
        result = 31 * result + (getPrefix() != null ? getPrefix().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + (getNetworkId() != null ? getNetworkId().hashCode() : 0);
        result = 31 * result + (getChannels() != null ? getChannels().hashCode() : 0);
        result = 31 * result + (getUsers() != null ? getUsers().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LibraryPT{" +
            "id=" + id +
            ", prefix='" + prefix + '\'' +
            ", name='" + name + '\'' +
            ", counter=" + counter +
            ", counterType='" + counterType + '\'' +
            ", libraryType='" + libraryType + '\'' +
            ", indexLength=" + indexLength +
            ", shortcut='" + shortcut + '\'' +
            ", prefix='" + prefix + '\'' +
            ", type=" + type +
            ", networkId=" + networkId +
            ", channels=" + channels +
            ", users=" + users +
            ", description='" + description + '\'' +
            '}';
    }

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

        @JsonCreator
        public static TypeEnum fromValue(String text) {
            for (TypeEnum b : TypeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }
    }
}
