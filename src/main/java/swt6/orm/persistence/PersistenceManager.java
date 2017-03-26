package swt6.orm.persistence;

        import org.hibernate.Session;
        import org.hibernate.SessionFactory;
        import org.hibernate.Transaction;
        import org.hibernate.cfg.Configuration;

public abstract class PersistenceManager {
    protected static PersistenceManager persistenceManager;
    protected static DAOFactory daoFactory;

    public static PersistenceManager getPersistenceManager(){
        return persistenceManager;
    }

    public abstract DAOFactory getDaoFactory();

    public static void finishPersistence(){
        if(persistenceManager != null){
            persistenceManager.freeRessources();
            persistenceManager = null;
        }
    }

    public abstract void beginTransaction();
    public abstract void commit();
    public abstract void rollback();
    protected abstract void freeRessources();
}