package ru.tronin.springdata.models.entities.orders;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.tronin.springdata.models.entities.product.Product;

import java.util.HashMap;
import java.util.Map;

@Component
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart {

    Map<Product, Integer> orderedProducts = new HashMap<>();

}
