package io.protone.application.web.rest.dto.cor;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * CorCurrencyDTO
 */
public class CorCurrencyDTO implements Serializable{
    private Long id = null;

    @NotNull
    private String name = null;

    private String symbol = null;

    private String delimiter = null;

    @NotNull
    private String shortName = null;

    public CorCurrencyDTO id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     **/
    @ApiModelProperty(value = "")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CorCurrencyDTO name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get name
     *
     * @return name
     **/
    @ApiModelProperty(required = true, value = "")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CorCurrencyDTO symbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    /**
     * Get symbol
     *
     * @return symbol
     **/
    @ApiModelProperty(required = true, value = "")
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public CorCurrencyDTO delimiter(String delimiter) {
        this.delimiter = delimiter;
        return this;
    }

    /**
     * Get delimiter
     *
     * @return delimiter
     **/
    @ApiModelProperty(required = true, value = "")
    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public CorCurrencyDTO shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    /**
     * Get shortName
     *
     * @return shortName
     **/
    @ApiModelProperty(required = true, value = "")
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CorCurrencyDTO corCurrencyDTO = (CorCurrencyDTO) o;
        return Objects.equals(this.id, corCurrencyDTO.id) &&
            Objects.equals(this.name, corCurrencyDTO.name) &&
            Objects.equals(this.symbol, corCurrencyDTO.symbol) &&
            Objects.equals(this.delimiter, corCurrencyDTO.delimiter) &&
            Objects.equals(this.shortName, corCurrencyDTO.shortName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, symbol, delimiter, shortName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CorCurrencyDTO {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    symbol: ").append(toIndentedString(symbol)).append("\n");
        sb.append("    delimiter: ").append(toIndentedString(delimiter)).append("\n");
        sb.append("    shortName: ").append(toIndentedString(shortName)).append("\n");
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

