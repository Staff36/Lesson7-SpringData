package ru.tronin.springdata.services;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.tronin.springdata.exceptions.NoEntityException;
import ru.tronin.springdata.models.entities.Product;
import ru.tronin.springdata.models.dto.ProductDto;
import ru.tronin.springdata.repositories.ProductRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Data
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;


    public ProductDto getEntityById(Long id) {
        Optional<Product> product = productRepository.findById(id);

        if(product.isEmpty()){
            throw new NoEntityException("Entity with id =" + id + " not found");
        }
        return mapProductToDto(product.get());
    }

    public Page<ProductDto> findPaginatedProducts(Pageable pageable, Double min, Double max, String namePart){
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List <Product> productsFromDB = getProducts(min, max, namePart);
        List<ProductDto> products;
        if (productsFromDB.size() < startItem){
            products = Collections.emptyList();
        } else {
            int endItem = Math.min(startItem + pageSize, productsFromDB.size());
            products = productsFromDB.subList(startItem, endItem)
                    .stream()
                    .map(this::mapProductToDto).toList();
        }
        return new PageImpl<ProductDto>(products, PageRequest.of(currentPage, pageSize), productsFromDB.size());
    }

    private ProductDto mapProductToDto(Product product){
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        productDto.setCategory(product.getCategory().getName());
        return productDto;
    }

    public void create(Product product) {
        productRepository.saveAndFlush(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getProducts(Double min, Double max, String namePart) {
        if (min == null && max == null && namePart == null){
            return productRepository.findAll();
        }
        if (min == null) {
            min = productRepository.findMinCost();
        }
        if (max == null) {
            max = productRepository.findMaxCost();
        }
        if (namePart == null){
            namePart ="";
        }
        return productRepository.findProductLikePartName(namePart,min, max);

    }
}
