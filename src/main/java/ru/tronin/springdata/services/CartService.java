package ru.tronin.springdata.services;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.tronin.springdata.models.dto.OrderedProductsDto;
import ru.tronin.springdata.models.dto.ProductDto;
import ru.tronin.springdata.repositories.CartRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope("session")
@Data
@RequiredArgsConstructor
public class CartService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartRepository cartRepository;


    private OrderedProductsDto mapProductToDto(ProductDto product, Integer value){
        OrderedProductsDto orderedProductsDto = modelMapper.map(product, OrderedProductsDto.class);
        orderedProductsDto.setCount(value);
        return orderedProductsDto;
    }

    public List<OrderedProductsDto> getAllOrderedProducts(){
    List<OrderedProductsDto> list = new ArrayList<>();
        cartRepository.getAllOrderedProducts().forEach((key, value) -> {
            list.add(mapProductToDto(key, value));
        });
        return list;
    }

    public void addProductToCart(Long id, Integer value){
        cartRepository.addProductToCart(id, value);
    }

    public void removeProductFromCart(Long id){
        cartRepository.removeProductFromCart(id);
    }
}
