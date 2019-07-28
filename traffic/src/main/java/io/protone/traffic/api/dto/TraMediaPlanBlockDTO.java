package io.protone.traffic.api.dto;

import java.io.Serializable;

/**
 * Created by lukaszozimek on 11/06/2017.
 */
public class TraMediaPlanBlockDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Long length;

    private Integer sequence;

    private Long startBlock;

    private Long stopBlock;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TraMediaPlanBlockDTO name(String name) {
        this.name = name;
        return this;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public TraMediaPlanBlockDTO length(Long length) {
        this.length = length;
        return this;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public TraMediaPlanBlockDTO sequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    public Long getStartBlock() {
        return startBlock;
    }

    public void setStartBlock(Long startBlock) {
        this.startBlock = startBlock;
    }

    public TraMediaPlanBlockDTO startBlock(Long startBlock) {
        this.startBlock = startBlock;
        return this;
    }

    public Long getStopBlock() {
        return stopBlock;
    }

    public void setStopBlock(Long stopBlock) {
        this.stopBlock = stopBlock;
    }

    public TraMediaPlanBlockDTO stopBlock(Long stopBlock) {
        this.stopBlock = stopBlock;
        return this;
    }

}
