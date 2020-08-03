package com.retail.discount.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Builder
@Data
@Entity
@Table(name = "invoice")
public class Invoice {
    @Id
    @Column(name="id")
    private String id;
    @Column(name="total_price")
    private double totalPrice;
    @Column(name="price_after_discount")
    private String priceAfterDiscount;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;





}
