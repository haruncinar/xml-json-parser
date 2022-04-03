package model.xml;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "SaleObjects")
@XmlAccessorType(XmlAccessType.FIELD)
public class SaleObjects {

    @XmlAttribute(name = "count")
    private int count;
    @XmlElement(name = "SaleObject")
    private List<SaleObject> saleObjects;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<SaleObject> getSaleObjects() {
        return saleObjects;
    }

    public void setSaleObjects(List<SaleObject> saleObjects) {
        this.saleObjects = saleObjects;
    }

}


