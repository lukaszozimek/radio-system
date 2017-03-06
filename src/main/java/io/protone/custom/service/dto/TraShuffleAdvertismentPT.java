package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

/**
 * Created by lukaszozimek on 06/03/2017.
 */
public class TraShuffleAdvertismentPT {

    @JsonProperty("advertisement")
    private TraAdvertisementPT traAdvertisementPT;

    @JsonProperty("from")
    private ZonedDateTime from;

    @JsonProperty("to")
    private ZonedDateTime to;

    @JsonProperty("number")
    private int number;

    @JsonProperty("optional")
    private TraShuffleAdvertismentOptionaltPT traShuffleAdvertismentOptionaltPT;

    public ZonedDateTime getFrom() {
        return from;
    }

    public void setFrom(ZonedDateTime from) {
        this.from = from;
    }

    public ZonedDateTime getTo() {
        return to;
    }

    public void setTo(ZonedDateTime to) {
        this.to = to;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public TraShuffleAdvertismentOptionaltPT getTraShuffleAdvertismentOptionaltPT() {
        return traShuffleAdvertismentOptionaltPT;
    }

    public void setTraShuffleAdvertismentOptionaltPT(TraShuffleAdvertismentOptionaltPT traShuffleAdvertismentOptionaltPT) {
        this.traShuffleAdvertismentOptionaltPT = traShuffleAdvertismentOptionaltPT;
    }

    public TraAdvertisementPT getTraAdvertisementPT() {
        return traAdvertisementPT;
    }

    public void setTraAdvertisementPT(TraAdvertisementPT traAdvertisementPT) {
        this.traAdvertisementPT = traAdvertisementPT;
    }
}
