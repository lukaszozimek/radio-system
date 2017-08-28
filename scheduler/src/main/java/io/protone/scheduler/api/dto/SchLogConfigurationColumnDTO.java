package io.protone.scheduler.api.dto;

/**
 * SchGridDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class SchLogConfigurationColumnDTO {

    private Long id;

    private String name;

    private Integer lenght;

    private String delimiter;

    private Integer columnSequence;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLenght() {
        return lenght;
    }

    public void setLenght(Integer lenght) {
        this.lenght = lenght;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
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
        if (lenght != null ? !lenght.equals(that.lenght) : that.lenght != null) return false;
        if (delimiter != null ? !delimiter.equals(that.delimiter) : that.delimiter != null) return false;
        if (columnSequence != null ? !columnSequence.equals(that.columnSequence) : that.columnSequence != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lenght != null ? lenght.hashCode() : 0);
        result = 31 * result + (delimiter != null ? delimiter.hashCode() : 0);
        result = 31 * result + (columnSequence != null ? columnSequence.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SchLogConfigurationColumnDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lenght=" + lenght +
                ", delimiter='" + delimiter + '\'' +
                ", columnSequence=" + columnSequence +
                '}';
    }
}

