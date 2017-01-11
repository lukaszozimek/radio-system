package io.protone.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.protone.domain.enumeration.LIBFileTypeEnum;

/**
 * A DTO for the LIBFileItem entity.
 */
public class LIBFileItemDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 15)
    private String idx;

    @NotNull
    @Size(max = 100)
    private String name;

    private LIBFileTypeEnum type;


    private Long libraryId;
    
    private Long parentFileId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public LIBFileTypeEnum getType() {
        return type;
    }

    public void setType(LIBFileTypeEnum type) {
        this.type = type;
    }

    public Long getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(Long lIBLibraryId) {
        this.libraryId = lIBLibraryId;
    }

    public Long getParentFileId() {
        return parentFileId;
    }

    public void setParentFileId(Long lIBFileItemId) {
        this.parentFileId = lIBFileItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LIBFileItemDTO lIBFileItemDTO = (LIBFileItemDTO) o;

        if ( ! Objects.equals(id, lIBFileItemDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LIBFileItemDTO{" +
            "id=" + id +
            ", idx='" + idx + "'" +
            ", name='" + name + "'" +
            ", type='" + type + "'" +
            '}';
    }
}
