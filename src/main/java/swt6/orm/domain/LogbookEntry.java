package swt6.orm.domain;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class LogbookEntry implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String activity;

    // @Temporal(TemporalType.TIME)
    private Time startTime;
    // @Temporal(TemporalType.TIME)
    private Time endTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Phase phase;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Module module;

    public LogbookEntry() {
    }

    public LogbookEntry(String activity, Time startTime, Time endTime, Employee emp, Phase phase, Module module) {
        this.employee = emp;
        this.activity = activity;
        this.startTime = startTime;
        this.endTime = endTime;
        this.phase = phase;
        this.module = module;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "LogbookEntry [id=" + id + ", activity=" + activity + ", startTime=" + startTime + ", endTime=" + endTime
                + "]";
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
}
