package io.protone.traffic.service.mediaplan.descriptor;


import io.protone.library.domain.LibMediaItem;
import io.protone.traffic.domain.TraMediaPlanTemplate;
import io.protone.traffic.domain.TraOrder;

/**
 * Created by lukaszozimek on 11/06/2017.
 */
public class TraMediaPlanDescriptor {
    private TraMediaPlanTemplate traMediaPlanTemplate;

    private TraOrder order;

    private LibMediaItem libMediaItem;

    public TraMediaPlanTemplate getTraMediaPlanTemplate() {
        return traMediaPlanTemplate;
    }

    public void setTraMediaPlanTemplate(TraMediaPlanTemplate traMediaPlanTemplate) {
        this.traMediaPlanTemplate = traMediaPlanTemplate;
    }

    public TraMediaPlanDescriptor mediaplanTemplate(TraMediaPlanTemplate traMediaPlanTemplate) {
        this.traMediaPlanTemplate = traMediaPlanTemplate;
        return this;
    }

    public TraOrder getOrder() {
        return order;
    }

    public void setOrder(TraOrder order) {
        this.order = order;
    }

    public TraMediaPlanDescriptor order(TraOrder traOrder) {
        this.order = traOrder;
        return this;
    }


    public LibMediaItem getLibMediaItem() {
        return libMediaItem;
    }

    public void setLibMediaItem(LibMediaItem libMediaItem) {
        this.libMediaItem = libMediaItem;
    }

    public TraMediaPlanDescriptor libMediaItem(LibMediaItem libMediaItem) {
        this.libMediaItem = libMediaItem;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TraMediaPlanDescriptor that = (TraMediaPlanDescriptor) o;

        if (traMediaPlanTemplate != null ? !traMediaPlanTemplate.equals(that.traMediaPlanTemplate) : that.traMediaPlanTemplate != null)
            return false;
        if (order != null ? !order.equals(that.order) : that.order != null) return false;
        return libMediaItem != null ? libMediaItem.equals(that.libMediaItem) : that.libMediaItem == null;
    }

    @Override
    public int hashCode() {
        int result = traMediaPlanTemplate != null ? traMediaPlanTemplate.hashCode() : 0;
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (libMediaItem != null ? libMediaItem.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TraMediaPlanDescriptor{" +
                "traMediaPlanTemplate=" + traMediaPlanTemplate +
                ", order=" + order +
                ", libMediaItem=" + libMediaItem +
                '}';
    }

}
