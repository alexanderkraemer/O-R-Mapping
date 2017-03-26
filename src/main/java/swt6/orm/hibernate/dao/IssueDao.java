package swt6.orm.hibernate.dao;

import org.hibernate.Session;
import swt6.orm.common.IIssueDao;
import swt6.orm.domain.*;
import swt6.orm.hibernate.HibernatePersistenceManager;
import swt6.orm.persistence.PersistenceManager;

import java.sql.Time;
import java.util.List;

public class IssueDao implements IIssueDao {
    private HibernatePersistenceManager manager = (HibernatePersistenceManager) PersistenceManager.getPersistenceManager();

    @Override
    public Issue insert(Issue obj) {
        Session session = manager.getCurrentSession();
        session.saveOrUpdate(obj);
        return obj;
    }

    @Override
    public Issue update(Issue obj) {
        Session session = manager.getCurrentSession();
        session.update(obj);
        return obj;
    }

    @Override
    public void delete(Issue obj) {
        Session session = manager.getCurrentSession();
        session.delete(obj);
    }

    @Override
    public Issue findById(long id) {
        Session session = manager.getCurrentSession();
        return session.find(Issue.class, id);
    }

    @Override
    public List<Issue> getAll() {
        Session session = manager.getCurrentSession();
        return session.createQuery("from Issue ", Issue.class).getResultList();
    }
}
