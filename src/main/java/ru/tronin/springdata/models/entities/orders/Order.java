package ru.tronin.springdata.models.entities.orders;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.tronin.springdata.models.entities.address.UsersAddress;
import ru.tronin.springdata.models.entities.product.OrderedProduct;
import ru.tronin.springdata.models.entities.users.User;

import javax.persistence.*;
import java.util.List;


@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    User user;
    @ManyToMany
    List<OrderedProduct> orderedProducts;



}
