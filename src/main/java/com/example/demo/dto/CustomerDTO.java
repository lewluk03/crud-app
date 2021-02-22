package com.example.demo.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CustomerDTO {

    @NotNull
    private final Long customerID;

    @NotBlank
    private final String firstName;

    @NotBlank
    private final String lastName;

    public CustomerDTO(Long customerID, String firstName, String lastName) {
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getCustomerID() {
        return customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
