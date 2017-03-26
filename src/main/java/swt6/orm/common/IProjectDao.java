package swt6.orm.common;

import swt6.orm.domain.Employee;
import swt6.orm.domain.Project;

import java.util.List;

public interface IProjectDao {
    public Project insert(Project empl);
    public Project update(Project empl);
    public void delete(Project empl);
    public Project findById(long id);
    public List<Project> getAll();
    public void addEmployees(Project project, Employee... employees);
    public void removeEmployee(Project project, Employee employee);
}
