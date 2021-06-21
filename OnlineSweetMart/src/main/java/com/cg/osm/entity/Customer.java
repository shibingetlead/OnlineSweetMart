package com.cg.osm.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@JsonIgnoreProperties(allowSetters = true)
public class Customer {

	@Id
	private Integer customerId;
	@Column(name = "Name")
	@NotEmpty(message = "CustomerName cannot be empty")
	private String customerName;
	@Column(name = "EmailId", unique = true)
	@Email(message = "Please check your EmailId")
	@NotEmpty(message = "Email cannot be empty")
	private String email;
	@Column(name = "Address")
	@Autowired
	@Embedded
	@Cascade(CascadeType.ALL)
	@Valid
	private Address address;
	@OneToMany(mappedBy = "customer")
	@ElementCollection(targetClass = SweetOrder.class)
	@JsonManagedReference
	@Cascade(CascadeType.ALL)
	private List<SweetOrder> sweetOrders;

	public Customer() {
	}

		public Customer(Integer customerId, @NotEmpty(message = "CustomerName cannot be empty") String customerName,
			@Email(message = "Please check your EmailId") @NotEmpty(message = "Email cannot be empty") String email,
			@Valid Address address, List<SweetOrder> sweetOrders) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.email = email;
		this.address = address;
		this.sweetOrders = sweetOrders;
	}

		public Integer getCustomerId() {
			return customerId;
		}

		public void setCustomerId(Integer customerId) {
			this.customerId = customerId;
		}

		public String getCustomerName() {
			return customerName;
		}

		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}

		public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

		

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<SweetOrder> getSweetOrders() {
		return sweetOrders;
	}

	public void setSweetOrders(List<SweetOrder> sweetOrders) {
		this.sweetOrders = sweetOrders;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerName=" + customerName + ", email=" + email
				+ ", address=" + address + ", sweetOrders=" + sweetOrders + "]";
	}

	
}
