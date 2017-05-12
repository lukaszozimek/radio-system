package io.protone.custom.service.dto;

import io.protone.custom.service.dto.thin.SchGridThinPT;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by lukaszozimek on 17/04/2017.
 */
public class SchSchedulePT implements Serializable {

    private Long id;

    @NotNull
    private LocalDate date;

    private SchGridThinPT schGridPT;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public SchGridThinPT getSchGridPT() {
        return schGridPT;
    }

    public void setSchGridPT(SchGridThinPT schGridPT) {
        this.schGridPT = schGridPT;
    }

    public SchSchedulePT id(Long id) {
        this.id = id;
        return this;
    }

    public SchSchedulePT date(LocalDate date) {
        this.date = date;
        return this;
    }

    public SchSchedulePT clock(SchGridThinPT schClockPT) {
        this.schGridPT = schClockPT;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof io.protone.custom.service.dto.SchGridPT)) return false;

        io.protone.custom.service.dto.SchGridPT that = (io.protone.custom.service.dto.SchGridPT) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        return getSchGridPT() != null ? getSchGridPT().equals(that.getClocks()) : that.getClocks() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getSchGridPT() != null ? getSchGridPT().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SchGridPT{");
        sb.append("id=").append(id);
        sb.append(", date='").append(date).append('\'');
        sb.append(", schGridPT=").append(schGridPT);
        sb.append('}');
        return sb.toString();
    }


}

