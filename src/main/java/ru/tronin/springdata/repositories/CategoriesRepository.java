package ru.tronin.springdata.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tronin.springdata.models.entities.product.Category;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Long> {
    Category findCategoryByName(String name);
}
