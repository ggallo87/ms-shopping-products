package com.gonza.prueba.shopping.repository.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Data
@Entity
@Table(name = "tbl_invoce_items")
public class InvoiceItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive(message = "El stock debe ser mayor que cero")
    private Double quantity;
    private Double price;

    @Column(name = "product_id")
    private Long productId;

    @Transient
    private Double subTotal;

    public InvoiceItems() {
        this.quantity = (double) 0;
        this.price = (double) 0;
    }

    public Double getSubtotal(){
        if (this.price > 0 && this.quantity > 0){
            return this.quantity * this.price;
        } else {
            return (double) 0;
        }
    }
}
