package com.example.demo.controller;

import com.example.demo.DemoApplication;
import com.example.demo.entity.Customer;
import com.example.demo.service.CustomerService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;


@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
public class CustomerControllerIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService customerService;



}
