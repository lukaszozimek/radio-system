package io.protone.scheduler.api.dto;

import java.util.List;


public class SchLogConfigurationDTO {

    private Long id;

    private String name;

    private String pattern;

    private String extension;

    private List<SchLogConfigurationColumnDTO> columnDTOList;

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

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public List<SchLogConfigurationColumnDTO> getColumnDTOList() {
        return columnDTOList;
    }

    public void setColumnDTOList(List<SchLogConfigurationColumnDTO> columnDTOList) {
        this.columnDTOList = columnDTOList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SchLogConfigurationDTO that = (SchLogConfigurationDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (pattern != null ? !pattern.equals(that.pattern) : that.pattern != null) return false;
        if (extension != null ? !extension.equals(that.extension) : that.extension != null) return false;
        return columnDTOList != null ? columnDTOList.equals(that.columnDTOList) : that.columnDTOList == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (pattern != null ? pattern.hashCode() : 0);
        result = 31 * result + (extension != null ? extension.hashCode() : 0);
        result = 31 * result + (columnDTOList != null ? columnDTOList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SchLogConfigurationDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pattern='" + pattern + '\'' +
                ", extension='" + extension + '\'' +
                ", columnDTOList=" + columnDTOList +
                '}';
    }
}

