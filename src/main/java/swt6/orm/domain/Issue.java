package swt6.orm.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;

@Entity
public class Issue implements Serializable{

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private State zustand;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Priority priority;

    private Time estimatedTime;

    private double finished;

    @ManyToOne(cascade = CascadeType.ALL)
    private Employee employee;

    @ManyToOne(cascade = CascadeType.ALL)
    private Project project;

    public Issue() {
    }

    public Issue(State zustand, Priority priority, Time estimatedTime, double finished, Project project) {
        this.zustand = zustand;
        this.priority = priority;
        this.estimatedTime = estimatedTime;
        this.finished = finished;
        this.project = project;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public State getZustand() {
        return zustand;
    }

    public void setZustand(State zustand) {
        this.zustand = zustand;
    }

    public swt6.orm.domain.Priority getPriority() {
        return priority;
    }

    public void setPriority(swt6.orm.domain.Priority priority) {
        this.priority = priority;
    }

    public Time getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Time estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public double getFinished() {
        return finished;
    }

    public void setFinished(float finished) {
        if(finished >= 0.0 && finished <= 1.0) {
            this.finished = finished;
        }
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String toString(){
        return "Issue " + this.getId() + ": [" + getZustand() + "] Finished Status: " + getFinished() + "%; Estimated Time: " + getEstimatedTime();
    }
}
