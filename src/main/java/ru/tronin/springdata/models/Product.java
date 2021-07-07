package ru.tronin.springdata.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "table_name")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Size(min = 2, max = 64, message = "Количетсво символов в поле Имя должно быть в диапазоне от 2 до 64")
    String name;
    @NotEmpty(message = "Значение не может быть пустым")
    String description;
    @Min(value = 0, message = "Цена не может быть отрицательной")
    Double cost;
}
