package com.gonza.prueba.customer;

import com.gonza.prueba.customer.repository.CustomerRepository;
import com.gonza.prueba.customer.repository.entity.Customer;
import com.gonza.prueba.customer.repository.entity.Region;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class CustomerRepositoryMockTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void whenFindByNumberId_ThenReturnCustomer(){

        Customer customer = Customer.builder()
                .firstName("gonzalo")
                .lastName("gallo")
                .numberId("32901644")
                .photoUrl("www.url.com")
                .email("gonzalo.hca@gmail.com")
                .region(Region.builder().id(2L).build())
                .state("Arg")
                .build();

        customerRepository.save(customer);

        Customer customerDB = customerRepository.findByNumberId("32901644");
        Assertions.assertThat(customer.getEmail()).isEqualTo(customer.getEmail());
    }

    @Test
    public void whenFindByRegion_ThenReturnListCustomer(){
        Customer customer = Customer.builder()
                .firstName("gato")
                .lastName("gato")
                .numberId("32901645")
                .photoUrl("www.url.com")
                .email("gonzalo.hca@gmail.com")
                .region(Region.builder().id(3L).build())
                .state("Arg")
                .build();

        customerRepository.save(customer);

        List<Customer> list = customerRepository.findByRegion(customer.getRegion());
        Assertions.assertThat(list.size()).isEqualTo(1);
    }

    @Test
    public void whenFindByLastName_ThenReturnListCustomer(){
        Customer customer = Customer.builder()
                .firstName("pablo")
                .lastName("ramos")
                .numberId("33333333")
                .photoUrl("www.url.com")
                .email("gonzal.hca@gmail.com")
                .region(Region.builder().id(2L).build())
                .state("Arg")
                .build();

        customerRepository.save(customer);

        List<Customer> lista = customerRepository.findByLastName(customer.getLastName());
        Assertions.assertThat(lista.size()).isEqualTo(1);

    }
}
