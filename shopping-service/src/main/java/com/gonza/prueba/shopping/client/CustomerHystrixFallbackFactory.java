package com.gonza.prueba.shopping.client;

import com.gonza.prueba.shopping.model.Customer;
import org.springframework.http.ResponseEntity;

public class CustomerClientFallbackFactory implements CustomerClient{
    @Override
    public ResponseEntity<Customer> getCustomer(Long id) {
        return null;
    }
}
