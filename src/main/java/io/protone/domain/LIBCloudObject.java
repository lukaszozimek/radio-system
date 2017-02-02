package io.protone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import io.protone.domain.enumeration.LIBObjectTypeEnum;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public LIBCloudObject uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public LIBObjectTypeEnum getObjectType() {
        return objectType;
    }

    public LIBCloudObject objectType(LIBObjectTypeEnum objectType) {
        this.objectType = objectType;
        return this;
    }

    public void setObjectType(LIBObjectTypeEnum objectType) {
        this.objectType = objectType;
    }

    public String getOriginalName() {
        return originalName;
    }

    public LIBCloudObject originalName(String originalName) {
        this.originalName = originalName;
        return this;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getContentType() {
        return contentType;
    }

    public LIBCloudObject contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSize() {
        return size;
    }

    public LIBCloudObject size(Long size) {
        this.size = size;
        return this;
    }

    public void setSize(Long size) {
        this.size = size;
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

    public LIBCloudObject hash(String hash) {
        this.hash = hash;
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public LIBCloudObject createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
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
