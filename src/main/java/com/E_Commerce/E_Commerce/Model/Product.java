package com.E_Commerce.E_Commerce.Model;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Product")
public class Product {
    @Id
    private Long id;
    @Column(nullable = false , unique = true)
    private String name;
    @Column(nullable = false , unique = true)
    private String discription;
    @Column(nullable = false , unique = true)
    private int price;
    @Column(nullable = false , unique = true)
    private int StockQuantity;
    @Column(nullable = false , unique = true)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "categoy_id")
    private Category category;

}
