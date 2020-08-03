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
public class UserTypeDTO {
    private String id;
    private String type;
}
