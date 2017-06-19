package io.protone.web.rest.dto.traffic;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.web.rest.dto.traffic.thin.TraAdvertisementThinDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * TraEmissionDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class TraEmissionDTO implements Serializable {

    @JsonProperty("id")
    private Long id = null;

    @NotNull
    private TraAdvertisementThinDTO advertiment = null;

    @NotNull
    private Long timeStart = null;

    @NotNull
    private Long timeStop = null;

    @NotNull
    private Integer sequence = null;

    @NotNull
    private Long orderId = null;

    public TraEmissionDTO advertisment(TraAdvertisementThinDTO advertisment) {
        this.advertiment = advertisment;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get advertiment
     *
     * @return advertiment
     **/
    @ApiModelProperty(value = "")
    public TraAdvertisementThinDTO getAdvertiment() {
        return advertiment;
    }

    public void setAdvertiment(TraAdvertisementThinDTO advertiment) {
        this.advertiment = advertiment;
    }

    public TraEmissionDTO timeStart(Long timeStart) {
        this.timeStart = timeStart;
        return this;
    }

    /**
     * Get timeStart
     *
     * @return timeStart
     **/
    @ApiModelProperty(value = "")
    public Long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Long timeStart) {
        this.timeStart = timeStart;
    }

    public TraEmissionDTO timeStop(Long timeStop) {
        this.timeStop = timeStop;
        return this;
    }

    /**
     * Get timeStop
     *
     * @return timeStop
     **/
    @ApiModelProperty(value = "")
    public Long getTimeStop() {
        return timeStop;
    }

    public void setTimeStop(Long timeStop) {
        this.timeStop = timeStop;
    }


    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public TraEmissionDTO sequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public TraEmissionDTO order(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TraEmissionDTO that = (TraEmissionDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (advertiment != null ? !advertiment.equals(that.advertiment) : that.advertiment != null) return false;
        if (timeStart != null ? !timeStart.equals(that.timeStart) : that.timeStart != null) return false;
        if (timeStop != null ? !timeStop.equals(that.timeStop) : that.timeStop != null) return false;
        if (sequence != null ? !sequence.equals(that.sequence) : that.sequence != null) return false;
        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (advertiment != null ? advertiment.hashCode() : 0);
        result = 31 * result + (timeStart != null ? timeStart.hashCode() : 0);
        result = 31 * result + (timeStop != null ? timeStop.hashCode() : 0);
        result = 31 * result + (sequence != null ? sequence.hashCode() : 0);
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TraEmissionDTO{" +
            "id=" + id +
            ", advertiment=" + advertiment +
            ", timeStart=" + timeStart +
            ", timeStop=" + timeStop +
            ", sequence=" + sequence +
            ", orderId=" + orderId +
            '}';
    }

}

