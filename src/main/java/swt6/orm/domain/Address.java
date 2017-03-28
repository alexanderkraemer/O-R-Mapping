package swt6.orm.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(AddressId.class)
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String zipCode;
	
	@Id
	private String street;

	private String city;

	public Address() {
	}

	public Address(String zipCode, String street, String city) {
		this.zipCode = zipCode;
		this.street = street;
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String toString() {
		return getZipCode() + " " + getCity() + ", " + getStreet();
	}
}
