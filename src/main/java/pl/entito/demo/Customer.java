package pl.entito.demo;

import lombok.Data;

@Data
public class Customer {

	final private long id;
	final private String firstName;
	final private String lastName;

	public Customer(long id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}

}
