package ru.tronin.springdata.repositories;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import ru.tronin.springdata.models.dto.ProductDto;
import ru.tronin.springdata.models.entities.Product;
import ru.tronin.springdata.services.ProductService;

import java.util.Map;

@Data
@Scope("session")
@Repository
public class CartRepository {

    @Autowired
    private Map<ProductDto, Integer> orderedProducts;

    @Autowired
    private ProductService productService;

    public Map<ProductDto, Integer> getAllOrderedProducts(){
        return orderedProducts;
    }

    public void addProductToCart(Long id, Integer value){
        if(value <= 0){
            removeProductFromCart(id);
        }
        ProductDto product = findProductInCartById(id);
        if(product == null){
            orderedProducts.put(productService.getEntityById(id), value);
        } else{
            value = orderedProducts.get(product) + value;
            value = value <=  0 ? 0 : value;
            if(value <= 0){
                removeProductFromCart(id);
            }
            orderedProducts.put(product, value);
        }
    }

    private ProductDto findProductInCartById(Long id){
        return orderedProducts.entrySet().stream()
                .filter(product -> product.getKey().getId().equals(id))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public void removeProductFromCart(Long id){
        ProductDto product = findProductInCartById(id);
        orderedProducts.remove(product);
    }
}
