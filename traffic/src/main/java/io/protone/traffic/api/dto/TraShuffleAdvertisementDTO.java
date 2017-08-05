package io.protone.traffic.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.library.api.dto.thin.LibMediaItemThinDTO;
import io.protone.traffic.api.dto.thin.TraOrderThinDTO;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by lukaszozimek on 06/03/2017.
 */
public class TraShuffleAdvertisementDTO implements Serializable {


    @NotNull
    private LibMediaItemThinDTO libMediaItemThinDTO;

    @NotNull
    private TraOrderThinDTO traOrderThinDTO;

    @NotNull
    private LocalDate from;

    @NotNull
    private LocalDate to;

    @NotNull
    private int number;

    @JsonProperty("optional")
    @NotNull
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

    public LibMediaItemThinDTO getLibMediaItemThinDTO() {
        return libMediaItemThinDTO;
    }

    public void setLibMediaItemThinDTO(LibMediaItemThinDTO libMediaItemThinDTO) {
        this.libMediaItemThinDTO = libMediaItemThinDTO;
    }
    public TraOrderThinDTO getTraOrderThinDTO() {
        return traOrderThinDTO;
    }

    public void setTraOrderThinDTO(TraOrderThinDTO traOrderThinDTO) {
        this.traOrderThinDTO = traOrderThinDTO;
    }


    @Override
    public String toString() {
        return "TraShuffleAdvertisementDTO{" +
                "libMediaItemThinDTO=" + libMediaItemThinDTO +
                ", traOrderThinDTO=" + traOrderThinDTO +
                ", from=" + from +
                ", to=" + to +
                ", number=" + number +
                ", traShuffleAdvertisementOptionalDTO=" + traShuffleAdvertisementOptionalDTO +
                '}';
    }
}
