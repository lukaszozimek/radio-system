package io.protone.web.rest.dto.traffic;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.web.rest.dto.traffic.thin.TraAdvertisementThinDTO;
import io.swagger.annotations.ApiModelProperty;

/**
 * TraEmissionDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class TraEmissionDTO {

    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("advertiment")
    private TraAdvertisementThinDTO advertiment = null;

    @JsonProperty("timeStart")
    private Long timeStart = null;

    @JsonProperty("timeStop")
    private Long timeStop = null;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TraEmissionDTO traEmissionDTO = (TraEmissionDTO) o;
        return Objects.equals(this.advertiment, traEmissionDTO.advertiment) &&
            Objects.equals(this.timeStart, traEmissionDTO.timeStart) &&
            Objects.equals(this.timeStop, traEmissionDTO.timeStop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(advertiment, timeStart, timeStop);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TraEmissionDTO {\n");

        sb.append("    advertiment: ").append(toIndentedString(advertiment)).append("\n");
        sb.append("    timeStart: ").append(toIndentedString(timeStart)).append("\n");
        sb.append("    timeStop: ").append(toIndentedString(timeStop)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

