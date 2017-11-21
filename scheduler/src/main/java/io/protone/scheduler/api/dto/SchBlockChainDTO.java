package io.protone.scheduler.api.dto;

import com.google.common.base.Objects;

/**
 * SchBlockDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-14T18:17:14.326Z")

public class SchBlockChainDTO {
    private Long previousBlockId;
    private SchBlockDTO schBlockDTO;
    private Long nextBlockId;

    public Long getPreviousBlockId() {
        return previousBlockId;
    }

    public void setPreviousBlockId(Long previousBlockId) {
        this.previousBlockId = previousBlockId;
    }

    public SchBlockDTO getSchBlockDTO() {
        return schBlockDTO;
    }

    public void setSchBlockDTO(SchBlockDTO schBlockDTO) {
        this.schBlockDTO = schBlockDTO;
    }

    public Long getNextBlockId() {
        return nextBlockId;
    }

    public void setNextBlockId(Long nextBlockId) {
        this.nextBlockId = nextBlockId;
    }

    @Override
    public String toString() {
        return "SchBlockChainDTO{" +
                "previousBlockId=" + previousBlockId +
                ", schBlockDTO=" + schBlockDTO +
                ", nextBlockId=" + nextBlockId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchBlockChainDTO)) return false;
        SchBlockChainDTO that = (SchBlockChainDTO) o;
        return Objects.equal(getPreviousBlockId(), that.getPreviousBlockId()) &&
                Objects.equal(getSchBlockDTO(), that.getSchBlockDTO()) &&
                Objects.equal(getNextBlockId(), that.getNextBlockId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPreviousBlockId(), getSchBlockDTO(), getNextBlockId());
    }
}

