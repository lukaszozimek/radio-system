package io.protone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.protone.domain.enumeration.LibItemStateEnum;
import io.protone.domain.enumeration.LibItemTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

/**
 * A LibMediaItem.
 */
@Entity
@Table(name = "lib_media_item", uniqueConstraints =
@UniqueConstraint(columnNames = {"idx","library_id", "network_id"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LibMediaItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @PodamExclude
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
    private LibItemTypeEnum itemType;

    @NotNull
    @Column(name = "length", nullable = false)
    private Double length;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private LibItemStateEnum state;

    @Column(name = "command")
    private String command;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @PodamExclude
    private LibLibrary library;

    @ManyToOne
    @PodamExclude
    private LibLabel label;

    @ManyToOne
    @PodamExclude
    private LibArtist artist;

    @ManyToOne
    @PodamExclude
    private LibAlbum album;

    @ManyToOne
    @PodamExclude
    private LibTrack track;

    @ManyToOne
    @PodamExclude
    private CorNetwork network;

    @PodamExclude
    @OneToMany(mappedBy = "author",fetch = EAGER)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CorPerson> authors = new HashSet<>();

    @PodamExclude
    @OneToMany(mappedBy = "composer",fetch = EAGER)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CorPerson> composers = new HashSet<>();

    @PodamExclude
    @OneToMany(mappedBy = "mediaItem",fetch = EAGER)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LibMarker> markers = new HashSet<>();

    @PodamExclude
    @OneToMany(mappedBy = "tags",fetch = EAGER)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CorTag> tags = new HashSet<>();

    @PodamExclude
    @OneToMany(mappedBy = "libItemPropertyValue",fetch = EAGER)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CorPropertyValue> properites = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdx() {
        return idx;
    }

    public LibMediaItem idx(String idx) {
        this.idx = idx;
        return this;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getName() {
        return name;
    }

    public LibMediaItem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LibItemTypeEnum getItemType() {
        return itemType;
    }

    public LibMediaItem itemType(LibItemTypeEnum itemType) {
        this.itemType = itemType;
        return this;
    }

    public void setItemType(LibItemTypeEnum itemType) {
        this.itemType = itemType;
    }

    public Double getLength() {
        return length;
    }

    public LibMediaItem length(Double length) {
        this.length = length;
        return this;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public LibItemStateEnum getState() {
        return state;
    }

    public LibMediaItem state(LibItemStateEnum state) {
        this.state = state;
        return this;
    }

    public void setState(LibItemStateEnum state) {
        this.state = state;
    }

    public String getCommand() {
        return command;
    }

    public LibMediaItem command(String command) {
        this.command = command;
        return this;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getDescription() {
        return description;
    }

    public LibMediaItem description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LibLibrary getLibrary() {
        return library;
    }

    public LibMediaItem library(LibLibrary libLibrary) {
        this.library = libLibrary;
        return this;
    }

    public void setLibrary(LibLibrary libLibrary) {
        this.library = libLibrary;
    }

    public LibLabel getLabel() {
        return label;
    }

    public LibMediaItem label(LibLabel libLabel) {
        this.label = libLabel;
        return this;
    }

    public void setLabel(LibLabel libLabel) {
        this.label = libLabel;
    }

    public LibArtist getArtist() {
        return artist;
    }

    public LibMediaItem artist(LibArtist libArtist) {
        this.artist = libArtist;
        return this;
    }

    public void setArtist(LibArtist libArtist) {
        this.artist = libArtist;
    }

    public LibAlbum getAlbum() {
        return album;
    }

    public LibMediaItem album(LibAlbum libAlbum) {
        this.album = libAlbum;
        return this;
    }

    public void setAlbum(LibAlbum libAlbum) {
        this.album = libAlbum;
    }

    public LibTrack getTrack() {
        return track;
    }

    public LibMediaItem track(LibTrack libTrack) {
        this.track = libTrack;
        return this;
    }

    public void setTrack(LibTrack libTrack) {
        this.track = libTrack;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public LibMediaItem network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public Set<CorPerson> getAuthors() {
        return authors;
    }

    public LibMediaItem authors(Set<CorPerson> corPeople) {
        this.authors = corPeople;
        return this;
    }

    public LibMediaItem addAuthor(CorPerson corPerson) {
        this.authors.add(corPerson);
        corPerson.setAuthor(this);
        return this;
    }

    public LibMediaItem removeAuthor(CorPerson corPerson) {
        this.authors.remove(corPerson);
        corPerson.setAuthor(null);
        return this;
    }

    public void setAuthors(Set<CorPerson> corPeople) {
        this.authors = corPeople;
    }

    public Set<CorPerson> getComposers() {
        return composers;
    }

    public LibMediaItem composers(Set<CorPerson> corPeople) {
        this.composers = corPeople;
        return this;
    }

    public LibMediaItem addComposer(CorPerson corPerson) {
        this.composers.add(corPerson);
        corPerson.setComposer(this);
        return this;
    }

    public LibMediaItem removeComposer(CorPerson corPerson) {
        this.composers.remove(corPerson);
        corPerson.setComposer(null);
        return this;
    }

    public void setComposers(Set<CorPerson> corPeople) {
        this.composers = corPeople;
    }

    public Set<LibMarker> getMarkers() {
        return markers;
    }

    public LibMediaItem markers(Set<LibMarker> libMarkers) {
        this.markers = libMarkers;
        return this;
    }

    public LibMediaItem addMarkers(LibMarker libMarker) {
        this.markers.add(libMarker);
        libMarker.setMediaItem(this);
        return this;
    }

    public LibMediaItem removeMarkers(LibMarker libMarker) {
        this.markers.remove(libMarker);
        libMarker.setMediaItem(null);
        return this;
    }

    public void setMarkers(Set<LibMarker> libMarkers) {
        this.markers = libMarkers;
    }

    public Set<CorTag> getTags() {
        return tags;
    }

    public LibMediaItem tags(Set<CorTag> corTags) {
        this.tags = corTags;
        return this;
    }

    public LibMediaItem addTags(CorTag corTag) {
        this.tags.add(corTag);
        corTag.setTags(this);
        return this;
    }

    public LibMediaItem removeTags(CorTag corTag) {
        this.tags.remove(corTag);
        corTag.setTags(null);
        return this;
    }

    public void setTags(Set<CorTag> corTags) {
        this.tags = corTags;
    }

    public Set<CorPropertyValue> getProperites() {
        return properites;
    }

    public LibMediaItem properites(Set<CorPropertyValue> corPropertyValues) {
        this.properites = corPropertyValues;
        return this;
    }

    public LibMediaItem addProperites(CorPropertyValue corPropertyValue) {
        this.properites.add(corPropertyValue);
        corPropertyValue.setLibItemPropertyValue(this);
        return this;
    }

    public LibMediaItem removeProperites(CorPropertyValue corPropertyValue) {
        this.properites.remove(corPropertyValue);
        corPropertyValue.setLibItemPropertyValue(null);
        return this;
    }

    public void setProperites(Set<CorPropertyValue> corPropertyValues) {
        this.properites = corPropertyValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LibMediaItem libMediaItem = (LibMediaItem) o;
        if (libMediaItem.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, libMediaItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LibMediaItem{" +
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
