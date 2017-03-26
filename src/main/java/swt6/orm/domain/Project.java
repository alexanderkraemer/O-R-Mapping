package swt6.orm.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Project implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "projects")
    private Set<Employee> employees = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    private Employee leader;

    public Project() {
        // TODO Auto-generated constructor stub
    }

    public Project(String name, Employee leader) {
        this.leader = leader;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Project [id=" + id + ", name=" + name + "]";
    }

    public Employee getLeader() {
        return leader;
    }

    public void setLeader(Employee leader) {
        this.leader = leader;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        for(Employee e: employees) {
            this.getEmployees().add(e);
            e.addProject(this);
        }
        this.employees = employees;
    }

    public void addEmployee(Project project, Employee employee) {
        project.getEmployees().add(employee);
        employee.addProject(this);
    }
}
