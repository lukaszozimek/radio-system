package io.protone.domain;

import io.protone.domain.enumeration.LibObjectTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A LibCloudObject.
 */
@Entity
@Table(name = "lib_cloud_object")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LibCloudObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "uuid", length = 100, nullable = false)
    private String uuid;

    @Enumerated(EnumType.STRING)
    @Column(name = "object_type")
    private LibObjectTypeEnum objectType;

    @NotNull
    @Size(max = 100)
    @Column(name = "original_name", length = 100, nullable = false)
    private String originalName;

    @NotNull
    @Size(max = 100)
    @Column(name = "content_type", length = 100, nullable = false)
    private String contentType;

    @NotNull
    @Column(name = "size", nullable = false)
    private Long size;

    @Column(name = "original")
    private Boolean original;

    @NotNull
    @Size(max = 100)
    @Column(name = "hash", length = 100, nullable = false)
    private String hash;

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    @ManyToOne
    private CorNetwork network;

    @ManyToOne
    private CorUser createdBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public LibCloudObject uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public LibObjectTypeEnum getObjectType() {
        return objectType;
    }

    public LibCloudObject objectType(LibObjectTypeEnum objectType) {
        this.objectType = objectType;
        return this;
    }

    public void setObjectType(LibObjectTypeEnum objectType) {
        this.objectType = objectType;
    }

    public String getOriginalName() {
        return originalName;
    }

    public LibCloudObject originalName(String originalName) {
        this.originalName = originalName;
        return this;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getContentType() {
        return contentType;
    }

    public LibCloudObject contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSize() {
        return size;
    }

    public LibCloudObject size(Long size) {
        this.size = size;
        return this;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Boolean isOriginal() {
        return original;
    }

    public LibCloudObject original(Boolean original) {
        this.original = original;
        return this;
    }

    public void setOriginal(Boolean original) {
        this.original = original;
    }

    public String getHash() {
        return hash;
    }

    public LibCloudObject hash(String hash) {
        this.hash = hash;
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public LibCloudObject createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public CorNetwork getNetwork() {
        return network;
    }

    public LibCloudObject network(CorNetwork corNetwork) {
        this.network = corNetwork;
        return this;
    }

    public void setNetwork(CorNetwork corNetwork) {
        this.network = corNetwork;
    }

    public CorUser getCreatedBy() {
        return createdBy;
    }

    public LibCloudObject createdBy(CorUser corUser) {
        this.createdBy = corUser;
        return this;
    }

    public void setCreatedBy(CorUser corUser) {
        this.createdBy = corUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LibCloudObject libCloudObject = (LibCloudObject) o;
        if (libCloudObject.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, libCloudObject.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LibCloudObject{" +
            "id=" + id +
            ", uuid='" + uuid + "'" +
            ", objectType='" + objectType + "'" +
            ", originalName='" + originalName + "'" +
            ", contentType='" + contentType + "'" +
            ", size='" + size + "'" +
            ", original='" + original + "'" +
            ", hash='" + hash + "'" +
            ", createDate='" + createDate + "'" +
            '}';
    }
}
