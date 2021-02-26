package com.example.demo.service;

import com.example.demo.dto.AddModCustomerDTO;
import com.example.demo.entity.Customer;
import com.example.demo.entity.ImportFile;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.ImportFileRepository;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Captor
    private ArgumentCaptor<Collection<Customer>> customersArgumentCaptor;

    @Captor
    private ArgumentCaptor<ImportFile> importFileArgumentCaptor;

    @Captor
    private ArgumentCaptor<Long> customerIdArgumentCaptor;

    @Captor
    private ArgumentCaptor<Customer> customerAddArgumentCaptor;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ImportFileRepository importFileRepository;


    @Test
    public void getCustomer() throws Exception{

        //given
        Customer customer =  createCustomer(3l,"John", "Doe");

        given(customerRepository.findById(Mockito.eq(3L))).willReturn(Optional.of(customer));

        //when

        Customer result = customerService.findById(3L);

        //then

        assertThat(result).isNotNull();
        assertThat(result.getLastName()).isEqualTo("Doe");
        assertThat(result.getFirstName()).isEqualTo("John");


    }

    @Test
    public void getAllCustomersData(){

        //given
        given(customerRepository.findAll()).willReturn(Arrays.asList(
                createCustomer(1L, "Jon", "Doe"),
                createCustomer(2L, "Jan", "Kowalski")
        ));

        //when

        List<Customer> customers = customerRepository.findAll();

        //then

        assertThat(customers)
                .hasSize(2)
                .extracting(c -> c.getFirstName() + " " + c.getLastName())
                .containsExactlyInAnyOrder("Jan Kowalski", "Jon Doe");

    }

    @Test
    public void deleteCustomer(){
        //given
        Long customerId = 3L;

        //when
        customerService.deleteCustomer(customerId);

        //then

        verify(customerRepository,times(1)).deleteById(customerIdArgumentCaptor.capture());
        Long expected = customerIdArgumentCaptor.getValue();

        assertThat(expected).isEqualTo(customerId);


    }


    @Test
    public void addCustomer(){
        //given
        Customer customer =  new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");


        //when
        customerService.addCustomer(customer);

        //then
        verify(customerRepository, times(1)).save(customerAddArgumentCaptor.capture());
        Customer expected = customerAddArgumentCaptor.getValue();

        assertThat(expected).isNotNull();
        assertThat(expected).isEqualTo(customer);

    }

    @Test
    public void updateCustomer(){
        //given
        Customer customer =  createCustomer(3L,"John", "Doe");
        AddModCustomerDTO newCustomer = new AddModCustomerDTO();
        newCustomer.setFirstName("Marcin");
        newCustomer.setLastName("Kowalski");

        given(customerRepository.findById(3L)).willReturn(Optional.of(customer));

        //when
        customerService.updateCustomer(3L, newCustomer);

        //then
        verify(customerRepository, times(1)).save(customerAddArgumentCaptor.capture());
        Customer expected = customerAddArgumentCaptor.getValue();

        assertThat(expected).isNotNull();
        assertThat(expected.getLastName()).isEqualTo("Kowalski");
        assertThat(expected.getFirstName()).isEqualTo("Marcin");

    }


    @Test
    public void importCustomerFromCSV() throws IOException {

        //given
        InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream("customers.csv");
        MultipartFile file = new MockMultipartFile("usersImp.csv", "usersImp.csv", "text/csv",fileInputStream);

        //when

        customerService.importCustomerFromCSV(file);

        //then
        verify(customerRepository, times(1)).saveAll(customersArgumentCaptor.capture());
        Collection<Customer> savedCustomers = customersArgumentCaptor.getValue();

        assertThat(savedCustomers)
                .hasSize(2)
                .extracting(c -> c.getFirstName() + " " + c.getLastName())
                .containsExactlyInAnyOrder("Adrian Kowalczyk", "Zbyszek Jakubowski");

        verify(importFileRepository, times(1)).save(importFileArgumentCaptor.capture());
        ImportFile result = importFileArgumentCaptor.getValue();

        assertThat(result).isNotNull();
        assertThat(result.getFileName()).isEqualTo("usersImp.csv");
        assertThat(result.getFileType()).isEqualTo("text/csv");

    }



    @Test
    public void exportCustomerFromCSV() throws IOException {

        //given
        String expected = loadResourceAsString("expected.csv");
        given(customerRepository.findAll()).willReturn(Arrays.asList(
                createCustomer(1L, "Jon", "Doe"),
                createCustomer(2L, "Jan", "Kowalski")
        ));
        StringWriter writer = new StringWriter();

        //when
        customerService.exportCustomersToCsv(writer);

        //then
        assertThat(writer.toString()).isEqualTo(expected);
    }

    private Customer createCustomer(Long id, String firstName, String lastName){
        Customer customer = new Customer();
        customer.setCustomerId(id);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        return customer;
    }

    private String loadResourceAsString(String name) throws IOException {
        //given
        InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream(name);
        return IOUtils.toString(fileInputStream,StandardCharsets.UTF_8);
    }
}
