package com.crud.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.crud.dto.CustomerRequest;
import com.crud.model.Customer;
import com.crud.storage.CustomerStorage;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private final CustomerStorage customerStorage;

    public CustomerController() {
        this.customerStorage = new CustomerStorage();
    }

    @PostMapping("/")
    ResponseEntity<String> store(@RequestBody CustomerRequest customerRequest) {
        try {
            String customerId = customerStorage.store(customerRequest.getName(), customerRequest.getSurname(),
                    customerRequest.getEmail(), customerRequest.getPassword(), customerRequest.getBirthdate(),
                    LocalDate.now(), null);
            return ResponseEntity.ok("Customer stored with ID: " + customerId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error storing customer");
        }
    }

    @GetMapping("/")
    ResponseEntity<Customer[]> list() {
        try {
            Customer[] customers = customerStorage.list();
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new Customer[0]);
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<Customer> find(@PathVariable String id) {
        try {
            Customer customer = customerStorage.find(id);
            return ResponseEntity.ok(customer);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new Customer());
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<String> update(@PathVariable String id, @RequestBody CustomerRequest customerRequest) {
        try {
            customerStorage.update(id, customerRequest.getName(), customerRequest.getEmail(),
                    customerRequest.getBirthdate(), LocalDate.now());
            return ResponseEntity.ok("Customer updated with ID: " + id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error updating customer");
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable String id) {
        try {
            customerStorage.delete(id);
            return ResponseEntity.ok("Customer deleted with ID: " + id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error deleting customer");
        }
    }

}