package io.protone.web.rest.dto.traffic;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * TraBlockDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class TraBlockDTO {

    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("length")
    private Long length = null;


    @JsonProperty("startBlock")
    private Long startBlock = null;


    @JsonProperty("stopBlock")
    private Long stopBlock = null;


    @JsonProperty("sequence")
    private Integer sequence = null;


    @JsonProperty("emissions")
    private List<TraEmissionDTO> emissions = new ArrayList<TraEmissionDTO>();


    public TraBlockDTO id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     **/
    @ApiModelProperty(value = "")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TraBlockDTO name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get name
     *
     * @return name
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TraBlockDTO length(Long lenght) {
        this.length = lenght;
        return this;
    }

    /**
     * Get lenght
     *
     * @return lenght
     **/
    @ApiModelProperty(value = "")
    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }


    public TraBlockDTO emissions(List<TraEmissionDTO> emissions) {
        this.emissions = emissions;
        return this;
    }

    public TraBlockDTO addEmissionsItem(TraEmissionDTO emissionsItem) {
        this.emissions.add(emissionsItem);
        return this;
    }

    /**
     * Get emissions
     *
     * @return emissions
     **/
    @ApiModelProperty(value = "")
    public List<TraEmissionDTO> getEmissions() {
        return emissions;
    }

    public void setEmissions(List<TraEmissionDTO> emissions) {
        this.emissions = emissions;
    }

    @ApiModelProperty(value = "")
    public Long getStopBlock() {
        return stopBlock;
    }

    public void setStopBlock(Long stopBlock) {
        this.stopBlock = stopBlock;
    }

    @ApiModelProperty(value = "")
    public Long getStartBlock() {
        return startBlock;
    }

    public void setStartBlock(Long startBlock) {
        this.startBlock = startBlock;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public TraBlockDTO sequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TraBlockDTO traBlockDTO = (TraBlockDTO) o;
        return
            Objects.equals(this.id, traBlockDTO.id) &&
                Objects.equals(this.name, traBlockDTO.name) &&
                Objects.equals(this.length, traBlockDTO.length) &&
                Objects.equals(this.startBlock, traBlockDTO.startBlock) &&
                Objects.equals(this.stopBlock, traBlockDTO.stopBlock) &&
                Objects.equals(this.emissions, traBlockDTO.emissions);
    }

    @Override
    public String toString() {
        return "TraBlockDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", length=" + length +
            ", startBlock=" + startBlock +
            ", stopBlock=" + stopBlock +
            ", sequence=" + sequence +
            ", emissions=" + emissions +
            '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, length, emissions);
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

