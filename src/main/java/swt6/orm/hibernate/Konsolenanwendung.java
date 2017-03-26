package swt6.orm.hibernate;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.hibernate.Session;
import org.hibernate.query.Query;
import swt6.orm.domain.*;
import swt6.orm.hibernate.dao.*;
import swt6.orm.persistence.DAOFactory;
import swt6.orm.persistence.PersistenceManager;

import javax.persistence.SynchronizationType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.sql.Date;
import java.sql.Time;
import java.util.*;


public class Konsolenanwendung {
    private static AddressDao addressDao;
    private static EmployeeDao employeeDao;
    private static IssueDao issueDao;
    private static LogbookEntryDao logbookEntryDao;
    private static ModuleDao moduleDao;
    private static PhaseDao phaseDao;
    private static ProjectDao projectDao;

    private static DAOFactory df;
    private static PersistenceManager pm;


    public static void setup() {
        pm = HibernatePersistenceManager.getPersistenceManager();
        df = pm.getDaoFactory();

        addressDao = df.getAddressDao();
        employeeDao = df.getEmployeeDao();
        issueDao = df.getIssueDao();
        logbookEntryDao = df.getLogbookEntryDao();
        moduleDao = df.getModuleDao();
        phaseDao = df.getPhaseDao();
        projectDao = df.getProjectDao();
    }

    public static void insertData() {
        pm.beginTransaction();

        Employee pe1 = new PermanentEmployee("Markus", "Krämer", new Date(18, 01, 1991));
        Employee pe2 = new PermanentEmployee("Alexander", "Krämer", new Date(19, 02, 1992));
        Employee pe3 = new PermanentEmployee("Michaela", "Pilz", new Date(20, 03, 1993));
        Employee te4 = new TemporaryEmployee("Rebecca", "Krämer", new Date(21, 04, 1994));
        Employee te5 = new PermanentEmployee("Ute", "Krämer", new Date(22, 05, 1995));
        Employee te6 = new PermanentEmployee("Daniel", "Krämer", new Date(23, 06, 1996));


        employeeDao.insert(pe1);
        employeeDao.insert(pe2);
        employeeDao.insert(pe3);
        employeeDao.insert(te4);
        employeeDao.insert(te5);
        employeeDao.insert(te6);

        Project p1 = new Project("project1", pe1);
        Project p2 = new Project("project2", pe2);
        Project p3 = new Project("project3", pe3);
        Project p4 = new Project("project4", te4);
        Project p5 = new Project("project5", te5);
        Project p6 = new Project("project6", te6);

        p1.addEmployee(p1, pe1);
        p1.addEmployee(p1, pe2);
        p1.addEmployee(p1, pe3);

        p1.setLeader(pe1);

        projectDao.insert(p1);
        projectDao.insert(p2);
        projectDao.insert(p3);
        projectDao.insert(p4);
        projectDao.insert(p5);
        projectDao.insert(p6);

        List<Issue> issues = new LinkedList<>();
        Issue i1 = new Issue(State.OPEN, Priority.HIGH, new Time(3, 10, 0), 10.0, p1);
        Issue i2 = new Issue(State.NEW, Priority.LOW, new Time(6, 0, 0), 50.0, p1);
        Issue i3 = new Issue(State.CLOSED, Priority.NORMAL, new Time(2, 10, 0), 40.0, p1);
        Issue i4 = new Issue(State.REJECTED, Priority.HIGH, new Time(1, 20, 0), 20.0, p1);
        Issue i5 = new Issue(State.NEW, Priority.NORMAL, new Time(1, 30, 0), 90.0, p1);
        Issue i6 = new Issue(State.OPEN, Priority.LOW, new Time(5, 50, 0), 80.0, p1);

        i1.setEmployee(pe1);
        i2.setEmployee(pe2);
        i3.setEmployee(pe3);
        i4.setEmployee(pe1);
        i5.setEmployee(pe2);
        i6.setEmployee(pe3);


        issues.add(i1);
        issues.add(i2);
        issues.add(i3);
        issues.add(i4);
        issues.add(i5);
        issues.add(i6);

        for(Issue issue: issues) {
            issueDao.insert(issue);
        }

        Phase ph1 = new Phase("phase1");
        Phase ph2 = new Phase("phase2");
        Phase ph3 = new Phase("phase3");
        Phase ph4 = new Phase("phase4");
        Phase ph5 = new Phase("phase5");
        Phase ph6 = new Phase("phase6");

        phaseDao.insert(ph1);
        phaseDao.insert(ph2);
        phaseDao.insert(ph3);
        phaseDao.insert(ph4);
        phaseDao.insert(ph5);
        phaseDao.insert(ph6);


        Module m1 = new Module("module1", p1);
        Module m2 = new Module("module2", p2);
        Module m3 = new Module("module3", p3);
        Module m4 = new Module("module4", p4);
        Module m5 = new Module("module5", p5);
        Module m6 = new Module("module6", p6);

        moduleDao.insert(m1);
        moduleDao.insert(m2);
        moduleDao.insert(m3);
        moduleDao.insert(m4);
        moduleDao.insert(m5);
        moduleDao.insert(m6);


        LogbookEntry e1 = new LogbookEntry("activity1",
                //new Date(2017, 1, 1),
                //new Date(2017, 1, 2),
                new Time(10, 00, 00),
                new Time(11, 00, 00),
                pe1, ph1, m1);


        LogbookEntry e2 = new LogbookEntry("activity2",
//                new Date(2017, 2, 2),
//                new Date(2017, 2, 4),
                new Time(11, 00, 00),
                new Time(12, 00, 00),

                pe1, ph2, m1);


        LogbookEntry e3 = new LogbookEntry("activity3",
//                new Date(2017, 3, 8),
//                new Date(2017, 3, 4),
                new Time(13, 00, 00),
                new Time(14, 00, 00),

                pe1, ph3, m1);

        LogbookEntry e4 = new LogbookEntry("activity4",
//                new Date(2017, 4, 8),
//                new Date(2017, 4, 16),
                new Time(15, 00, 00),
                new Time(16, 00, 00),

                pe1, ph4, m1);

        LogbookEntry e5 = new LogbookEntry("activity5",
//                new Date(2017, 5, 16),
//                new Date(2017, 5, 30),
                new Time(17, 00, 00),
                new Time(18, 00, 00),

                pe1, ph5, m1);

        logbookEntryDao.insert(e1);
        logbookEntryDao.insert(e2);
        logbookEntryDao.insert(e3);
        logbookEntryDao.insert(e4);
        logbookEntryDao.insert(e5);

        Address a1 = new Address("zip1", "street1", "city1");
        Address a2 = new Address("zip2", "street2", "city2");
        Address a3 = new Address("zip3", "street3", "city3");
        Address a4 = new Address("zip4", "street4", "city4");
        Address a5 = new Address("zip5", "street5", "city5");
        Address a6 = new Address("zip6", "street6", "city6");


        addressDao.insert(a1);
        addressDao.insert(a2);
        addressDao.insert(a3);
        addressDao.insert(a4);
        addressDao.insert(a5);
        addressDao.insert(a6);


        pm.commit();

        System.out.println("finished...");
    }

