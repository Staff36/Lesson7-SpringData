package ru.tronin.springdata.models.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
//    @Size(min = 2, max = 64, message = "Количетсво символов в поле Имя должно быть в диапазоне от 2 до 64")
    String name;
//    @NotEmpty(message = "Значение не может быть пустым")
    String description;
    @Min(value = 0, message = "Цена не может быть отрицательной")
    Double cost;

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "category", referencedColumnName = "id")
    Category category;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Product(String name, String description, Double cost, Category category) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.category = category;
    }
}
