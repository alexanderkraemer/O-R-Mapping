package swt6.orm.hibernate;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import swt6.orm.persistence.DAOFactory;
import swt6.orm.persistence.PersistenceManager;

public class HibernatePersistenceManager extends PersistenceManager{

    private SessionFactory sessionFactory;
    private Transaction transaction;



    public static PersistenceManager getPersistenceManager(){
        if(persistenceManager == null){
            persistenceManager = new HibernatePersistenceManager();
        }
        return persistenceManager;
    }

    public DAOFactory getDaoFactory(){
        if(daoFactory == null){
            daoFactory = new HibernateDAOFactory();
        }
        return daoFactory;
    }

    @Override
    public void beginTransaction() {
        if(transaction == null) {
            transaction = getCurrentSession().beginTransaction();
        }
    }

    @Override
    public void commit() {
        if(transaction != null){
            transaction.commit();
            transaction = null;
        }
    }

    @Override
    public void rollback() {
        if(transaction != null){
            transaction.rollback();
            transaction = null;
        }
    }

    @Override
    protected void freeRessources() {
        closeSessionFactory();
    }

    private SessionFactory getSessionFactory() {
        if(sessionFactory == null){
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        }
        return sessionFactory;
    }

    private void closeSessionFactory() {
        if(sessionFactory != null) {
            sessionFactory.close();
            sessionFactory = null;
        }
    }

    public Session getCurrentSession() {
        return getSessionFactory().getCurrentSession();
    }

}