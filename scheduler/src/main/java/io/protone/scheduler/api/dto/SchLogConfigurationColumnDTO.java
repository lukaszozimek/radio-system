package io.protone.scheduler.api.dto;

import io.protone.scheduler.domain.enumeration.LogColumnTypEnum;

/**
 * SchGridDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class SchLogConfigurationColumnDTO {

    private Long id;

    private LogColumnTypEnum name;

    private Integer length;


    private Integer columnSequence;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LogColumnTypEnum getName() {
        return name;
    }

    public void setName(LogColumnTypEnum name) {
        this.name = name;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }


    public Integer getColumnSequence() {
        return columnSequence;
    }

    public void setColumnSequence(Integer columnSequence) {
        this.columnSequence = columnSequence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SchLogConfigurationColumnDTO that = (SchLogConfigurationColumnDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (length != null ? !length.equals(that.length) : that.length != null) return false;
        if (columnSequence != null ? !columnSequence.equals(that.columnSequence) : that.columnSequence != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (length != null ? length.hashCode() : 0);
        result = 31 * result + (columnSequence != null ? columnSequence.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SchLogConfigurationColumnDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", length=" + length +
                ", columnSequence=" + columnSequence +
                '}';
    }
}

