package io.protone.custom.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.domain.enumeration.CfgLogTypeEnum;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * ConfMusicLogPT
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

public class ConfMusicLogPT implements Serializable {
    private Long id;

    private String name;

    private Integer lenght;

    private CfgLogTypeEnum logColumn;

    private String delimiter;

    private Integer columnSequence;

    private Long channelId;

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

    public Integer getLenght() {
        return lenght;
    }

    public void setLenght(Integer lenght) {
        this.lenght = lenght;
    }

    public CfgLogTypeEnum getLogColumn() {
        return logColumn;
    }

    public void setLogColumn(CfgLogTypeEnum logColumn) {
        this.logColumn = logColumn;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public Integer getColumnSequence() {
        return columnSequence;
    }

    public void setColumnSequence(Integer columnSequence) {
        this.columnSequence = columnSequence;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long corChannelId) {
        this.channelId = corChannelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConfMusicLogPT cfgExternalSystemLogDTO = (ConfMusicLogPT) o;

        if (!Objects.equals(id, cfgExternalSystemLogDTO.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ConfMusicLogPT{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", lenght='" + lenght + "'" +
            ", logColumn='" + logColumn + "'" +
            ", delimiter='" + delimiter + "'" +
            ", columnSequence='" + columnSequence + "'" +
            '}';
    }
}

