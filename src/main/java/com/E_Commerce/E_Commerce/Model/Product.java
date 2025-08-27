package com.E_Commerce.E_Commerce.Model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product") // Conventionally, table names are lowercase
public class Product{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IMPORTANT: To auto-generate the ID
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false) // A description is unlikely to be unique
    private String discription; // Note: The correct spelling is "description"

    @Column(nullable = false) // Price is not unique
    private int price;

    @Column( nullable = false) // Stock quantity is not unique
    private int stockQuantity;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY) // Use LAZY fetch for better performance
    @JoinColumn(name = "categoy_id")
    private Category category;

}
