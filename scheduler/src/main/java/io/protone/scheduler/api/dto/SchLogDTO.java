package io.protone.scheduler.api.dto;

import io.protone.library.api.dto.thin.LibFileItemThinDTO;
import io.protone.scheduler.domain.SchLog;

import java.time.LocalDate;
import java.util.Objects;

/**
 * SchGridDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class SchLogDTO {

    private static final long serialVersionUID = 1L;

    private Long id;

    private LocalDate date;

    private SchLogConfigurationDTO schLogConfiguration;

    private LibFileItemThinDTO libFileItem;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public SchLogDTO date(LocalDate date) {
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

    public LibFileItemThinDTO getLibFileItem() {
        return libFileItem;
    }

    public void setLibFileItem(LibFileItemThinDTO libFileItem) {
        this.libFileItem = libFileItem;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchLog emission = (SchLog) o;
        if (emission.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), emission.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }


    @Override
    public String toString() {
        return "SchLogDTO{" +
                "id=" + id +
                ", date=" + date +
                ", schLogConfiguration=" + schLogConfiguration +
                ", libFileItem=" + libFileItem +
                '}';
    }
}

