package ru.tronin.springdata.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
@Data
@NoArgsConstructor
public class Cart {

    Map<Product, Integer> orderedProducts = new HashMap<>();



}
