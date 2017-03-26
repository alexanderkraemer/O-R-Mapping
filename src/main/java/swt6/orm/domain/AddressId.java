package swt6.orm.domain;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class AddressId implements Serializable {

	private static final long serialVersionUID = 1L;
	private String street;
	private String zipCode;

	public AddressId() {
	}

	public AddressId(String street, String zipCode) {
		this.street = street;
		this.zipCode = zipCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public boolean equals(Object other) {
		if (other == null || !(other instanceof AddressId))
			return false;
		AddressId that = (AddressId) other;
		return this.street.equals(that.street) && this.zipCode.equals(that.zipCode);
	}
}
