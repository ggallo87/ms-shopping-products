package com.gonza.prueba.shopping.service;

import com.gonza.prueba.shopping.repository.entity.Invoice;

import java.util.List;

public interface InvoiceService {

    public Invoice createInvoice(Invoice invoice);
    public Invoice updateInvoice(Invoice invoice);
    public Invoice deleteInvoice(Invoice invoice);
    public Invoice getInvoice(Long id);

    List<Invoice> findInvoiceAll();
}
