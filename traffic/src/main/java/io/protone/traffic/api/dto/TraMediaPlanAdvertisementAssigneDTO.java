package io.protone.traffic.api.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by lukaszozimek on 06/03/2017.
 */
public class TraMediaPlanAdvertisementAssigneDTO implements Serializable {

    @NotNull
    private Long mediaPlanId;

    @NotNull
    private String libMediaItemIdx;


    private boolean firstPostion;

    private boolean lasPosition;

    public String getLibMediaItemIdx() {
        return libMediaItemIdx;
    }

    public void setLibMediaItemIdx(String libMediaItemIdx) {
        this.libMediaItemIdx = libMediaItemIdx;
    }

    public TraMediaPlanAdvertisementAssigneDTO libMediaItemIdx(String libMediaItemIdx) {
        this.libMediaItemIdx = libMediaItemIdx;
        return this;
    }

    public Long getMediaPlanId() {
        return mediaPlanId;
    }

    public void setMediaPlanId(Long mediaPlanId) {
        this.mediaPlanId = mediaPlanId;
    }

    public TraMediaPlanAdvertisementAssigneDTO mediaPlanId(Long mediaPlanId) {
        this.mediaPlanId = mediaPlanId;
        return this;
    }

    public boolean isFirstPostion() {
        return firstPostion;
    }

    public void setFirstPostion(boolean firstPostion) {
        this.firstPostion = firstPostion;
    }

    public TraMediaPlanAdvertisementAssigneDTO firstPostion(boolean firstPostion) {
        this.firstPostion = firstPostion;
        return this;
    }

    public boolean isLasPosition() {
        return lasPosition;
    }

    public void setLasPosition(boolean lasPosition) {
        this.lasPosition = lasPosition;
    }

    public TraMediaPlanAdvertisementAssigneDTO lasPosition(boolean lasPosition) {
        this.lasPosition = lasPosition;
        return this;
    }
}
