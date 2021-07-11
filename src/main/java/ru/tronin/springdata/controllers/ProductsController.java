package ru.tronin.springdata.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.tronin.springdata.models.entities.Product;
import ru.tronin.springdata.models.dto.ProductDto;
import ru.tronin.springdata.repositories.CategoriesRepository;
import ru.tronin.springdata.repositories.ProductRepository;
import ru.tronin.springdata.services.ProductService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoriesRepository categoriesRepository;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping()
    public Page<ProductDto> index(Model model,
                                  @PageableDefault(size = 12) Pageable pageable,
                                  @RequestParam(name = "min_price", required = false) Double min,
                                  @RequestParam(name = "max_price", required = false) Double max,
                                  @RequestParam(name = "name_part", required = false) String partName){

            return productService.findPaginatedProducts(pageable, min, max, partName);
    }



    @GetMapping("/{id}")
    public String showProduct(@PathVariable Long id, Model model){
    model.addAttribute("product", productService.getEntityById(id));
    return "products/show";
}

    @GetMapping("/new")
    public String newProduct(Model model){
        model.addAttribute("prod", new Product());
        return "products/new";
     }

    @PostMapping()
    public ResponseEntity<Object> createProduct(@RequestBody @Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.OK);
        }
        productService.create(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    @GetMapping("/{id}/edit")
//    public String editProduct(Model model, @PathVariable Long id){
//        try {
//            model.addAttribute("prod", productService.getEntityById(id));
//        } catch (NoEntityException ignored){
//            return "products/notFound";
//        }
//        return "products/edit";
//    }

//    @PatchMapping("/{id}")
//    public ResponseEntity<Object> updateProduct(@RequestBody @Valid Product product,
//                                BindingResult bindingResult){
//        if (bindingResult.hasErrors()){
//            List<String> errors = bindingResult.getAllErrors().stream()
//                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                    .collect(Collectors.toList());
//            return new ResponseEntity<>(errors, HttpStatus.OK);
//        }
//        productService.update(product);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteById(id);
    }



}
