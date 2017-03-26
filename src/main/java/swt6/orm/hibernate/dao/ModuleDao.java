package swt6.orm.hibernate.dao;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.hibernate.Session;
import swt6.orm.common.IModuleDao;
import swt6.orm.domain.Module;
import swt6.orm.hibernate.HibernatePersistenceManager;
import swt6.orm.persistence.PersistenceManager;

import java.util.List;

public class ModuleDao implements IModuleDao {

    private HibernatePersistenceManager manager = (HibernatePersistenceManager) PersistenceManager.getPersistenceManager();

    @Override
    public Module insert(Module obj) {
        Session session = manager.getCurrentSession();
        session.saveOrUpdate(obj);
        return obj;
    }

    @Override
    public Module update(Module obj) {
        Session session = manager.getCurrentSession();
        session.update(obj);
        return obj;
    }

    @Override
    public void delete(Module obj) {
        Session session = manager.getCurrentSession();
        session.delete(obj);
    }

    @Override
    public Module findById(long id) {
        Session session = manager.getCurrentSession();
        return session.find(Module.class, id);
    }

    public List<Module> getAll() {
        Session session = manager.getCurrentSession();
        return session.createQuery("from Module", Module.class).getResultList();
    }
}
