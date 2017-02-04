package io.protone.domain;

import io.protone.domain.enumeration.LIBObjectTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A LIBCloudObject.
 */
@Entity
@Table(name = "lib_cloud_object")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LIBCloudObject implements Serializable {

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
    private LIBObjectTypeEnum objectType;

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

    @OneToOne
    @JoinColumn(unique = true)
    private CORNetwork network;

    @ManyToOne
    private User createdBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public LIBCloudObject uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public LIBObjectTypeEnum getObjectType() {
        return objectType;
    }

    public void setObjectType(LIBObjectTypeEnum objectType) {
        this.objectType = objectType;
    }

    public LIBCloudObject objectType(LIBObjectTypeEnum objectType) {
        this.objectType = objectType;
        return this;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public LIBCloudObject originalName(String originalName) {
        this.originalName = originalName;
        return this;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public LIBCloudObject contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public LIBCloudObject size(Long size) {
        this.size = size;
        return this;
    }

    public Boolean isOriginal() {
        return original;
    }

    public LIBCloudObject original(Boolean original) {
        this.original = original;
        return this;
    }

    public void setOriginal(Boolean original) {
        this.original = original;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public LIBCloudObject hash(String hash) {
        this.hash = hash;
        return this;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public LIBCloudObject createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public CORNetwork getNetwork() {
        return network;
    }

    public void setNetwork(CORNetwork cORNetwork) {
        this.network = cORNetwork;
    }

    public LIBCloudObject network(CORNetwork cORNetwork) {
        this.network = cORNetwork;
        return this;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User cORUser) {
        this.createdBy = cORUser;
    }

    public LIBCloudObject createdBy(User cORUser) {
        this.createdBy = cORUser;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LIBCloudObject lIBCloudObject = (LIBCloudObject) o;
        if (lIBCloudObject.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, lIBCloudObject.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LIBCloudObject{" +
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
