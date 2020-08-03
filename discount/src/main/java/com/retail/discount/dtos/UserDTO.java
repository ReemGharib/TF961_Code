package com.retail.discount.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Builder
@Setter
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class UserDTO {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Date startDate;
    private UserTypeDTO userTypeDTO;

}
