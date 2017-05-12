package io.protone.custom.service.dto.thin;

import io.protone.custom.service.dto.SchBlockPT;
import io.protone.custom.service.dto.SchEmissionPT;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A DTO for the SchClock entity.
 */
public class SchClockThinPT implements Serializable {

    private Long id;

    private String shortName;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SchClockThinPT id(Long id) {
        this.id = id;
        return this;
    }

    private SchClockThinPT name(String name) {
        this.name = name;
        return this;
    }

    private SchClockThinPT shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchClockThinPT)) return false;

        SchClockThinPT that = (SchClockThinPT) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getShortName() != null ? !getShortName().equals(that.getShortName()) : that.getShortName() != null)
            return false;
        return getName() != null ? !getName().equals(that.getName()) : that.getName() != null;

    }


    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getShortName() != null ? getShortName().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SchPlaylistPT{");
        sb.append("id=").append(id);
        sb.append(", shortName=").append(shortName);
        sb.append(", channelId=").append(name);
        sb.append('}');
        return sb.toString();
    }
}
