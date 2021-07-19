package ru.tronin.springdata.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.tronin.springdata.models.dto.ProductDto;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class Config {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    @Scope("prototype")
    public Map<ProductDto, Integer> orderedProducts(){
        return new HashMap<>();
    }

}
