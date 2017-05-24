package io.protone.web.rest.dto.traffic;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

/**
 * Created by lukaszozimek on 06/03/2017.
 */
public class TraShuffleAdvertisementDTO {

    @JsonProperty("advertisement")
    private TraAdvertisementDTO traAdvertisementDTO;

    @JsonProperty("from")
    private LocalDate from;

    @JsonProperty("to")
    private LocalDate to;

    @JsonProperty("number")
    private int number;

    @JsonProperty("optional")
    private TraShuffleAdvertisementOptionalDTO traShuffleAdvertisementOptionalDTO;

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public TraShuffleAdvertisementOptionalDTO getTraShuffleAdvertisementOptionalDTO() {
        return traShuffleAdvertisementOptionalDTO;
    }

    public void setTraShuffleAdvertisementOptionalDTO(TraShuffleAdvertisementOptionalDTO traShuffleAdvertisementOptionalDTO) {
        this.traShuffleAdvertisementOptionalDTO = traShuffleAdvertisementOptionalDTO;
    }

    public TraAdvertisementDTO getTraAdvertisementDTO() {
        return traAdvertisementDTO;
    }

    public void setTraAdvertisementDTO(TraAdvertisementDTO traAdvertisementDTO) {
        this.traAdvertisementDTO = traAdvertisementDTO;
    }

    @Override
    public String toString() {
        return "TraShuffleAdvertisementDTO{" +
            "traAdvertisementDTO=" + traAdvertisementDTO +
            ", from=" + from +
            ", to=" + to +
            ", number=" + number +
            ", traShuffleAdvertisementOptionalDTO=" + traShuffleAdvertisementOptionalDTO +
            '}';
    }
}
