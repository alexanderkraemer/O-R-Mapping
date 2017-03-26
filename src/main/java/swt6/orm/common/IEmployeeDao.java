package swt6.orm.common;

import swt6.orm.domain.Employee;
import swt6.orm.domain.LogbookEntry;
import swt6.orm.domain.Project;

import java.util.List;
import java.util.Set;

/**
 * Created by Alexander on 15/03/2017.
 */
public interface IEmployeeDao {
    public Employee insert(Employee empl);
    public Employee update(Employee empl);
    public void delete(Employee empl);
    public Employee findById(long id);
    public List<Employee> getAll();
    public void addLogbookEntries(Employee empl, LogbookEntry... entries);
    public void removeLogbookEntries(Employee employee, LogbookEntry... entry);
    public void addProjects(Employee empl, Project... projects);
    public void removeProjects(Employee empl, Project... projects);
}
