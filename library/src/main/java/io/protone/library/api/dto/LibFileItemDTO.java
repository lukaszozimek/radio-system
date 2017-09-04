package io.protone.library.api.dto;

/**
 * Created by lukaszozimek on 29/08/2017.
 */
public class LibFileItemDTO {
    private Long id;
    private String idx;

    private String name;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LibFileItemDTO that = (LibFileItemDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (idx != null ? !idx.equals(that.idx) : that.idx != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (idx != null ? idx.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LibFileItemDTO{" +
                "id=" + id +
                ", idx='" + idx + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
