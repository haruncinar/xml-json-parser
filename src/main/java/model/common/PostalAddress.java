package model.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostalAddress {

    private String city;
    private String street;
    private int floor;
}
