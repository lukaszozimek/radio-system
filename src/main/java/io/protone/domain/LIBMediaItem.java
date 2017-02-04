package io.protone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.protone.domain.enumeration.LIBItemTypeEnum;

import io.protone.domain.enumeration.LIBItemStateEnum;

/**
 * A LIBMediaItem.
 */
@Entity
@Table(name = "lib_media_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LIBMediaItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 15)
    @Column(name = "idx", length = 15, nullable = false)
    private String idx;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "item_type", nullable = false)
    private LIBItemTypeEnum itemType;

    @NotNull
    @Column(name = "length", nullable = false)
    private Long length;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private LIBItemStateEnum state;

    @Column(name = "command")
    private String command;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private LibLibrary libLibrary;

    @OneToOne
    @JoinColumn(unique = true)
    private LIBAlbum album;

    @OneToOne
    @JoinColumn(unique = true)
    private LIBArtist artist;


    @OneToOne
    @JoinColumn(unique = true)
    private LIBLabel label;

    @OneToOne
    @JoinColumn(unique = true)
    private CORNetwork network;

    @OneToOne
    @JoinColumn(unique = true)
    private LibLibrary item;


    @OneToMany(mappedBy = "lIBMediaItem")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LIBMarker> markers = new HashSet<>();

    @OneToMany(mappedBy = "lIBMediaItem")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CORPropertyKey> properties = new HashSet<>();

    @OneToMany(mappedBy = "lIBMediaItem")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CORTag> tags = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdx() {
        return idx;
    }

    public LIBMediaItem idx(String idx) {
        this.idx = idx;
        return this;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getName() {
        return name;
    }

    public LIBMediaItem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LIBItemTypeEnum getItemType() {
        return itemType;
    }

    public LIBMediaItem itemType(LIBItemTypeEnum itemType) {
        this.itemType = itemType;
        return this;
    }

    public void setItemType(LIBItemTypeEnum itemType) {
        this.itemType = itemType;
    }

    public Long getLength() {
        return length;
    }

    public LIBMediaItem length(Long length) {
        this.length = length;
        return this;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public LIBItemStateEnum getState() {
        return state;
    }

    public LIBMediaItem state(LIBItemStateEnum state) {
        this.state = state;
        return this;
    }

    public void setState(LIBItemStateEnum state) {
        this.state = state;
    }

    public String getCommand() {
        return command;
    }

    public LIBMediaItem command(String command) {
        this.command = command;
        return this;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getDescription() {
        return description;
    }

    public LIBMediaItem description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LibLibrary getLibLibrary() {
        return libLibrary;
    }

    public LIBMediaItem libLibrary(LibLibrary libLibrary) {
        this.libLibrary = libLibrary;
        return this;
    }

    public void setLibLibrary(LibLibrary libLibrary) {
        this.libLibrary = libLibrary;
    }

    public LIBAlbum getAlbum() {
        return album;
    }

    public LIBMediaItem album(LIBAlbum lIBAlbum) {
        this.album = lIBAlbum;
        return this;
    }

    public void setAlbum(LIBAlbum lIBAlbum) {
        this.album = lIBAlbum;
    }

    public LIBArtist getArtist() {
        return artist;
    }

    public LIBMediaItem artist(LIBArtist lIBArtist) {
        this.artist = lIBArtist;
        return this;
    }

    public void setArtist(LIBArtist lIBArtist) {
        this.artist = lIBArtist;
    }

    public LIBLabel getComposer() {
        return label;
    }

    public LIBMediaItem composer(LIBLabel lIBLabel) {
        this.label = lIBLabel;
        return this;
    }

    public void setComposer(LIBLabel lIBLabel) {
        this.label = lIBLabel;
    }

    public CORNetwork getNetwork() {
        return network;
    }

    public LIBMediaItem network(CORNetwork cORNetwork) {
        this.network = cORNetwork;
        return this;
    }

    public void setNetwork(CORNetwork cORNetwork) {
        this.network = cORNetwork;
    }

    public LibLibrary getItem() {
        return item;
    }

    public LIBMediaItem item(LibLibrary libLibrary) {
        this.item = libLibrary;
        return this;
    }

    public void setItem(LibLibrary libLibrary) {
        this.item = libLibrary;
    }


    public Set<LIBMarker> getMarkers() {
        return markers;
    }

    public LIBMediaItem markers(Set<LIBMarker> lIBMarkers) {
        this.markers = lIBMarkers;
        return this;
    }

    public LIBMediaItem addMarkers(LIBMarker lIBMarker) {
        markers.add(lIBMarker);
        lIBMarker.setLIBMediaItem(this);
        return this;
    }

    public LIBMediaItem removeMarkers(LIBMarker lIBMarker) {
        markers.remove(lIBMarker);
        lIBMarker.setLIBMediaItem(null);
        return this;
    }

    public void setMarkers(Set<LIBMarker> lIBMarkers) {
        this.markers = lIBMarkers;
    }

    public Set<CORPropertyKey> getProperties() {
        return properties;
    }

    public LIBMediaItem properties(Set<CORPropertyKey> cORPropertyKeys) {
        this.properties = cORPropertyKeys;
        return this;
    }

    public LIBMediaItem addProperty(CORPropertyKey cORPropertyKey) {
        properties.add(cORPropertyKey);
        cORPropertyKey.setLIBMediaItem(this);
        return this;
    }

    public LIBMediaItem removeProperty(CORPropertyKey cORPropertyKey) {
        properties.remove(cORPropertyKey);
        cORPropertyKey.setLIBMediaItem(null);
        return this;
    }

    public void setProperties(Set<CORPropertyKey> cORPropertyKeys) {
        this.properties = cORPropertyKeys;
    }

    public Set<CORTag> getTags() {
        return tags;
    }

    public LIBMediaItem tags(Set<CORTag> cORTags) {
        this.tags = cORTags;
        return this;
    }

    public LIBMediaItem addTags(CORTag cORTag) {
        tags.add(cORTag);
        cORTag.setLIBMediaItem(this);
        return this;
    }

    public LIBMediaItem removeTags(CORTag cORTag) {
        tags.remove(cORTag);
        cORTag.setLIBMediaItem(null);
        return this;
    }

    public void setTags(Set<CORTag> cORTags) {
        this.tags = cORTags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LIBMediaItem lIBMediaItem = (LIBMediaItem) o;
        if (lIBMediaItem.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, lIBMediaItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LIBMediaItem{" +
            "id=" + id +
            ", idx='" + idx + "'" +
            ", name='" + name + "'" +
            ", itemType='" + itemType + "'" +
            ", length='" + length + "'" +
            ", state='" + state + "'" +
            ", command='" + command + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
