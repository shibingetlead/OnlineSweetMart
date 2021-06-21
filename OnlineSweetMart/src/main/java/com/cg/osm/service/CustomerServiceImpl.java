package com.cg.osm.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cg.osm.entity.Address;
import com.cg.osm.entity.Customer;
import com.cg.osm.error.CustomerNotFoundException;
import com.cg.osm.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CustomerServiceImpl implements CustomerService {

	/*
	 * Injecting Customer Repository into Service Layer
	 */
	@Autowired
	private CustomerRepository customerRepository;
	Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Override
	public Customer addCustomer(Customer customer) {
		logger.info("Customer addCustomer()");
		return customerRepository.save(customer);
	}

	@Override
	public Customer updateCustomer(Customer Customer) throws CustomerNotFoundException {
		logger.info("Customer updateCustomer()");
		int customerId = Customer.getCustomerId();
		boolean found = customerRepository.existsById(customerId);
		Optional<Customer> customer = customerRepository.findById(customerId);
		if (found) {
			Customer customer1 = customer.get();
			customer1.setCustomerName(Customer.getCustomerName());
			customer1.setAddress(Customer.getAddress());
			customerRepository.saveAndFlush(customer1);
			return customer1;
		} else {
			throw new CustomerNotFoundException("No such customer found to update");
		}
	}

	@Override
	public String deleteCustomer(int CustomerId) throws CustomerNotFoundException {
		logger.info("Customer deleteCustomer()");
		boolean customer_found = customerRepository.existsById(CustomerId);
		if (customer_found) {
			customerRepository.deleteById(CustomerId);
			return "Successfully deleted";
		} else {
			throw new CustomerNotFoundException("No such customer found to delete with id: " + CustomerId);
		}

	}

	@Override
	public List<Customer> showAllCustomers() throws CustomerNotFoundException {
		logger.info("Customer showAllCustomers()");
		List<Customer> customerList = customerRepository.findAll();
		if (customerList.isEmpty()) {
			throw new CustomerNotFoundException("No such customers found");
		} else {
			return customerList;
		}
	}

	@Override
	public Optional<Customer> findCustomerById(int CustomerId) throws CustomerNotFoundException {
		logger.info("Customer findCustomerById()");
		Optional<Customer> customer = customerRepository.findById(CustomerId);
		if (customer.isPresent()) {
			return customer;
		} else {
			throw new CustomerNotFoundException("No such customer found with id: " + CustomerId);
		}
	}

	@Override
	public Customer updateAddress(Address address, int customerId) throws CustomerNotFoundException {
		logger.info("Address UpdateAddress()");
		Optional<Customer> customer2 = customerRepository.findById(customerId);
		if (customer2.isPresent()) {
			Customer cust1 = customer2.get();
			cust1.setAddress(address);
			customerRepository.saveAndFlush(cust1);
			return cust1;
		} else {
			throw new CustomerNotFoundException("Address cannot be updated as there is no such customer");
		}
	}

	@Override
	public Optional<Customer> findCustomerByName(String CustomerName) throws CustomerNotFoundException {
		logger.info("Customer findCustomerByName()");
		Optional<Customer> customer = customerRepository.findCustomerByName(CustomerName);
		if (customer.isPresent()) {
			return customer;
		} else {
			throw new CustomerNotFoundException("No customer record with the name= " + CustomerName);
		}
	}

}
