package ru.tronin.springdata.services;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tronin.springdata.exceptions.NoEntityException;
import ru.tronin.springdata.models.Product;
import ru.tronin.springdata.repositories.ProductRepository;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getEntityById(Long id) {
        Product product = productRepository.getById(id);
        if(product.getId() == null){
            throw new NoEntityException("Entity with id =" + id + " not found");
        }
        return productRepository.getById(id);
    }

    public void create(Product product) {
        productRepository.saveAndFlush(product);
    }

    public Double getMinimumCost(){
        return productRepository.findMinCost();
    }

    public Double getMaximumCost(){
        return productRepository.findMaxCost();
    }

    public void update(Product product) {
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