    private static void printTitle(String title){
        System.out.println("======================= " + title + " =======================");
    }

    private static Project workflow1 (){
        // Workflow 1
        pm.beginTransaction();

        // Es gibt 3 Angestellt
        Employee e = new Employee("Alexander", "Krämer", new Date(18, 6, 1995));
        Employee e1 = new Employee("Max", "Mustermann", new Date(17, 5, 1998));
        Employee e2 = new Employee("Martina", "Musterfrau", new Date(7, 3, 1988));
        employeeDao.insert(e);
        employeeDao.insert(e1);
        employeeDao.insert(e2);

        // Sie arbeiten an einem projekt. "e" ist Projektleiter
        Project p = new Project("myProject", e);
        Set<Employee> set = new HashSet<>();
        set.add(e);
        set.add(e1);
        set.add(e2);
        p.setEmployees(set);
        projectDao.insert(p);

        // es gibt 3 Issues.
        // ein davon ist neu, eins ist in bearbeitung, eins ist fertig.
        Issue i1 = new Issue(State.NEW, Priority.HIGH, new Time(1, 0, 0), 0.0, p);
        Issue i2 = new Issue(State.OPEN, Priority.NORMAL, new Time(2, 0, 0), 0.4, p);
        Issue i3 = new Issue(State.CLOSED, Priority.NORMAL, new Time(1, 30, 0), 1.0, p);

        i1.setEmployee(e);
        i2.setEmployee(e1);
        i3.setEmployee(e2);


        issueDao.insert(i1);
        issueDao.insert(i2);
        issueDao.insert(i3);


        Phase ph = new Phase("development");
        phaseDao.insert(ph);

        Module m = new Module("module", p);
        moduleDao.insert(m);

        LogbookEntry entry1 = new LogbookEntry("starting issue 1",
//                new Date(2017, 3, 12),
//                new Date(2017, 3, 14),
                new Time(10, 00, 00),
                new Time(11, 00, 00),

                e, ph, m);

        i1.setFinished((float)0.2);
        i1.setZustand(State.OPEN);
        issueDao.update(i1);

        logbookEntryDao.insert(entry1);
        return p;
    }

