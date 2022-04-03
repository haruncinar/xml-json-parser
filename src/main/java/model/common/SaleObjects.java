package model.common;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleObjects {

    private int numberOfSaleObjects;
    private List<SaleObject> saleObjects;
}
