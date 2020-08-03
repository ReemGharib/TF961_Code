package com.retail.discount.service;

import com.retail.discount.dtos.InvoiceDTO;
import com.retail.discount.dtos.UserDTO;
import com.retail.discount.dtos.UserTypeDTO;
import com.retail.discount.entity.Invoice;
import com.retail.discount.entity.User;
import com.retail.discount.repository.InvoiceRepository;
import com.retail.discount.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The type Invoice service.
 */
@Log4j2
@Service
public class InvoiceService {
    private final InvoiceRepository repository;
    private final UserRepository userRepository;

    /**
     * Instantiates a new Invoice service.
     *
     * @param repository     the repository
     * @param userRepository the user repository
     */
    public InvoiceService(InvoiceRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    /**
     * Get all invoices list.
     *
     * @return the list
     */
    public List<InvoiceDTO> getAll() {
        return this.repository.findAll().stream().map(
                invoice -> InvoiceDTO.builder()
                        .id(invoice.getId())
                        .totalPrice(invoice.getTotalPrice())
                        .priceAfterDiscount(invoice.getPriceAfterDiscount())
                        .userDTO(UserDTO.builder()
                                .id(invoice.getUser().getId())
                                .phone(invoice.getUser().getPhone())
                                .firstName(invoice.getUser().getFirstName())
                                .email(invoice.getUser().getEmail())
                                .lastName(invoice.getUser().getLastName())
                                .startDate(invoice.getUser().getStartDate())
                                .userTypeDTO(UserTypeDTO.builder()
                                        .id(invoice.getUser().getUserType().getId())
                                        .type(invoice.getUser().getUserType().getType()).build()).build())
                        .build()).collect(Collectors.toList());
    }

    /**
     * Calculate bill invoice dto.
     *
     * @param invoiceDTO   the invoice
     * @param oldPrice the old price
     * @param userId   the user id
     * @return the invoice dto
     */
    public InvoiceDTO calculateBill(InvoiceDTO invoiceDTO, double oldPrice, String userId) {


        User user;
        double totalPriceAfterDiscount = 0;
        double roundOldPrice;

        log.info("Find user by id {}", userId);
        user = this.findUserById(userId);

        String userType = user.getUserType().getType();
        switch (userType) {
            case "Affiliate":
                totalPriceAfterDiscount = (oldPrice * 0.9);
                break;
            case "Employee":
                totalPriceAfterDiscount = (oldPrice * 0.7);
                break;
            case "Customer":
                Date currentDate = new Date();
                int differenceDate = currentDate.getYear() - user.getStartDate().getYear();
                if (differenceDate > 2) {
                    totalPriceAfterDiscount = oldPrice * 0.95;
                } else {
                    if (oldPrice >= 100) {
                        roundOldPrice = Math.floor(oldPrice / 100);
                        totalPriceAfterDiscount = oldPrice - (roundOldPrice * 5);
                    }
                }
                break;
            default:
                totalPriceAfterDiscount = oldPrice;
        }


        Invoice invoice = Invoice.builder()
                .id(UUID.randomUUID().toString())
                .totalPrice(oldPrice)
                .priceAfterDiscount("$ " + totalPriceAfterDiscount)
                .user(user).build();

        invoice = repository.save(invoice);

        return InvoiceDTO.builder()
                .id(invoice.getId())
                .priceAfterDiscount(invoice.getPriceAfterDiscount())
                .totalPrice(invoice.getTotalPrice())
                .userDTO(UserDTO.builder()
                        .id(invoice.getUser().getId())
                        .startDate(invoice.getUser().getStartDate())
                        .email(invoice.getUser().getEmail())
                        .lastName(invoice.getUser().getLastName())
                        .firstName(invoice.getUser().getFirstName())
                        .phone(invoice.getUser().getPhone())
                        .userTypeDTO(UserTypeDTO.builder()
                                .id(invoice.getUser().getUserType().getId())
                                .type(invoice.getUser().getUserType().getId())
                                .build())
                        .build())
                .build();
    }

    /**
     *
     * @param user_id
     * @return
     */
    private User findUserById(String user_id) {
        return userRepository.findById(user_id)
                .orElseThrow(() -> new EntityNotFoundException("User_DOES_NOT_EXIST" + user_id));
    }
}