    private static Project workflow2 (){
        // Workflow 1
        pm.beginTransaction();

        // Es gibt 3 Angestellt
        Employee e = new Employee("Alexander", "Krämer", new Date(18, 6, 1995));
        Employee e1 = new Employee("Max", "Mustermann", new Date(17, 5, 1998));
        Employee e2 = new Employee("Martina", "Musterfrau", new Date(7, 3, 1988));
        employeeDao.insert(e);
        employeeDao.insert(e1);
        employeeDao.insert(e2);

        // Sie arbeiten an einem projekt. "e" ist Projektleiter
        Project p = new Project("myProject", e);
        Set<Employee> set = new HashSet<>();
        set.add(e);
        set.add(e1);
        set.add(e2);
        p.setEmployees(set);
        projectDao.insert(p);

        // es gibt 3 Issues.
        // ein davon ist neu, eins ist in bearbeitung, eins ist fertig.
        Issue i1 = new Issue(State.NEW, Priority.HIGH, new Time(1, 0, 0), 0.0, p);
        Issue i2 = new Issue(State.OPEN, Priority.NORMAL, new Time(2, 0, 0), 0.4, p);
        Issue i3 = new Issue(State.CLOSED, Priority.NORMAL, new Time(1, 30, 0), 1.0, p);

        i1.setEmployee(e);
        i2.setEmployee(e);
        i3.setEmployee(e2);


        issueDao.insert(i1);
        issueDao.insert(i2);
        issueDao.insert(i3);


        Phase ph = new Phase("development");
        phaseDao.insert(ph);

        Module m = new Module("module", p);
        moduleDao.insert(m);


        LogbookEntry entry2 = new LogbookEntry("finishing issue 2",
//                new Date(2017, 2, 12),
//                new Date(2017, 2, 15),
                new Time(10, 00, 00),
                new Time(11, 00, 00),

                e2, ph, m);
        i1.setFinished((float) 1.0);
        i1.setZustand(State.CLOSED);

        issueDao.update(i1);

        logbookEntryDao.insert(entry2);

        return p;
    }

    public static void main(String[] args) {
        setup();

        // Project p = workflow1();
        Project p = workflow2();



        // statistischen Auswertungen für diesen Workflow
        StatisticDao sdao = new StatisticDao();

        List<Issue> list = sdao.getIssuesWithState(p, State.OPEN);

        printTitle("Issues with State \"" + State.OPEN + "\"");
        list.forEach(System.out::println);

        printTitle("Time needed in total for Project \"" + p.getName() + "\"");
        System.out.println(sdao.neededTimeSum(p, false));

        printTitle("Issues for Employee");
        System.out.println(sdao.getIssuesForEmployee(employeeDao.findById(1L) ,State.OPEN));

        printTitle("Issues Time for Employee");
        System.out.println(sdao.getIssueTimeForEmployee(employeeDao.findById(1L) ,State.OPEN, true));

        printTitle("Get Time by Phases");
        System.out.println(sdao.getTimeByPhases(p));


        printTitle("Time remaining in total for Project \"" + p.getName() + "\"");
        System.out.println(sdao.neededTimeSum(p, true));

        printTitle("total time used per employee");
        Map<Employee, Time> map1 = sdao.neededTimePerEmployee(p, false);

        map1.forEach((k, v) -> {
            System.out.println("Employee " + k.getFirstName() + ": " + v);
        });



        printTitle("total time remaining per employee");
        Map<Employee, Time> map2 = sdao.neededTimePerEmployee(p, true);

        map2.forEach((k, v) -> {
            System.out.println("Employee " + k.getFirstName() + ": " + v);
        });

        HibernatePersistenceManager.getPersistenceManager().commit();
    }
}
