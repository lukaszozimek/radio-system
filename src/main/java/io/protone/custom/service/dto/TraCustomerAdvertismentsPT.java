package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * TraCustomerAdvertismentsPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class TraCustomerAdvertismentsPT {

    @JsonProperty("mediaitem")
    private List<TraAdvertisementPT> mediaitem = new ArrayList<TraAdvertisementPT>();

    public TraCustomerAdvertismentsPT mediaitem(List<TraAdvertisementPT> mediaitem) {
        this.mediaitem = mediaitem;
        return this;
    }

    public TraCustomerAdvertismentsPT addMediaitemItem(TraAdvertisementPT mediaitemItem) {
        this.mediaitem.add(mediaitemItem);
        return this;
    }

    /**
     * Get mediaitem
     *
     * @return mediaitem
     **/
    @ApiModelProperty(value = "")
    public List<TraAdvertisementPT> getMediaitem() {
        return mediaitem;
    }

    public void setMediaitem(List<TraAdvertisementPT> mediaitem) {
        this.mediaitem = mediaitem;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TraCustomerAdvertismentsPT traCustomerAdvertismentsPT = (TraCustomerAdvertismentsPT) o;
        return Objects.equals(this.mediaitem, traCustomerAdvertismentsPT.mediaitem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mediaitem);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TraCustomerAdvertismentsPT {\n");
        sb.append("    mediaitem: ").append(toIndentedString(mediaitem)).append("\n");
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
}

