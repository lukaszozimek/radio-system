package io.protone.scheduler.api.dto;

/**
 * SchTimeParamsDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class SchConfigurationTimeParamsDTO {

    private static final long serialVersionUID = 1L;

    private Long length;

    private Long relativeDelay;

    public Long getLength() {
        return length;
    }

    public SchConfigurationTimeParamsDTO length(Long length) {
        this.length = length;
        return this;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public Long getRelativeDelay() {
        return relativeDelay;
    }

    public void setRelativeDelay(Long relativeDelay) {
        this.relativeDelay = relativeDelay;
    }

    public SchConfigurationTimeParamsDTO relativeDelay(Long relativeDelay) {
        this.relativeDelay = relativeDelay;
        return this;
    }


    @Override
    public String toString() {
        return "SchConfigurationTimeParamsDTO{" +
                "length=" + length +
                ", relativeDelay=" + relativeDelay +
                '}';
    }
}

