package com.retail.discount.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Builder
@Table(name="user")
public class User {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="email")
    private String email;
    @Column(name="phone")
    private String phone;
    @Column(name="start_date")
    Date startDate;

    @ManyToOne
    @JoinColumn(name="user_type_id")
    private UserType userType;

    @OneToMany(mappedBy = "user", targetEntity = Invoice.class, fetch = FetchType.LAZY)
    @JsonIgnore
    List<Invoice> invoiceList;

}
