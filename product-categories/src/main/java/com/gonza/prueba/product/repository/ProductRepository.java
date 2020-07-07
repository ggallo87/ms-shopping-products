package com.gonza.prueba.product.repository;

import com.gonza.prueba.product.entity.Category;
import com.gonza.prueba.product.entity.Product;

import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    public List<Product> findByCategory(Category category);
}
