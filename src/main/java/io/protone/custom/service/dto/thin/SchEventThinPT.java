package io.protone.custom.service.dto.thin;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.protone.custom.service.dto.SchBlockPT;
import io.protone.domain.enumeration.SchStartTypeEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A DTO for the SchEvent entity.
 */
public class SchEventThinPT implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String name;

    private SchStartTypeEnum startType;

    private SchEventTypeEnum schEventType;

    private Long relativeDelay;

    private Long scheduledLength = 0L;

    private Long length = 0L;

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

    public SchStartTypeEnum getStartType() {
        return startType;
    }

    public void setStartType(SchStartTypeEnum startType) {
        this.startType = startType;
    }

    public Long getRelativeDelay() {
        return relativeDelay;
    }

    public void setRelativeDelay(Long relativeDelay) {
        this.relativeDelay = relativeDelay;
    }

    public Long getScheduledLength() {
        return scheduledLength;
    }

    public void setScheduledLength(Long scheduledLength) {
        this.scheduledLength = scheduledLength;
        this.length = scheduledLength;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {

        this.length = length;
        this.scheduledLength = length;
    }

    public SchEventTypeEnum getSchEventType() {
        return schEventType;
    }

    public void setSchEventType(SchEventTypeEnum schEventType) {
        this.schEventType = schEventType;
    }

    public SchEventThinPT id(Long id) {
        this.id = id;
        return this;
    }

    public SchEventThinPT name(String name) {
        this.name = name;
        return this;
    }

    public SchEventThinPT startType(SchStartTypeEnum startType) {
        this.startType = startType;
        return this;
    }

    public SchEventThinPT relativeDelay(Long relativeDelay) {
        this.relativeDelay = relativeDelay;
        return this;
    }

    public SchEventThinPT scheduledLength(Long scheduledLength) {
        this.scheduledLength = scheduledLength;
        this.length = scheduledLength;
        return this;
    }

    public SchEventThinPT length(Long length) {
        this.length = length;
        this.scheduledLength = length;
        return this;
    }

    public SchEventThinPT eventType(SchEventTypeEnum eventTypeEnum) {
        this.schEventType = eventTypeEnum;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchBlockPT)) return false;

        SchBlockPT that = (SchBlockPT) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getStartType() != that.getStartType()) return false;
        if (getRelativeDelay() != null ? !getRelativeDelay().equals(that.getRelativeDelay()) : that.getRelativeDelay() != null)
            return false;
        if (getScheduledLength() != null ? !getScheduledLength().equals(that.getScheduledLength()) : that.getScheduledLength() != null)
            return false;
        return getLength() != null ? !getLength().equals(that.getLength()) : that.getLength() != null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getStartType() != null ? getStartType().hashCode() : 0);
        result = 31 * result + (getRelativeDelay() != null ? getRelativeDelay().hashCode() : 0);
        result = 31 * result + (getScheduledLength() != null ? getScheduledLength().hashCode() : 0);
        result = 31 * result + (getLength() != null ? getLength().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SchBlockPT{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", startType=").append(startType);
        sb.append(", scheduledLength=").append(scheduledLength);
        sb.append('}');
        return sb.toString();
    }

    public enum SchEventTypeEnum {
        MUSIC_IMPORT("ET_MUSIC_IMPORT"),

        COMMERCIAL_IMPORT("ET_COMMERCIAL_IMPORT"),

        AUTOMATION("ET_AUTOMATION"),

        AUDIO("ET_AUDIO"),

        OTHER("ET_OTHER");

        private String value;

        SchEventTypeEnum(String value) {
            this.value = value;
        }

        @JsonCreator
        public static SchEventThinPT.SchEventTypeEnum fromValue(String text) {
            for (SchEventThinPT.SchEventTypeEnum b : SchEventThinPT.SchEventTypeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }
    }
}
