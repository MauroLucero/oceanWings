package com.oceanwinds.product.repository;

import com.oceanwinds.category.entity.Category;
import com.oceanwinds.feature.entity.Feature;
import com.oceanwinds.location.entity.Location;
import com.oceanwinds.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    boolean existsByAvailable(boolean available);
    boolean existsById(Long id);



    Optional<Product> findByName(String name);

    List<Product> findByCategoryIn(List<Category> categories);

    List<Product> findByFeatureContaining(Feature feature);

    List<Product> findByFeatureIn(Set<Feature> features);
    List<Product> findByLocation(Location location);
    List<Product> findByLocationAndCategoryIn(Location location, List<Category> categories);

    List<Product> findByLocationAndFeatureIn(Location location, Set<Feature> features);

    List<Product> findByCategoryInAndFeatureIn(List<Category> categories, Set<Feature> features);

    List<Product> findByLocationAndCategoryInAndFeatureInAndPricePerDayBetween(Location location, List<Category> categories, Set<Feature> features, Double priceMin, Double priceMax);

    Set<Product> findByAvailable(boolean b);

    List<Product> findByAvailableAndCategory(boolean b, String category);

    Page<Product>  findAll (Pageable pegeable);

}
