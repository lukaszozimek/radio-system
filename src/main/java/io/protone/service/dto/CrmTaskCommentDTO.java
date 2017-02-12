package io.protone.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CrmTaskComment entity.
 */
public class CrmTaskCommentDTO implements Serializable {

    private Long id;

    private String comment;

    private Long taskCommentId;

    private Long createdById;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getTaskCommentId() {
        return taskCommentId;
    }

    public void setTaskCommentId(Long crmTaskId) {
        this.taskCommentId = crmTaskId;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long corUserId) {
        this.createdById = corUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CrmTaskCommentDTO crmTaskCommentDTO = (CrmTaskCommentDTO) o;

        if ( ! Objects.equals(id, crmTaskCommentDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CrmTaskCommentDTO{" +
            "id=" + id +
            ", comment='" + comment + "'" +
            '}';
    }
}
