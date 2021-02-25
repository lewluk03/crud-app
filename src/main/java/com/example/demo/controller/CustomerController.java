package com.example.demo.controller;


import com.example.demo.dto.AddModCustomerDTO;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.converter.CustomerEntityDTOConverter;
import com.example.demo.entity.Customer;
import com.example.demo.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RequestMapping("api/customer")
@RestController
public class CustomerController {

    private static Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    private final CustomerService customerService;
    private final CustomerEntityDTOConverter customerEntityDTOConverter;

    @Autowired
    public CustomerController(CustomerService customerService, CustomerEntityDTOConverter customerEntityDTOConverter){
        this.customerService = customerService;
        this.customerEntityDTOConverter = customerEntityDTOConverter;
    }

    @ApiOperation(value = "Gets information about the Customer",notes = "Returns information about the Customer")
    @GetMapping("{customerId}")
    public CustomerDTO getCustomerById(@PathVariable("customerId") Long customerId){
        logger.info("CustomerController - getCustomerById");
        return customerEntityDTOConverter.convertEntity(customerService.findById(customerId));
    }

    @GetMapping
    public List<CustomerDTO> getAllCustomers(){
        logger.info("CustomerController - getAllCustomers");
        return customerService.findAll().stream().map(customerEntityDTOConverter::convertEntity).collect(Collectors.toList());
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Long customerId){
        logger.info("CustomerController - deleteCustomer");
        customerService.deleteCustomer(customerId);
    }

    @PostMapping
    public CustomerDTO addCustomer(@Valid @RequestBody AddModCustomerDTO addModCustomerDTO){
        logger.info("CustomerController - addCustomer");
       return customerEntityDTOConverter.convertEntity(customerService.addCustomer(customerEntityDTOConverter.convertAdModCustomerDTO(addModCustomerDTO)));
    }

    @PutMapping("{customerId}")
    public void updateCustomer(@PathVariable("customerId") Long customerId, @Valid @RequestBody AddModCustomerDTO addModCustomerDTO){
        logger.info("CustomerController - updateCustomer");
        customerService.updateCustomer(customerId, addModCustomerDTO);
    }
    @GetMapping("/export")
    public void exportCustomerToCSV(HttpServletResponse response) throws IOException {
        logger.info("CustomerController - exportCustomerToCSV");
        response.setHeader(
                "Content-Disposition",
                "attachment; filename=users_" + dateFormatter.format(new Date()) + ".csv");
        response.setContentType("text/csv");
        customerService.exportCustomersToCsv(response.getWriter());
    }

    @PostMapping("/import")
    public String importCustomerFromCSV(@RequestParam("file") MultipartFile file) {
        logger.info("CustomerController - importCustomerFromCSV");
        return customerService.importCustomerFromCSV(file);
    }

}
