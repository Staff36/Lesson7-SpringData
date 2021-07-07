package ru.tronin.springdata.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tronin.springdata.models.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCostBetween(Double min, Double max);

    @Query("select min(p.cost) from Product p")
    Double findMinCost();

    @Query("select max(p.cost) from Product p")
    Double findMaxCost();

    @Query("select p from Product p where " +
            "(p.name like concat('%',:partName,'%') " +
                "or p.description like concat('%',:partName,'%')) " +
            "and (p.cost between :minimum and :maximum)")
    List<Product> findProductLikePartName(@Param("partName") String partName,
                                          @Param("minimum") Double minimum, @Param("maximum") Double maximum);


}
