package swt6.orm.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @OneToMany(mappedBy = "employee", cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private Set<LogbookEntry> logbookEntries = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ProjectEmployee", joinColumns = @JoinColumn(name = "employeeId"), inverseJoinColumns = @JoinColumn(name = "projectId"))
    private Set<Project> projects = new HashSet<>();

    public Employee() {
    }

    public Employee(String firstName, String lastName, Date dateOfBirth) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<LogbookEntry> getLogbookEntries() {
        return logbookEntries;
    }

    public void setLogbookEntries(Set<LogbookEntry> logbookEntries) {
        this.logbookEntries = logbookEntries;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public void addLogbookEntry(LogbookEntry entry) {
        if (entry.getEmployee() != null) {
            entry.getEmployee().getLogbookEntries().remove(entry);
        }
        logbookEntries.add(entry);
        entry.setEmployee(this);
    }

    public void addProject(Project project) {
        projects.add(project);
        project.setLeader(this);
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", address=" + address
                + ", dateOfBirth=" + dateOfBirth + ", logbookEntries=" + logbookEntries + ", projects=" + projects
                + "]";
    }
}
