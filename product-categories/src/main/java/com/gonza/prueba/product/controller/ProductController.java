package com.gonza.prueba.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gonza.prueba.product.entity.Category;
import com.gonza.prueba.product.entity.Product;
import com.gonza.prueba.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value= "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> listProduct(@RequestParam(name = "categoryId", required = false)
                                                    Long id){
        List<Product> lista = new ArrayList<>();
        if (id == null){
            lista = productService.listAllProducts();
            if (lista.isEmpty()) return ResponseEntity.noContent().build();
        } else {
            lista = productService.findByCategory(Category.builder().id(id).build());

            if (lista.isEmpty()) return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id){

        Product product = productService.getProduct(id);

        if (product == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(product);

    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product,
                                                 BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Product crearProducto = productService.createProduct(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(crearProducto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id,
                                                 @RequestBody Product product){
        product.setId(id);
        Product productDB = productService.updateProduct(product);

        if (productDB == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(productDB);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id){

        Product productDelete = productService.deleteProduct(id);
        if (productDelete == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(productDelete);
    }

    @GetMapping(value = "/{id}/stock")
    public ResponseEntity<Product> updateStock(@PathVariable("id") Long id,
                                               @RequestParam(name = "quantity", required = true)
                                                       Double quantity){

        Product stock = productService.updateStock(id, quantity);
        if (stock == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(stock);

    }

    private String formatMessage(BindingResult result){

        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err -> {
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());

        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString ="";
        try {
                jsonString = objectMapper.writeValueAsString(errorMessage);

        }catch (JsonProcessingException ex){
            ex.printStackTrace();
        }
        return jsonString;
    }
}
