package model.xml;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;

@XmlRootElement(name = "SaleObject")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class SaleObject {


    private String type;
    private long id;
    private Integer sizeSqm;
    private String startingPrice;
    private BigInteger startingPriceAsBigInteger;
    private Address address;

    public String getType() {
        return type;
    }

    @XmlAttribute
    public void setType(String type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    @XmlAttribute
    public void setId(long id) {
        this.id = id;
    }

    public Integer getSizeSqm() {
        return sizeSqm;
    }

    public void setSizeSqm(Integer sizeSqm) {
        this.sizeSqm = sizeSqm;
    }

    public String getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(String startingPrice) {
        this.startingPrice = startingPrice;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public BigInteger getStartingPriceAsBigInteger() {
        return startingPriceAsBigInteger;
    }

    public void setStartingPriceAsBigInteger(BigInteger startingPriceAsBigInteger) {
        this.startingPriceAsBigInteger = startingPriceAsBigInteger;
    }
}
