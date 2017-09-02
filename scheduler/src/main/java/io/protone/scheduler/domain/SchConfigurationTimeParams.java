package io.protone.scheduler.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * A TimeParams.
 */
@Embeddable
public class SchConfigurationTimeParams implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "length")
    private Long length;

    @Column(name = "relative_delay")
    private Long relativeDelay;

    public Long getLength() {
        return length;
    }

    public SchConfigurationTimeParams length(Long length) {
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

    public SchConfigurationTimeParams relativeDelay(Long relativeDelay) {
        this.relativeDelay = relativeDelay;
        return this;
    }


    @Override
    public String toString() {
        return "SchConfigurationTimeParams{" +
                "length=" + length +
                ", relativeDelay=" + relativeDelay +
                '}';
    }
}
