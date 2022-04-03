package model.xml;

import javax.xml.bind.annotation.*;
import java.util.Comparator;

@XmlRootElement(name = "Address")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Address {

    private String city;
    private String street;
    private int floor;


    public  String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public static Comparator<Address> cityComparator = (address1, address2) -> address1.getCity().compareTo(address2.getCity());


}
