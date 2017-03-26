package swt6.orm.hibernate.dao;

import org.junit.*;
import swt6.orm.domain.*;
import swt6.orm.hibernate.HibernatePersistenceManager;
import swt6.orm.persistence.PersistenceManager;
import swt6.util.DateUtil;

import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by Alexander on 19/03/2017.
 */
public class EmployeeDaoTest {


    private static PersistenceManager manager;
    private static EmployeeDao employeeDao = HibernatePersistenceManager.getPersistenceManager().getDaoFactory().getEmployeeDao();


    @BeforeClass
    public static void setUpClass() throws Exception {
        manager = HibernatePersistenceManager.getPersistenceManager();
        employeeDao = manager.getDaoFactory().getEmployeeDao();
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
    public void insert() throws Exception {
        Assert.assertEquals(0, employeeDao.getAll().size());
        Employee emp = new Employee("Max", "Mustermann", DateUtil.getDate(1999, 01, 01));
        employeeDao.insert(emp);
        Assert.assertEquals(1, employeeDao.getAll().size());
    }

    @Test
    public void update() throws Exception {
        Assert.assertEquals(0, employeeDao.getAll().size());
        Employee emp = new Employee("Max", "Mustermann", DateUtil.getDate(1999, 01, 01));
        employeeDao.insert(emp);
        Assert.assertEquals(1, employeeDao.getAll().size());
        Employee emp1 = employeeDao.findById(emp.getId());
        emp1.setFirstName("Alexander");
        employeeDao.update(emp1);
        Assert.assertEquals("Alexander", employeeDao.findById(emp1.getId()).getFirstName());
    }

    @Test
    public void delete() throws Exception {
        Assert.assertEquals(0, employeeDao.getAll().size());
        Employee emp = new Employee("Max", "Mustermann", DateUtil.getDate(1999, 01, 01));
        employeeDao.insert(emp);
        Assert.assertEquals(1, employeeDao.getAll().size());
        Employee emp1 = employeeDao.findById(emp.getId());
        employeeDao.delete(emp1);
        Assert.assertEquals(0, employeeDao.getAll().size());
    }

    @Test
    public void findById() throws Exception {
        Employee em = new Employee("Max", "Mustermann", DateUtil.getDate(1999, 01, 01));
        Assert.assertNull(em.getId());
        employeeDao.insert(em);

        Employee employee = employeeDao.findById(em.getId());
        Assert.assertNotNull(employee);
        Assert.assertEquals("Max", employee.getFirstName());
        Assert.assertEquals("Mustermann", employee.getLastName());
        Assert.assertEquals(DateUtil.getDate(1999, 01, 01), employee.getDateOfBirth());
    }

    @Test
    public void getAll() throws Exception {
        Assert.assertEquals(0, employeeDao.getAll().size());
        employeeDao.insert(new Employee("Max", "Mustermann", DateUtil.getDate(1990, 01, 01)));
        employeeDao.insert(new Employee("Hans", "Müller", DateUtil.getDate(1991, 02, 02)));
        employeeDao.insert(new Employee("Thomas", "Berger", DateUtil.getDate(1992, 03, 03)));
        Assert.assertEquals(3, employeeDao.getAll().size());
    }

    @Test
    public void addLogbookEntries() throws Exception {
        Employee em = new Employee("Max", "Mustermann", DateUtil.getDate(1999, 01, 01));
        employeeDao.insert(em);
        Employee employee = employeeDao.findById(em.getId());
        Assert.assertNotNull(employee);

        HashSet<LogbookEntry> list = new HashSet<>();
        LogbookEntry l1 = new LogbookEntry("activity1",
//                new Date(2017, 01, 01),
//                new Date(2017, 01, 03),
                new Time(10, 00, 00),
                new Time(11, 00, 00),
                new Employee("Alexander", "Krämer", new Date(1995, 06, 18)),
                new Phase("name"),
                new Module("name", new Project("project",
                        new Employee("Alexander", "Krämer", new Date(18, 06, 1995)))));

        LogbookEntry l2 = new LogbookEntry("activity2",
//                new Date(2017, 01, 01),
//                new Date(2017, 01, 03),
                new Time(12, 00, 00),
                new Time(13, 00, 00),

                new Employee("Alexander", "Krämer", new Date(1995, 06, 18)),
                new Phase("name"),
                new Module("name", new Project("project",
                        new Employee("Alexander", "Krämer", new Date(18, 06, 1995)))));

        LogbookEntry l3 = new LogbookEntry("activity3",
//                new Date(2017, 01, 01),
//                new Date(2017, 01, 03),
                new Time(14, 00, 00),
                new Time(15, 00, 00),

                new Employee("Alexander", "Krämer", new Date(1995, 06, 18)),
                new Phase("name"),
                new Module("name", new Project("project",
                        new Employee("Alexander", "Krämer", new Date(18, 06, 1995)))));

        list.add(l1);
        list.add(l2);
        list.add(l3);

        Assert.assertEquals(0, employee.getLogbookEntries().size());
        employee.setLogbookEntries(list);
        Assert.assertEquals(3, employee.getLogbookEntries().size());
    }

    @Test
    public void removeLogbookEntry() throws Exception {
        Employee emp = new Employee("Max", "Mustermann", DateUtil.getDate(1999, 01, 01));
        employeeDao.insert(emp);
        Employee employee = employeeDao.findById(emp.getId());

        HashSet<LogbookEntry> list = new HashSet<>();
        LogbookEntry l1 = new LogbookEntry("activity1",
//                new Date(2017, 01, 01),
//                new Date(2017, 01, 03),
                new Time(10, 00, 00),
                new Time(11, 00, 00),
                new Employee("Alexander", "Krämer", new Date(1995, 06, 18)),
                new Phase("name"),
                new Module("name", new Project("project",
                        new Employee("Alexander", "Krämer", new Date(18, 06, 1995)))));

        LogbookEntry l2 = new LogbookEntry("activity2",
//                new Date(2017, 01, 01),
//                new Date(2017, 01, 03),
                new Time(12, 00, 00),
                new Time(13, 00, 00),

                new Employee("Alexander", "Krämer", new Date(1995, 06, 18)),
                new Phase("name"),
                new Module("name", new Project("project",
                        new Employee("Alexander", "Krämer", new Date(18, 06, 1995)))));

        LogbookEntry l3 = new LogbookEntry("activity3",
//                new Date(2017, 01, 01),
//                new Date(2017, 01, 03),
                new Time(14, 00, 00),
                new Time(15, 00, 00),

                new Employee("Alexander", "Krämer", new Date(1995, 06, 18)),
                new Phase("name"),
                new Module("name", new Project("project",
                        new Employee("Alexander", "Krämer", new Date(18, 06, 1995)))));

        list.add(l1);
        list.add(l2);
        list.add(l3);


        employee.setLogbookEntries(list);

        Assert.assertEquals(3, employee.getLogbookEntries().size());

        list.removeIf(value -> value.getActivity().equals("activity1"));

        Assert.assertEquals(2, employee.getLogbookEntries().size());
    }

    @Test
    public final void testAddProject() {
        Employee employee = new Employee("Max", "Mustermann", DateUtil.getDate(1990, 01, 01));
        employeeDao.insert(employee);
        Assert.assertEquals(0, employeeDao.findById(employee.getId()).getProjects().size());

        Project project = new Project("project",
                new Employee("Alexander", "Krämer", new Date(18, 06, 1995)));

        employeeDao.addProjects(employee, project);

        Assert.assertEquals(1, employeeDao.findById(employee.getId()).getProjects().size());
    }

    @Test
    public final void testRemoveProject() {
        Employee employee = new Employee("Max", "Mustermann", DateUtil.getDate(1990, 01, 01));
        employeeDao.insert(employee);
        Project project = new Project("project",
                new Employee("Alexander", "Krämer", new Date(18, 06, 1995)));
        Project project2 = new Project("project",
                new Employee("Alexander", "Krämer", new Date(18, 06, 1995)));
        employeeDao.addProjects(employee, project, project2);

        Assert.assertEquals(2, employeeDao.findById(1L).getProjects().size());

        employeeDao.removeProjects(employee, project2);

        Assert.assertEquals(1, employee.getProjects().size());
    }

}