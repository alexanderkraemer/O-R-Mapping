package swt6.orm.hibernate.dao;

import org.hibernate.Session;
import swt6.orm.common.IPhaseDao;
import swt6.orm.domain.Phase;
import swt6.orm.hibernate.HibernatePersistenceManager;
import swt6.orm.persistence.PersistenceManager;

import java.util.List;

public class PhaseDao implements IPhaseDao {
    private HibernatePersistenceManager manager = (HibernatePersistenceManager) PersistenceManager.getPersistenceManager();
    
    @Override
    public Phase insert(Phase obj) {
        Session session = manager.getCurrentSession();
        session.saveOrUpdate(obj);
        return obj;
    }

    @Override
    public Phase update(Phase obj) {
        Session session = manager.getCurrentSession();
        session.update(obj);
        return obj;
    }

    @Override
    public void delete(Phase obj) {
        Session session = manager.getCurrentSession();
        session.delete(obj);
    }

    @Override
    public Phase findById(long id) {
        Session session = manager.getCurrentSession();
        return session.find(Phase.class, id);
    }

    public List<Phase> getAll() {
        Session session = manager.getCurrentSession();
        return session.createQuery("from Phase", Phase.class).getResultList();
    }
}
