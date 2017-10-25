package io.protone.scheduler.api.dto.base;

/**
 * SchTimeParamsDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class SchTemplateTimeParamsDTO extends SchBaseDTO {

    private static final long serialVersionUID = 1L;
    protected Long sequence;
    private Long length;
    private Long relativeDelay;

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public SchTemplateTimeParamsDTO length(Long length) {
        this.length = length;
        return this;
    }

    public Long getRelativeDelay() {
        return relativeDelay;
    }

    public void setRelativeDelay(Long relativeDelay) {
        this.relativeDelay = relativeDelay;
    }

    public SchTemplateTimeParamsDTO relativeDelay(Long relativeDelay) {
        this.relativeDelay = relativeDelay;
        return this;
    }


    @Override
    public String toString() {
        return "SchTemplateTimeParamsDTO{" +
                "length=" + length +
                ", relativeDelay=" + relativeDelay +
                '}';
    }
}

