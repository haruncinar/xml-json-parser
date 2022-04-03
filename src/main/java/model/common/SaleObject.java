package model.common;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data
@Builder
public class SaleObject {

    private String type;
    private long id;
    private Integer sizeSqm;
    private BigInteger startingPrice;
    private PostalAddress postalAddress;
}
