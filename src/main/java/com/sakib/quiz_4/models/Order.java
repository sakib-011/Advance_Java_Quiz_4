package com.sakib.quiz_4.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private String id;

    private String customerName;
    private String productName;
    private double productQuantity;
    private double productPrice;
    private String orderStatus;
    private LocalDate purchaseDate;

}
