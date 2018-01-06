package io.protone.library.domain;

import io.protone.core.domain.AbstractAuditingEntity;
import io.protone.core.domain.CorNetwork;
import io.protone.library.domain.enumeration.LibObjectTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A LibCloudObject.
 */
@Entity
@Table(name = "lib_cloud_object")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LibCloudObject extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
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

    public LibCloudObject uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public LibObjectTypeEnum getObjectType() {
        return objectType;
    }

    public void setObjectType(LibObjectTypeEnum objectType) {
        this.objectType = objectType;
    }

    public LibCloudObject objectType(LibObjectTypeEnum objectType) {
        this.objectType = objectType;
        return this;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public LibCloudObject originalName(String originalName) {
        this.originalName = originalName;
        return this;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public LibCloudObject contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public LibCloudObject size(Long size) {
        this.size = size;
        return this;
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

    public void setHash(String hash) {
        this.hash = hash;
    }

    public LibCloudObject hash(String hash) {
        this.hash = hash;
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
            '}';
    }
}
