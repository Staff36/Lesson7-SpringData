package ru.tronin.springdata.models.beans;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.tronin.springdata.exceptions.NoEntityException;
import ru.tronin.springdata.models.entities.products.OrderedProduct;
import ru.tronin.springdata.models.entities.products.Product;
import ru.tronin.springdata.services.ProductService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart {

    ProductService productService;
    List<OrderedProduct> orderedProducts;
    Double totalPrice;

    @PostConstruct
    public void init(){
       orderedProducts = new ArrayList<>();
    }

    public void addToCart(Long id){

        for (OrderedProduct orderedProduct : orderedProducts) {
            if (orderedProduct.getId().equals(id)){
                orderedProduct.incrementQuantity();
                recalculate();
                return;
            }
        }
        Product productFromDB = productService.findProductById(id).orElseThrow(() -> new NoEntityException("Product with id " + id + ", not found"));
        orderedProducts.add(new OrderedProduct(productFromDB));
        recalculate();
    }

    public void recalculate() {
        totalPrice = 0D;
        orderedProducts.forEach(orderedProduct -> totalPrice += orderedProduct.getTotalPrice());
    }

    public void clear(){
        orderedProducts.clear();
        recalculate();
    }



}
