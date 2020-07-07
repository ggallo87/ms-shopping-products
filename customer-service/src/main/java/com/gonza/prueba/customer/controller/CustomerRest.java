package com.gonza.prueba.customer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gonza.prueba.customer.repository.entity.Customer;
import com.gonza.prueba.customer.repository.entity.Region;
import com.gonza.prueba.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping("/customers")
public class CustomerRest {

    @Autowired
    CustomerService customerService;

    // -------------------Retrieve All Customers--------------------------------------------

    @GetMapping()
    public ResponseEntity<List<Customer>> listAllCustomers(@RequestParam(name = "regionId", required = false)
                                                           Long regionId){

        List<Customer> list = new ArrayList<>();
        if (regionId == null){
            list = customerService.findCustomerAll();
            if (list.isEmpty()) return ResponseEntity.noContent().build();

        }else {
            Region region = new Region();
            region.setId(regionId);
            list = customerService.findCustomerByRegion(region);
            if (list.isEmpty()){
                log.error("Customer with Region {} not found.",regionId);
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.ok(list);
    }

    // -------------------Retrieve Single Customer------------------------------------------

    @GetMapping(value = "/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id")Long id){

        log.info("Fetching Customer with id {}", id);
        Customer customer = customerService.getCustomer(id);
        if (customer == null){
            log.error("Customer with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    // -------------------Create a Customer-------------------------------------------
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer,
                                                   BindingResult result){
        log.info("Creating Customer {}", customer);
        if (result.hasErrors()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                this.formatMessage(result));

        Customer customerDB = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerDB);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long id,
                                                   @RequestBody Customer customer){
        log.info("Updating Customer with id{}", id);

        Customer customerDB = customerService.getCustomer(id);
        if (customerDB == null){
            log.error("Unable to update, Customer with id {} not found", id);
            return ResponseEntity.notFound().build();
        }

        customer.setId(id);
        customerDB = customerService.updateCustomer(customer);

        return ResponseEntity.ok(customer);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("id") Long id){

        log.info("Fetching and Deleting Customer with id {}", id);

        Customer customerDB = customerService.getCustomer(id);
        if (customerDB == null){
            log.error("Unable to delete. Customer with id {} not found", id);
            return ResponseEntity.notFound().build();
        }

        customerDB = customerService.deleteCustomer(customerDB);
        return ResponseEntity.ok(customerDB);

    }

    private String formatMessage(BindingResult result) {

        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err -> {
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());

        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .message(errors)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json= " ";
        try {
                json = objectMapper.writeValueAsString(errorMessage);

        }catch (JsonProcessingException jsonProcessingException){
            jsonProcessingException.printStackTrace();
        }
        return json;
    }
}
