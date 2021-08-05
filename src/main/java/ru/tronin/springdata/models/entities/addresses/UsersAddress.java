package ru.tronin.springdata.models.entities.addresses;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "address")
public class UsersAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    //Считаю, что тут Игер возможен, так как в 99% случаев адрес будем доставать полностью
    @ManyToOne(fetch = FetchType.EAGER)
    City city;
    @Column(name = "house_number")
    String houseNumber;
    @Column(name = "flat_number")
    String flatNumber;

    @Column(name = "created_at")
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    LocalDateTime updatedAt;
}
