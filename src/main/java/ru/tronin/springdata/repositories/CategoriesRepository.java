package ru.tronin.springdata.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tronin.springdata.models.entities.Category;

public interface CategoriesRepository extends JpaRepository<Category, Long> {
    Category findCategoryByName(String name);
}
