package ru.tronin.springdata.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.tronin.springdata.exceptions.NoEntityException;
import ru.tronin.springdata.models.Product;
import ru.tronin.springdata.services.ProductService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductService productService;


    @GetMapping()
    public String index(Model model,
                        @RequestParam(name = "min", required = false) Double min,
                        @RequestParam(name = "max", required = false) Double max,
                        @RequestParam(name = "partName", required = false) String partName,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size){
            Double minimumCost = productService.getMinimumCost();
            Double maximumCost = productService.getMaximumCost();
            min = min == null ? minimumCost : min;
            max = max == null ? maximumCost : max;
            partName = partName == null ? "" : partName;
            int currentPage = page.orElse(1);
            int pageSize = size.orElse(12);
            Page<Product> productPage = productService.findPaginatedProducts(PageRequest.of(currentPage - 1, pageSize), min, max, partName);

            model.addAttribute("prodPage", productPage);

            int totalPages = productPage.getTotalPages();

            if(totalPages > 0){
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
            }
            model.addAttribute("minCost", minimumCost);
            model.addAttribute("maxCost", maximumCost);
            model.addAttribute("partName", partName);
            model.addAttribute("from", min);
            model.addAttribute("to", max);

            return "products/products";
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
    public String createProduct(@ModelAttribute("prod") @Valid Product product, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "products/new";
        }
        productService.create(product);
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String editProduct(Model model, @PathVariable Long id){
        try {
            model.addAttribute("prod", productService.getEntityById(id));
        } catch (NoEntityException ignored){
            return "products/notFound";
        }
        return "products/edit";
    }

    @PatchMapping("/{id}")
    public String updateProduct(@ModelAttribute("prod") @Valid Product product,
                                BindingResult bindingResult,
                                @PathVariable Long id){
        if (bindingResult.hasErrors()){
            return "products/edit";
        }
        productService.update(product); //Переделать
        return "redirect:/products";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.deleteById(id);
        return "redirect:/products";
    }



}
