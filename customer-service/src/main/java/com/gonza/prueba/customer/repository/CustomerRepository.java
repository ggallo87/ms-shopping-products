package com.gonza.prueba.customer.repository;

import com.gonza.prueba.customer.repository.entity.Customer;
import com.gonza.prueba.customer.repository.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    public Customer findByNumberId(String numberId);
    public List<Customer> findByRegion(Region region);
    public List<Customer> findByLastName(String lastName);
}
