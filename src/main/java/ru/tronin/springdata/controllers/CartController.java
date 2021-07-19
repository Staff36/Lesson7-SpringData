package ru.tronin.springdata.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.tronin.springdata.models.dto.OrderedProductsDto;
import ru.tronin.springdata.models.dto.ProductDto;
import ru.tronin.springdata.repositories.CartRepository;
import ru.tronin.springdata.services.CartService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Scope("session")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping
    public List<OrderedProductsDto> getAllProducts(){
        return cartService.getAllOrderedProducts();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addProductToCart(@RequestBody ProductDto productDto,
                                 @RequestBody Integer count){
        if (count == null){
            count = 1;
        }
        cartService.addProductToCart(productDto.getId(), count);
    }

    @DeleteMapping("/{id}")
    public void deleteProductFromCart(@PathVariable Long id){
        cartService.removeProductFromCart(id);
    }


}
