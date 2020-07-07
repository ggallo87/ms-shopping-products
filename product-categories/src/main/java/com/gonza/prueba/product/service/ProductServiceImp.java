package com.gonza.prueba.product.service;

import com.gonza.prueba.product.entity.Category;
import com.gonza.prueba.product.entity.Product;
import com.gonza.prueba.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService{

    //@Autowired
    private final ProductRepository productRepository;

    @Override
    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        product.setStatus("CREATED");
        product.setCreateAt(new Date());

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {

        Product producDB = getProduct(product.getId());

        if (null == producDB){
            return null;
        }

        producDB.setCategory(product.getCategory());
        producDB.setDescription(product.getDescription());
        producDB.setName(product.getName());
        producDB.setPrice(product.getPrice());

        return productRepository.save(producDB);
    }

    @Override
    public Product deleteProduct(Long id) {
        Product productDB = getProduct(id);

        if (null == productDB){
            return  null;
        }
        productDB.setStatus("DELETED");
        return productRepository.save(productDB);
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Product updateStock(Long id, Double quantity) {
        Product productDB = getProduct(id);

        if (null == productDB){
            return null;
        }

        Double stock = productDB.getStock() + quantity;
        productDB.setStock(stock);
        return productRepository.save(productDB);
    }
}
