package swt6.orm.common;

import swt6.orm.domain.Address;
import swt6.orm.domain.AddressId;

import java.util.List;

public interface IAddressDao {
    public Address insert(Address empl);
    public Address update(Address empl);
    public void delete(Address empl);
    public Address findById(AddressId id);
    public List<Address> getAll();
}
