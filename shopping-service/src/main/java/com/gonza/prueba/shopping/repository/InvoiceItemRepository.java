package com.gonza.prueba.shopping.repository;

import com.gonza.prueba.shopping.repository.entity.InvoiceItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceItems, Long> {
}
