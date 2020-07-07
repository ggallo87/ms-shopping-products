package com.gonza.prueba.product;

import com.gonza.prueba.product.entity.Category;
import com.gonza.prueba.product.entity.Product;
import com.gonza.prueba.product.repository.ProductRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

@DataJpaTest
public class ProductRepositoryMockTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void whenFindByCategory_then_ReturnListProduct(){

        Product product = Product.builder()
                .name("computer")
                .category(Category.builder().id(1L).build())
                .description("")
                .stock(Double.parseDouble("10"))
                .price(Double.parseDouble("1240.99"))
                .status("Created")
                .createAt(new Date()).build();

        productRepository.save(product);

        List<Product> lista = productRepository.findByCategory(product.getCategory());

        Assertions.assertThat(lista.size()).isEqualTo(3);
    }
}
