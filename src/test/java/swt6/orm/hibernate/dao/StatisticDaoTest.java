package swt6.orm.hibernate.dao;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.hibernate.Session;
import org.junit.*;
import sun.rmi.runtime.Log;
import swt6.orm.domain.*;
import swt6.orm.hibernate.HibernatePersistenceManager;
import swt6.orm.hibernate.Konsolenanwendung;
import swt6.orm.persistence.PersistenceManager;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.*;

import static org.junit.Assert.*;

public class StatisticDaoTest {
    private static PersistenceManager manager;
    private static StatisticDao sdao;


    @BeforeClass
    public static void setUpClass() throws Exception {
        manager = HibernatePersistenceManager.getPersistenceManager();
        sdao = new StatisticDao();
        Konsolenanwendung.setup();
        // Konsolenanwendung.insertData();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        HibernatePersistenceManager.finishPersistence();
    }

    @Before
    public void setUp() throws Exception {
        manager.beginTransaction();
    }

    @After
    public void tearDown() throws Exception {
        manager.rollback();
    }

    @Test
    public void getIssuesWithState() throws Exception {
        ProjectDao pdao = new ProjectDao();
        EmployeeDao edao = new EmployeeDao();
        IssueDao idao = new IssueDao();

        Employee e = new Employee("firstName", "lastname", new Date(18, 6, 1995));
        edao.insert(e);

        Project p = new Project("project1", e);
        pdao.insert(p);


        Issue i1 = new Issue(State.OPEN, Priority.HIGH, new Time(10, 0, 0), .5, p);
        i1.setEmployee(e);
        i1.setProject(p);
        idao.insert(i1);

        List<Issue> list = new LinkedList<>();
        Assert.assertEquals(0, list.size());
        list = sdao.getIssuesWithState(p, State.OPEN);
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void neededTimeSum() throws Exception {
        ProjectDao pdao = new ProjectDao();
        EmployeeDao edao = new EmployeeDao();
        IssueDao idao = new IssueDao();

        Employee e = new Employee("firstName", "lastname", new Date(18, 6, 1995));
        edao.insert(e);

        Project p = new Project("project1", e);
        pdao.insert(p);


        Issue i1 = new Issue(State.OPEN, Priority.HIGH, new Time(10, 0, 0), .5, p);
        i1.setEmployee(e);
        i1.setProject(p);
        idao.insert(i1);


        Assert.assertEquals(sdao.neededTimeSum(p, false), new Time(5,0,0));
    }

    @Test
    public void neededTimePerEmployee() throws Exception {
        ProjectDao pdao = new ProjectDao();
        EmployeeDao edao = new EmployeeDao();
        IssueDao idao = new IssueDao();

        Employee e = new Employee("firstName", "lastname", new Date(18, 6, 1995));
        edao.insert(e);

        Project p = new Project("project1", e);
        pdao.insert(p);
        p.addEmployee(p, e);

        Issue i1 = new Issue(State.OPEN, Priority.HIGH, new Time(10, 0, 0), .5, p);
        i1.setEmployee(e);
        i1.setProject(p);
        idao.insert(i1);

        Assert.assertEquals(sdao.getIssuesForEmployee(e, State.OPEN).size(), 1);
        e.addProject(p);
        edao.update(e);

        Map<Employee, Time> map = new HashMap<>();
        map.put(e, new Time(5, 0, 0));

        Assert.assertEquals(sdao.neededTimePerEmployee(p, true), map);
    }

    @Test
    public void getIssuesForEmployee() throws Exception {
        ProjectDao pdao = new ProjectDao();
        EmployeeDao edao = new EmployeeDao();
        IssueDao idao = new IssueDao();

        Employee e = new Employee("firstName", "lastname", new Date(18, 6, 1995));
        edao.insert(e);

        Project p = new Project("project1", e);
        pdao.insert(p);


        Issue i1 = new Issue(State.OPEN, Priority.HIGH, new Time(10, 0, 0), .5, p);
        i1.setEmployee(e);
        i1.setProject(p);
        idao.insert(i1);

        Assert.assertEquals(sdao.getIssuesForEmployee(e, State.OPEN).size(), 1);

        Issue i2 = new Issue(State.OPEN, Priority.NORMAL, new Time(11, 0, 0), .8, p);
        i2.setEmployee(e);
        i2.setProject(p);
        idao.insert(i2);

        Assert.assertEquals(sdao.getIssuesForEmployee(e, State.OPEN).size(), 2);
    }

    @Test
    public void getIssueTimeForEmployee() throws Exception {
        ProjectDao pdao = new ProjectDao();
        EmployeeDao edao = new EmployeeDao();
        IssueDao idao = new IssueDao();

        Employee e = new Employee("firstName", "lastname", new Date(18, 6, 1995));
        edao.insert(e);

        Project p = new Project("project1", e);
        pdao.insert(p);


        Issue i1 = new Issue(State.OPEN, Priority.HIGH, new Time(1, 0, 0), 1.0, p);
        i1.setEmployee(e);
        i1.setProject(p);
        idao.insert(i1);

        Issue i2 = new Issue(State.OPEN, Priority.NORMAL, new Time(1, 0, 0), 1.0, p);
        i2.setEmployee(e);
        i2.setProject(p);
        idao.insert(i2);
        Assert.assertEquals(sdao.getIssueTimeForEmployee(e, State.OPEN, false), new Time(2, 0, 0));
    }

    @Test
    public void getTimeByPhases() throws Exception {
        ProjectDao pdao = new ProjectDao();
        EmployeeDao edao = new EmployeeDao();
        IssueDao idao = new IssueDao();
        PhaseDao phdao = new PhaseDao();
        LogbookEntryDao enDao = new LogbookEntryDao();
        ModuleDao mdao = new ModuleDao();

        Employee e = new Employee("firstName", "lastname", new Date(18, 6, 1995));
        edao.insert(e);

        Project p = new Project("project1", e);
        pdao.insert(p);


        Issue i1 = new Issue(State.OPEN, Priority.HIGH, new Time(1, 0, 0), 0, p);
        i1.setEmployee(e);
        i1.setProject(p);
        idao.insert(i1);

        Phase ph1 = new Phase("phase1");
        Phase ph2 = new Phase("phase2");
        phdao.insert(ph1);
        phdao.insert(ph2);

        Module m1 = new Module("name", p);
        mdao.insert(m1);


        LogbookEntry en1 = new LogbookEntry("activity1",
//                new Date(2017, 10, 19),
//                new Date(2017, 10, 22),
                new Time(10, 00, 00),
                new Time(11, 00, 00),

                e, ph1, m1);

        LogbookEntry en2 = new LogbookEntry("activity2",
//                new Date(2017, 10, 23),
//                new Date(2017, 10, 26),
                new Time(12, 00, 00),
                new Time(13, 00, 00),

                e, ph2, m1);

        enDao.insert(en1);
        enDao.insert(en2);

        Map<Phase, Time> map = new HashMap<>();
        map.put(ph1, new Time(1, 0, 0));
        map.put(ph2, new Time(1, 0, 0));

        Assert.assertEquals(sdao.getTimeByPhases(p), map);
    }

}