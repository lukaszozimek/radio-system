package io.protone.custom.service.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A DTO for the SchPlaylist entity.
 */
public class SchClockPT implements Serializable {

    private Long id;
    private String shortName;
    private String name;
    private Integer seq;
    private List<SchBlockPT> blocks = new ArrayList<>();
    private List<SchEmissionPT> emissions = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<SchBlockPT> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<SchBlockPT> blocks) {
        this.blocks = blocks;
    }

    public SchClockPT id(Long id) {
        this.id = id;
        return this;
    }

    private SchClockPT name(String name) {
        this.name = name;
        return this;
    }

    private SchClockPT shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public SchClockPT blocks(List<SchBlockPT> blocks) {
        this.blocks = blocks;
        return this;
    }

    public SchClockPT addBlock(SchBlockPT block) {
        this.blocks.add(block);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchClockPT)) return false;

        SchClockPT that = (SchClockPT) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getShortName() != null ? !getShortName().equals(that.getShortName()) : that.getShortName() != null)
            return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null)
            return false;
        return getBlocks() != null ? getBlocks().equals(that.getBlocks()) : that.getBlocks() == null;
    }


    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getShortName() != null ? getShortName().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getBlocks() != null ? getBlocks().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SchPlaylistPT{");
        sb.append("id=").append(id);
        sb.append(", shortName=").append(shortName);
        sb.append(", channelId=").append(name);
        sb.append(", blocks=").append(blocks);
        sb.append('}');
        return sb.toString();
    }
}
