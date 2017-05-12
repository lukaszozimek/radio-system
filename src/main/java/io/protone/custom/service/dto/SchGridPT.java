package io.protone.custom.service.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.protone.custom.service.dto.thin.SchClockThinPT;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * A DTO for the SchGridPT entity.
 */
public class SchGridPT implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String name;

    @NotNull
    @Size(max = 3)
    private String shortName;

    @JsonProperty("dayOfWeek")
    private SchGridPT.DayOfWeekEnum dayOfWeek = null;

    /**
     * Gets or Sets resourceType
     */
    public enum DayOfWeekEnum {
        MONDAY("MONDAY"),

        TUESDAY("TUESDAY"),

        WEDNESDAY("WEDNESDAY"),

        THURSDAY("THURSDAY"),

        FRIDAY("FRIDAY"),

        SATURDAY("SATURDAY"),

        SUNDAY("SUNDAY");

        private String value;

        DayOfWeekEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static SchGridPT.DayOfWeekEnum fromValue(String text) {
            for (SchGridPT.DayOfWeekEnum b : SchGridPT.DayOfWeekEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    private Map<Integer,SchClockThinPT> clocks;

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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public DayOfWeekEnum getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeekEnum dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }


    public Map<Integer,SchClockThinPT> getClocks() {
        return clocks;
    }

    public void setClocks(Map<Integer,SchClockThinPT> clocks) {
        this.clocks = clocks;
    }

    public SchGridPT id(Long id) {
        this.id = id;
        return this;
    }

    public SchGridPT name(String name) {
        this.name = name;
        return this;
    }

    public SchGridPT shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public SchGridPT dayOfWeek(DayOfWeekEnum dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }

    public SchGridPT clock(Map<Integer,SchClockThinPT> schClockPT) {
        this.clocks = schClockPT;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchGridPT)) return false;

        SchGridPT that = (SchGridPT) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getShortName() != null ? !getShortName().equals(that.getShortName()) : that.getShortName() != null)
            return false;
        return getClocks() != null ? getClocks().equals(that.getClocks()) : that.getClocks() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getShortName() != null ? getShortName().hashCode() : 0);
        result = 31 * result + (getClocks() != null ? getClocks().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SchGridPT{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", shortName=").append(shortName);
        sb.append(", clocks=").append(clocks);
        sb.append(", dayOfWeek=").append(dayOfWeek);
        sb.append('}');
        return sb.toString();
    }
}
