package io.protone.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.protone.core.domain.*;
import io.protone.library.domain.enumeration.LibItemStateEnum;
import io.protone.library.domain.enumeration.LibItemTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
@UniqueConstraint(columnNames = {"idx", "library_id", "channel_id"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LibMediaItem extends CorItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_type", nullable = false)
    private LibItemTypeEnum itemType;

    @NotNull
    @Column(name = "length", nullable = false)
    private Double length;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private LibItemStateEnum state;

    @Column(name = "command")
    private String command;


    @Column(name = "is_conctent_available")
    private Boolean contentAvailable;

    @ManyToOne
    @PodamExclude
    private LibMediaLibrary library;

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

    @PodamExclude
    @OneToMany(mappedBy = "author", fetch = EAGER)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CorPerson> authors = new HashSet<>();

    @PodamExclude
    @OneToMany(mappedBy = "composer", fetch = EAGER)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CorPerson> composers = new HashSet<>();

    @PodamExclude
    @OneToMany(mappedBy = "mediaItem", fetch = EAGER)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LibMarker> markers = new HashSet<>();

    @PodamExclude
    @OneToMany
    @JoinTable(
            name = "lib_media_item_cor_image_item",
            joinColumns = @JoinColumn(name = "lib_media_item_id"),
            inverseJoinColumns = @JoinColumn(name = "cor_image_item_id")
    )
    private Set<CorImageItem> imageItems = new HashSet<>();

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public LibMediaItem idx(String idx) {
        this.idx = idx;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LibMediaItem name(String name) {
        this.name = name;
        return this;
    }

    public LibItemTypeEnum getItemType() {
        return itemType;
    }

    public void setItemType(LibItemTypeEnum itemType) {
        this.itemType = itemType;
    }

    public LibMediaItem itemType(LibItemTypeEnum itemType) {
        this.itemType = itemType;
        return this;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public LibMediaItem length(Double length) {
        this.length = length;
        return this;
    }

    public LibItemStateEnum getState() {
        return state;
    }

    public void setState(LibItemStateEnum state) {
        this.state = state;
    }

    public LibMediaItem state(LibItemStateEnum state) {
        this.state = state;
        return this;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public LibMediaItem command(String command) {
        this.command = command;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LibMediaItem description(String description) {
        this.description = description;
        return this;
    }

    public LibMediaLibrary getLibrary() {
        return library;
    }

    public void setLibrary(LibMediaLibrary libMediaLibrary) {
        this.library = libMediaLibrary;
    }

    public LibMediaItem library(LibMediaLibrary libMediaLibrary) {
        this.library = libMediaLibrary;
        return this;
    }

    public LibLabel getLabel() {
        return label;
    }

    public void setLabel(LibLabel libLabel) {
        this.label = libLabel;
    }

    public LibMediaItem label(LibLabel libLabel) {
        this.label = libLabel;
        return this;
    }

    public LibArtist getArtist() {
        return artist;
    }

    public void setArtist(LibArtist libArtist) {
        this.artist = libArtist;
    }

    public LibMediaItem artist(LibArtist libArtist) {
        this.artist = libArtist;
        return this;
    }

    public LibAlbum getAlbum() {
        return album;
    }

    public void setAlbum(LibAlbum libAlbum) {
        this.album = libAlbum;
    }

    public LibMediaItem album(LibAlbum libAlbum) {
        this.album = libAlbum;
        return this;
    }

    public LibTrack getTrack() {
        return track;
    }

    public void setTrack(LibTrack libTrack) {
        this.track = libTrack;
    }

    public LibMediaItem track(LibTrack libTrack) {
        this.track = libTrack;
        return this;
    }


    public Set<CorPerson> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<CorPerson> corPeople) {
        this.authors = corPeople;
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

    public Set<CorPerson> getComposers() {
        return composers;
    }

    public void setComposers(Set<CorPerson> corPeople) {
        this.composers = corPeople;
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

    public Set<LibMarker> getMarkers() {
        return markers;
    }

    public void setMarkers(Set<LibMarker> libMarkers) {
        this.markers = libMarkers;
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

    public Set<CorTag> getTags() {
        return tags;
    }

    public void setTags(Set<CorTag> corTags) {
        this.tags = corTags;
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

    public Set<CorPropertyValue> getProperites() {
        return properites;
    }

    public void setProperites(Set<CorPropertyValue> corPropertyValues) {
        this.properites = corPropertyValues;
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

    public Boolean isContentAvailable() {
        return contentAvailable;
    }

    public void setContentAvailable(Boolean contentAvailable) {
        this.contentAvailable = contentAvailable;
    }

    public LibMediaItem contentAvailable(Boolean isContentAvailable) {
        this.contentAvailable = isContentAvailable;
        return this;
    }

    public Set<CorImageItem> getImageItems() {
        return imageItems;
    }

    public void setImageItems(Set<CorImageItem> imageItems) {
        this.imageItems = imageItems;
    }

    public CorChannel getChannel() {
        return channel;
    }

    public LibMediaItem channel(CorChannel channel) {
        this.channel = channel;
        return this;
    }

    public void setChannel(CorChannel channel) {
        this.channel = channel;
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
                ", idx='" + idx + '\'' +
                ", name='" + name + '\'' +
                ", itemType=" + itemType +
                ", length=" + length +
                ", state=" + state +
                ", command='" + command + '\'' +
                ", description='" + description + '\'' +
                ", library=" + library +
                ", label=" + label +
                ", artist=" + artist +
                ", album=" + album +
                ", track=" + track +
                ", authors=" + authors +
                ", composers=" + composers +
                ", markers=" + markers +
                ", tags=" + tags +
                ", properites=" + properites +
                ", imageItems=" + imageItems +
                '}';
    }

}
