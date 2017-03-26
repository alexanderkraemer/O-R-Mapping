package swt6.orm.hibernate.dao;

import org.hibernate.Session;
import swt6.orm.common.IAddressDao;
import swt6.orm.domain.Address;
import swt6.orm.domain.AddressId;
import swt6.orm.domain.Employee;
import swt6.orm.hibernate.HibernatePersistenceManager;
import swt6.orm.persistence.PersistenceManager;

import java.util.List;

public class AddressDao implements IAddressDao {
    private HibernatePersistenceManager manager = (HibernatePersistenceManager) PersistenceManager.getPersistenceManager();

    @Override
    public Address insert(Address addr) {
        Session session = manager.getCurrentSession();

        session.saveOrUpdate(addr);
        return addr;
    }

    @Override
    public Address update(Address addr) {
        Session session = manager.getCurrentSession();
        session.update(addr);
        return addr;
    }

    @Override
    public void delete(Address addr) {
        Session session = manager.getCurrentSession();
        session.delete(addr);
    }

    @Override
    public Address findById(AddressId id) {
        Session session = manager.getCurrentSession();
        return session.find(Address.class, id);
    }

    @Override
    public List<Address> getAll(){
        Session session = manager.getCurrentSession();
        return session.createQuery("from Address ", Address.class).getResultList();
    }
}
