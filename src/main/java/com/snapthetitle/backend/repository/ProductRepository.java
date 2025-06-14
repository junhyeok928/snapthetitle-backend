// ProductRepository.java
package com.snapthetitle.backend.repository;

import com.snapthetitle.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByYearAndDeletedYnOrderByDisplayOrderAsc(Integer year, String deletedYn);
}
