package ru.tronin.springdata.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.tronin.springdata.models.dto.ProductDto;
import ru.tronin.springdata.models.entities.products.Product;
import ru.tronin.springdata.services.ProductService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {

    @Autowired
    private ProductService productService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping()
    public Page<ProductDto> index(@PageableDefault(size = 12) Pageable pageable,
                                  @RequestParam(name = "min_price", required = false) Double min,
                                  @RequestParam(name = "max_price", required = false) Double max,
                                  @RequestParam(name = "name_part", required = false) String partName) {

        return productService.findPaginatedProducts(min, max, partName, pageable);
    }

    @GetMapping("/{id}")
    public ProductDto showProduct(@PathVariable Long id) {

        return productService.getEntityById(id);
    }

    @PostMapping()
    public ResponseEntity<Object> createProduct(@RequestBody Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.OK);
        }
        productService.create(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}/edit")
    public ProductDto editProduct(@PathVariable Long id) {
        return productService.getEntityById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@RequestBody ProductDto product, @PathVariable Long id) {
        productService.updateProduct(product, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
    }


}
