package io.protone.custom.service.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the SchTemplate entity.
 */
public class SchTemplatePT implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String name;
    private Long channelId;

    private List<SchBlockPT> blocks;

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

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public List<SchBlockPT> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<SchBlockPT> blocks) {
        this.blocks = blocks;
    }

    public SchTemplatePT id(Long id) {
        this.id = id;
        return this;
    }

    public SchTemplatePT name(String name) {
        this.name = name;
        return this;
    }

    public SchTemplatePT channelId(Long channelId) {
        this.channelId = channelId;
        return this;
    }

    public SchTemplatePT blocks(List<SchBlockPT> blocks) {
        this.blocks = blocks;
        return this;
    }

    public SchTemplatePT addblock(SchBlockPT block) {
        this.blocks.add(block);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchTemplatePT)) return false;

        SchTemplatePT that = (SchTemplatePT) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getChannelId() != null ? !getChannelId().equals(that.getChannelId()) : that.getChannelId() != null)
            return false;
        return getBlocks() != null ? getBlocks().equals(that.getBlocks()) : that.getBlocks() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getChannelId() != null ? getChannelId().hashCode() : 0);
        result = 31 * result + (getBlocks() != null ? getBlocks().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SchTemplatePT{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", channelId=").append(channelId);
        sb.append(", blocks=").append(blocks);
        sb.append('}');
        return sb.toString();
    }
}
