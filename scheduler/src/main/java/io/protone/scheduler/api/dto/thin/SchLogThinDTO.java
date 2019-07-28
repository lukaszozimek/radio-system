package io.protone.scheduler.api.dto.thin;


import io.protone.scheduler.api.dto.SchLogConfigurationDTO;
import io.protone.scheduler.api.dto.base.SchBaseDTO;

import java.time.LocalDate;

public class SchLogThinDTO extends SchBaseDTO {

    private static final long serialVersionUID = 1L;


    private LocalDate date;

    private SchLogConfigurationDTO schLogConfiguration;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public SchLogThinDTO date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public SchLogConfigurationDTO getSchLogConfiguration() {
        return schLogConfiguration;
    }

    public void setSchLogConfiguration(SchLogConfigurationDTO schLogConfiguration) {
        this.schLogConfiguration = schLogConfiguration;
    }

    @Override
    public String toString() {
        return "SchLogThinDTO{" +
                "id=" + id +
                ", date=" + date +
                ", schLogConfiguration=" + schLogConfiguration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SchLogThinDTO that = (SchLogThinDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        return schLogConfiguration != null ? !schLogConfiguration.equals(that.schLogConfiguration) : that.schLogConfiguration != null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (schLogConfiguration != null ? schLogConfiguration.hashCode() : 0);
        return result;
    }
}

