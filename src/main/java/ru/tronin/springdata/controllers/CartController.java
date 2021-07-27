package ru.tronin.springdata.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.tronin.springdata.models.dto.OrderedProductsDto;
import ru.tronin.springdata.models.dto.ProductDto;
import ru.tronin.springdata.services.CartService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@Scope("session")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping
    public List<OrderedProductsDto> getAllProducts() {
        return cartService.getAllOrderedProducts();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addProductToCart(@RequestBody ProductDto productDto) {
        cartService.addProductToCart(productDto.getId(), 1);
    }

    @DeleteMapping("/{id}")
    public void deleteProductFromCart(@PathVariable Long id) {
        cartService.removeProductFromCart(id);
    }


}
