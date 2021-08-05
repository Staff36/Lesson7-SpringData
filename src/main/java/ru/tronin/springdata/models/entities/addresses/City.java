package ru.tronin.springdata.models.entities.addresses;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    //Считаю, что тут Игер возможен, так как в 99% случаев адрес будем доставать полностью
    @ManyToOne(fetch = FetchType.EAGER)
    Region region;

}
