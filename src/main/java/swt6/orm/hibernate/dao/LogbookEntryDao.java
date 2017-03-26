package swt6.orm.hibernate.dao;

import org.hibernate.Session;
import sun.rmi.runtime.Log;
import swt6.orm.common.ILogbookEntryDao;
import swt6.orm.domain.Employee;
import swt6.orm.domain.LogbookEntry;
import swt6.orm.hibernate.HibernatePersistenceManager;
import swt6.orm.persistence.PersistenceManager;

import java.util.List;

public class LogbookEntryDao implements ILogbookEntryDao {

    private HibernatePersistenceManager manager = (HibernatePersistenceManager) PersistenceManager.getPersistenceManager();

    @Override
    public LogbookEntry insert(LogbookEntry empl) {
        Session session = manager.getCurrentSession();
        session.save(empl);
        return empl;
    }

    @Override
    public LogbookEntry update(LogbookEntry empl) {
        Session session = manager.getCurrentSession();
        session.update(empl);
        return empl;
    }

    @Override
    public void delete(LogbookEntry empl) {
        Session session = manager.getCurrentSession();
        session.delete(empl);
    }

    @Override
    public LogbookEntry findById(long id) {
        Session session = manager.getCurrentSession();
        return session.find(LogbookEntry.class, id);
    }

    @Override
    public List<LogbookEntry> getAll() {
        Session session = manager.getCurrentSession();
        return session.createQuery("from LogbookEntry ", LogbookEntry.class).getResultList();
    }
}
