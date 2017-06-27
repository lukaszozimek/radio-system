package io.protone.core.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CorDictionary.
 */
@Entity
@Table(name = "cor_dictionary_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CorDictionaryType implements Serializable {


    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 0, max = 50)
    @Id
    @Column(length = 50, nullable = false, unique = true)
    public String name;

    public String getName() {
        return name;
    }

    public CorDictionaryType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CorDictionaryType corModule = (CorDictionaryType) o;
        if (corModule.name == null || name == null) {
            return false;
        }
        return Objects.equals(name, corModule.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "CorDictionaryType{" +
            ", name='" + name + "'" +
            '}';
    }
}
