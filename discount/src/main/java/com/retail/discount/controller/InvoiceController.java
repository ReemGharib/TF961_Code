package com.retail.discount.controller;

import com.retail.discount.dtos.InvoiceDTO;
import com.retail.discount.entity.Invoice;
import com.retail.discount.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("invoice")
public class InvoiceController {

    private final InvoiceService service;

    public InvoiceController(InvoiceService service) {
        this.service = service;
    }

    @GetMapping("getAllInvoices")
    public List<InvoiceDTO> getAll(){
        return service.getAll();
    }

    @PostMapping("calculateInvoice/{old_price}/{user_id}")
    public InvoiceDTO calculate(@RequestBody InvoiceDTO invoiceDTO,
                             @PathVariable("old_price") double old_price,
                             @PathVariable("user_id") String user_id){

        return service.calculateBill(invoiceDTO, old_price, user_id);
    }
}
