package com.example.demo.service;

import com.example.demo.dto.AddModCustomerDTO;
import com.example.demo.entity.Customer;
import com.example.demo.entity.ImportFile;
import com.example.demo.excetion.ResourceNotFoundException;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.ImportFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CustomerService {

    private static Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;
    private final ImportFileRepository importFileRepository;


    @Autowired
    public CustomerService(CustomerRepository customerRepository,
                           ImportFileRepository importFileRepository) {
        this.customerRepository = customerRepository;
        this.importFileRepository = importFileRepository;
    }

    public Customer findById(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", customerId));
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Transactional
    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Transactional
    public Customer addCustomer(Customer customer) {
       return customerRepository.save(customer);
    }

    @Transactional
    public void updateCustomer(Long customerId, AddModCustomerDTO newCustomer) {
        Customer customer = findById(customerId);
        customer.setFirstName(newCustomer.getFirstName());
        customer.setLastName(newCustomer.getLastName());

        customerRepository.save(customer);
    }

    public void exportCustomersToCsv(Writer writer) throws IOException {
        List<Customer> customerList = findAll();

        ICsvBeanWriter csvWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"Customer ID", "First Name", "Last Name"};
        String[] nameMapping = {"customerId", "firstName", "lastName"};

        csvWriter.writeHeader(csvHeader);

        for (Customer customer : customerList) {
            csvWriter.write(customer, nameMapping);
        }

        csvWriter.close();
    }

    @Transactional
    public String importCustomerFromCSV(MultipartFile file) {

        logger.info("Start loading Customer file");

        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File missing");
        } else if (!file.getOriginalFilename().endsWith(".csv")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please upload a CSV file");
        }

        List<Customer> newCutomerList = new ArrayList<>();

        ICsvBeanReader beanReader = null;
        CellProcessor[] processors = new CellProcessor[]{
                new NotNull(), // First name
                new NotNull(), // Last name
        };

        try {
            long start = System.currentTimeMillis();

            byte[] bytes = file.getBytes();
            beanReader = new CsvBeanReader(
                    new InputStreamReader(new ByteArrayInputStream(bytes))
                    , CsvPreference.STANDARD_PREFERENCE);

            beanReader.getHeader(true); // skip header line

            String[] header = {"firstName", "lastName"};
            Customer bean = null;

            while ((bean = beanReader.read(Customer.class, header, processors)) != null) {
                Customer customer = new Customer();
                customer.setFirstName(bean.getFirstName());
                customer.setLastName(bean.getLastName());

                newCutomerList.add(customer);

            }

            beanReader.close();

            if (newCutomerList.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File was empty");
            }

            customerRepository.saveAll(newCutomerList);

            ImportFile importFile = new ImportFile();
            importFile.setFileName(file.getOriginalFilename());
            importFile.setFileType(file.getContentType());
            importFile.setFileData(file.getBytes());
            importFileRepository.save(importFile);

            long end = System.currentTimeMillis();
            logger.info("Finish loading in {}", start- end);

        } catch (IOException ex) {
            logger.error("Could not import Customer file", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not import Customer file");
        }
        return "File uploaded successful";
    }
}
