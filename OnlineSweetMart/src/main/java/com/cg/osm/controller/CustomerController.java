package com.cg.osm.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cg.osm.entity.Address;
import com.cg.osm.entity.Customer;
import com.cg.osm.error.CustomerNotFoundException;
import com.cg.osm.service.CustomerServiceImpl;

@RestController
public class CustomerController {

	/* Injecting Customer Service Layer into Customer Controller layer. */
	@Autowired
	private CustomerServiceImpl customerservice;
	Logger logger = LoggerFactory.getLogger(CustomerController.class);

	/* Method to add a Customer */
	@PostMapping(path = "/customer")
	public Customer addCustomer(@Valid @RequestBody Customer customer) {
		logger.info("Customer addCustomer()");
		return customerservice.addCustomer(customer);
	}

	/*
	 * Method to delete a customer by ID, and throw appropriate exception if there
	 * is no such customer
	 */
	@DeleteMapping(path = "/customer/{customerId}")
	public String deleteCustomer(@PathVariable int customerId) throws CustomerNotFoundException {
		logger.info("Customer deleteCustomer()");
		return customerservice.deleteCustomer(customerId);
	}

	/*
	 * Method to get ALL customers throw exception if there are no customers
	 */
	@GetMapping(path = "/customer")
	public List<Customer> showAllCustomers() throws CustomerNotFoundException {
		logger.info("Customer showAllCustomers()");
		return customerservice.showAllCustomers();
	}

	/*
	 * Method to update customer details by ID throw exception if no customer is
	 * found.
	 */
	@PutMapping(path = "/customer")
	public Customer updateCustomer(@Valid @RequestBody Customer customer) throws CustomerNotFoundException {
		logger.info("Customer updateCustomer()");
		return customerservice.updateCustomer(customer);
	}

	/*
	 * Retrieving a customer details by ID throw exception if no customer is found
	 * by given ID
	 */
	@GetMapping(path = "/customer/{customerId}")
	public Customer findCustomerById(@PathVariable("customerId") int CustomerId) throws CustomerNotFoundException {
		Optional<Customer> result = customerservice.findCustomerById(CustomerId);
		logger.info("Customer findCustomerById()");
		return result.get();

	}

	/*
	 * Updating address of a customer by providing customer ID throw exception if
	 * there is no such customer with provided ID
	 */
	@PutMapping(path = "/customer/addr/{customerId}")
	public Customer UpdateAddress(@Valid @RequestBody Address address, @PathVariable("customerId") int customerId)
			throws CustomerNotFoundException {
		logger.info("Address UpdateAddress()");
		return customerservice.updateAddress(address, customerId);
	}

	/*
	 * Retrieving a customer by his/her name and throw exception if a customer is
	 * not found with provided name
	 */
	@GetMapping(path = "/customer/name/{username}")
	public Customer findCustomerByName(@PathVariable("username") String username) throws CustomerNotFoundException {
		Optional<Customer> customer = customerservice.findCustomerByName(username);
		logger.info("Customer findCustomerByName()");
		return customer.get();
	}
}
