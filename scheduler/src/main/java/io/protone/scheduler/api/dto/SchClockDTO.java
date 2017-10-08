package io.protone.scheduler.api.dto;

import io.protone.core.api.dto.CorDictionaryDTO;
import io.swagger.annotations.ApiModelProperty;
import uk.co.jemos.podam.common.PodamExclude;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * SchClockDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class SchClockDTO extends SchTimeParamsDTO {

    @PodamExclude
    private List<SchBlockDTO> blocks = new ArrayList<SchBlockDTO>();

    @PodamExclude
    private List<SchEmissionDTO> emissions = new ArrayList<SchEmissionDTO>();

    private String name = null;


    private CorDictionaryDTO clockCategory;


    private String shortName = null;

    public SchClockDTO endTime(LocalDateTime endTime) {
        super.setEndTime(endTime);
        return this;
    }

    public SchClockDTO startTime(LocalDateTime startTime) {
        super.setStartTime(startTime);
        return this;
    }

    public SchClockDTO blocks(List<SchBlockDTO> blocks) {
        this.blocks = blocks;
        return this;
    }

    public SchClockDTO addBlocksItem(SchBlockDTO blocksItem) {
        this.blocks.add(blocksItem);
        return this;
    }

    /**
     * Get blocks
     *
     * @return blocks
     **/
    @ApiModelProperty(value = "")
    public List<SchBlockDTO> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<SchBlockDTO> blocks) {
        this.blocks = blocks;
    }

    public SchClockDTO emissions(List<SchEmissionDTO> emissions) {
        this.emissions = emissions;
        return this;
    }

    public SchClockDTO addEmissionsItem(SchEmissionDTO emissionsItem) {
        this.emissions.add(emissionsItem);
        return this;
    }

    /**
     * Get emissions
     *
     * @return emissions
     **/
    @ApiModelProperty(value = "")
    public List<SchEmissionDTO> getEmissions() {
        return emissions;
    }

    public void setEmissions(List<SchEmissionDTO> emissions) {
        this.emissions = emissions;
    }

    public SchClockDTO id(Long id) {
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

    public SchClockDTO name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get name
     *
     * @return name
     **/
    @ApiModelProperty(value = "")
    @Size(min = 0, max = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CorDictionaryDTO getClockCategory() {
        return clockCategory;
    }

    public void setClockCategory(CorDictionaryDTO clockCategory) {
        this.clockCategory = clockCategory;
    }


    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchClockDTO schClockDTO = (SchClockDTO) o;
        return Objects.equals(this.blocks, schClockDTO.blocks) &&
                Objects.equals(this.emissions, schClockDTO.emissions) &&
                Objects.equals(this.id, schClockDTO.id) &&
                Objects.equals(this.name, schClockDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blocks, emissions, id, name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SchClockDTO {\n");

        sb.append("    blocks: ").append(toIndentedString(blocks)).append("\n");
        sb.append("    emissions: ").append(toIndentedString(emissions)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    clockCategory: ").append(toIndentedString(clockCategory)).append("\n");
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

    public SchClockDTO length(long nano) {
        super.setLength(nano);
        return this;
    }
}

