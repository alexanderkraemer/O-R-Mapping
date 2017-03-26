package swt6.orm.hibernate.dao;

import org.hibernate.Session;
import swt6.orm.common.IProjectDao;
import swt6.orm.domain.Employee;
import swt6.orm.domain.Project;
import swt6.orm.hibernate.HibernatePersistenceManager;
import swt6.orm.persistence.PersistenceManager;

import java.util.List;

public class ProjectDao implements IProjectDao {
    private HibernatePersistenceManager manager = (HibernatePersistenceManager) PersistenceManager.getPersistenceManager();

    @Override
    public Project insert(Project obj) {
        Session session = manager.getCurrentSession();
        session.saveOrUpdate(obj);
        return obj;
    }

    @Override
    public Project update(Project obj) {
        Session session = manager.getCurrentSession();
        session.update(obj);
        return obj;
    }

    @Override
    public void delete(Project obj) {
        Session session = manager.getCurrentSession();
        session.delete(obj);
    }

    @Override
    public Project findById(long id) {
        Session session = manager.getCurrentSession();
        return session.find(Project.class, id);
    }

    public Project findByName(String name) {
        Session session = manager.getCurrentSession();
        return session.createQuery("select p from Project p where p.name = :name", Project.class)
                .setParameter("name", name).getSingleResult();
    }

    @Override
    public List<Project> getAll() {
        Session session = manager.getCurrentSession();
        return session.createQuery("from Project", Project.class).getResultList();
    }

    @Override
    public void addEmployees(Project project, Employee... employees) {
        Session session = manager.getCurrentSession();

        for (Employee employee: employees) {
            project.getEmployees().add(employee);
            session.saveOrUpdate(employee);
        }
        session.saveOrUpdate(project);
    }

    @Override
    public void removeEmployee(Project project, Employee employee) {
        project.getEmployees().remove(employee);
        employee.getProjects().remove(project);
    }
}
