package swt6.orm.hibernate.dao;

import org.hibernate.Session;
import swt6.orm.common.IEmployeeDao;
import swt6.orm.domain.Employee;
import swt6.orm.domain.LogbookEntry;
import swt6.orm.domain.Project;
import swt6.orm.hibernate.HibernatePersistenceManager;
import swt6.orm.persistence.PersistenceManager;

import javax.persistence.Entity;
import java.util.List;

public class EmployeeDao implements IEmployeeDao {
    private HibernatePersistenceManager manager = (HibernatePersistenceManager) PersistenceManager.getPersistenceManager();

    @Override
    public Employee insert(Employee empl) {
        Session session = manager.getCurrentSession();
        session.save(empl);

        return empl;
    }

    @Override
    public Employee update(Employee empl) {
        Session session = manager.getCurrentSession();
        session.update(empl);
        return empl;
    }

    @Override
    public void delete(Employee empl) {
        Session session = manager.getCurrentSession();
        session.delete(empl);
    }

    @Override
    public Employee findById(long id) {
        Session session = manager.getCurrentSession();
        return session.find(Employee.class, id);
    }

    @Override
    public List<Employee> getAll() {
        Session session = manager.getCurrentSession();
        return session.createQuery("from Employee ", Employee.class).getResultList();
    }

    @Override
    public void addLogbookEntries(Employee empl, LogbookEntry... entries) {
        Session session = manager.getCurrentSession();

        for (LogbookEntry entry : entries) {
            empl.addLogbookEntry(entry);
            session.saveOrUpdate(entry);
        }
        session.saveOrUpdate(empl);
    }

    public void removeLogbookEntries(Employee employee, LogbookEntry... entries) {
        for (LogbookEntry entry: entries) {

            manager.getCurrentSession().merge(entry);
            manager.getCurrentSession().merge(employee);

            employee.getLogbookEntries().remove(entry);

            manager.getCurrentSession().delete(entry);
        }
    }

    public void addProjects(Employee empl, Project... projects) {
        Session session = manager.getCurrentSession();

        for (Project project : projects) {
            empl.addProject(project);
            session.saveOrUpdate(project);
        }
        session.saveOrUpdate(empl);
    }

    public void removeProjects(Employee empl, Project... projects) {
        for(Project project: projects) {
            manager.getCurrentSession().merge(empl);
            manager.getCurrentSession().merge(project);
            empl.getProjects().remove(project);
            manager.getCurrentSession().delete(project);
        }
    }


}
