package io.protone.web.rest.dto.cor;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CorDictionary entity.
 */
public class CorDictionaryDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private String corDictionaryType;

    private Long seqNumber;

    private String corModule;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getCorDictionaryType() {
        return corDictionaryType;
    }

    public void setCorDictionaryType(String corDictionaryType) {
        this.corDictionaryType = corDictionaryType;
    }
    public Long getSeqNumber() {
        return seqNumber;
    }

    public void setSeqNumber(Long seqNumber) {
        this.seqNumber = seqNumber;
    }
    public String getCorModule() {
        return corModule;
    }

    public void setCorModule(String corModule) {
        this.corModule = corModule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CorDictionaryDTO corDictionaryDTO = (CorDictionaryDTO) o;

        if ( ! Objects.equals(id, corDictionaryDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CorDictionaryDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", corDictionaryType='" + corDictionaryType + "'" +
            ", seqNumber='" + seqNumber + "'" +
            ", corModule='" + corModule + "'" +
            '}';
    }
}
