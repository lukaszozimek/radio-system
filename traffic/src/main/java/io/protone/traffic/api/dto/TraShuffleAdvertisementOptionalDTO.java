package io.protone.traffic.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lukaszozimek on 06/03/2017.
 */
public class TraShuffleAdvertisementOptionalDTO {

    @JsonProperty("firstPosition")
    private boolean firstPosition;

    @JsonProperty("lastPostion")
    private boolean lastPostion;

    public boolean isFirstPosition() {
        return firstPosition;
    }

    public void setFirstPosition(boolean firstPosition) {
        this.firstPosition = firstPosition;
    }

    public boolean isLastPostion() {
        return lastPostion;
    }

    public void setLastPostion(boolean lastPostion) {
        this.lastPostion = lastPostion;
    }
}
