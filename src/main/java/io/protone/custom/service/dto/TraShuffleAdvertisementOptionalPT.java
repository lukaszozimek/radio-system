package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lukaszozimek on 06/03/2017.
 */
public class TraShuffleAdvertisementOptionalPT {

    @JsonProperty("priority")
    private String priority;

    @JsonProperty("offsetType")
    private String offsetType;

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getOffsetType() {
        return offsetType;
    }

    public void setOffsetType(String offsetType) {
        this.offsetType = offsetType;
    }
}
