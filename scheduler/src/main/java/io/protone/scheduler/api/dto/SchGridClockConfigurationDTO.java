package io.protone.scheduler.api.dto;

/**
 * Created by lukaszozimek on 09.09.2017.
 */
public class SchGridClockConfigurationDTO {
    private Long id;
    private Long sequence;
    private SchClockConfigurationDTO schClockConfigurationDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public SchClockConfigurationDTO getSchClockConfigurationDTO() {
        return schClockConfigurationDTO;
    }

    public void setSchClockConfigurationDTO(SchClockConfigurationDTO schClockConfigurationDTO) {
        this.schClockConfigurationDTO = schClockConfigurationDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SchGridClockConfigurationDTO that = (SchGridClockConfigurationDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (sequence != null ? !sequence.equals(that.sequence) : that.sequence != null) return false;
        return schClockConfigurationDTO != null ? schClockConfigurationDTO.equals(that.schClockConfigurationDTO) : that.schClockConfigurationDTO == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (sequence != null ? sequence.hashCode() : 0);
        result = 31 * result + (schClockConfigurationDTO != null ? schClockConfigurationDTO.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SchGridClockConfigurationDTO{" +
                "id=" + id +
                ", sequence=" + sequence +
                ", schClockConfigurationDTO=" + schClockConfigurationDTO +
                '}';
    }
}
