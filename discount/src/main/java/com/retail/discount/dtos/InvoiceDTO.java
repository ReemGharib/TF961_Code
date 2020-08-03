package com.retail.discount.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class InvoiceDTO {
    private String id;
    private double totalPrice;
    private String priceAfterDiscount;
    private UserDTO userDTO;


}
