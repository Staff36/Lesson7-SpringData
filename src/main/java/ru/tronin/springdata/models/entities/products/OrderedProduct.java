package ru.tronin.springdata.models.entities.products;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "ordered_products")
public class OrderedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
    Long quantity;
    @Column(name = "ordered_product_name")
    Double orderedProductPrice;
    @Column(name = "total_price")
    Double totalPrice;

    @Column(name = "created_at")
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    LocalDateTime updatedAt;

    public OrderedProduct(Product product){
        this.product = product;
        this.quantity = 1L;
        this.orderedProductPrice = product.getCost();
        this.totalPrice = this.orderedProductPrice;
    }

    public void incrementQuantity(){
        quantity++ ;
        updatePrice();
    }

    public void decrementQuantity(){
        quantity--;
        updatePrice();
    }

    private void updatePrice(){
        totalPrice = quantity * orderedProductPrice;
    }

}
