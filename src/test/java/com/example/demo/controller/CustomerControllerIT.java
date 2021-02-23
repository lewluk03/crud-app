package com.example.demo.controller;

import com.example.demo.DemoApplication;
import com.example.demo.dto.AddModCustomerDTO;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.utils.JsonUtil;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import static com.example.demo.utils.SecurityUtils.httpBasicForTest;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
public class CustomerControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    public void cleanUp(){
        customerRepository.deleteAll();
        customerRepository.flush();
    }

    @Test
    public void getCustomerData() throws Exception {
        Customer customer = addCustomer("John", "Doe");

        mvc.perform(MockMvcRequestBuilders.get("/api/customer/" + customer.getCustomerId())
                .with(httpBasicForTest())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is("John")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is("Doe")))
        ;
    }

    @Test
    public void getAllCustomersData() throws Exception{
            Customer customerOne = addCustomer("John", "Doe");
            Customer customerTwo = addCustomer("Martin", "Kantor");

        mvc.perform(MockMvcRequestBuilders.get("/api/customer/")
                .with(httpBasicForTest())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].firstName", CoreMatchers.hasItems("Martin","John")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].lastName", CoreMatchers.hasItems("Kantor","Doe")))
        ;


    }

    @Test
    public void deleteCustomer() throws Exception{
        Customer customer = addCustomer("John", "Doe");

        mvc.perform(MockMvcRequestBuilders.delete("/api/customer/" + customer.getCustomerId())
                .with(httpBasicForTest())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void addCustomer() throws Exception{
        AddModCustomerDTO addModCustomerDTO = new AddModCustomerDTO();
        addModCustomerDTO.setFirstName("Alina");
        addModCustomerDTO.setLastName("Jakubowska");

        mvc.perform(MockMvcRequestBuilders.post("/api/customer/")
                .with(httpBasicForTest())
                .content(JsonUtil.toJson(addModCustomerDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Customer afterAddCustomer = customerRepository.findAll().iterator().next();
        assertThat(afterAddCustomer.getFirstName()).isEqualTo("Alina");
        assertThat(afterAddCustomer.getLastName()).isEqualTo("Jakubowska");

    }

    @Test
    public void updateCustomer() throws Exception{
        Customer customer = addCustomer("Mateusz", "Paprocki");
        AddModCustomerDTO addModCustomerDTO = new AddModCustomerDTO();
        addModCustomerDTO.setFirstName("Adam");
        addModCustomerDTO.setLastName("Kowalski");

        mvc.perform(MockMvcRequestBuilders.put("/api/customer/" + customer.getCustomerId())
                .with(httpBasicForTest())
                .content(JsonUtil.toJson(addModCustomerDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Customer afterAddCustomer = customerRepository.findAll().iterator().next();
        assertThat(afterAddCustomer.getFirstName()).isEqualTo("Adam");
        assertThat(afterAddCustomer.getLastName()).isEqualTo("Kowalski");

    }

    public Customer addCustomer(String firstName, String lastName) {

        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);

        return customerRepository.saveAndFlush(customer);

    }
}
