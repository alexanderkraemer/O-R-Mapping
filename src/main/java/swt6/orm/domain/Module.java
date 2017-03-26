package swt6.orm.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Module implements Serializable {

    public Module(){
    }

    public Module(String name, Project project) {
        this.name = name;
        this.project = project;
    }

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Project project;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
