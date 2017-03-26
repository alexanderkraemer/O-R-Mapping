package swt6.orm.hibernate.dao;

import org.junit.*;
import swt6.orm.domain.*;
import swt6.orm.hibernate.HibernateDAOFactory;
import swt6.orm.hibernate.HibernatePersistenceManager;
import swt6.orm.persistence.PersistenceManager;

import java.sql.Date;
import java.sql.Time;


public class IssueDaoTest {
    private static PersistenceManager manager;
    private static IssueDao issueDao;


    @BeforeClass
    public static void setUpClass() throws Exception {
        manager = HibernatePersistenceManager.getPersistenceManager();
        issueDao = manager.getDaoFactory().getIssueDao();
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
        Assert.assertEquals(0, issueDao.getAll().size());
        Project p = new Project("project",
                new Employee("Alexander", "Krämer", new Date(18, 06, 1995)));
        Issue issue = new Issue(State.CLOSED, Priority.HIGH, Time.valueOf("20:16:55"), 0.55, p);


        issueDao.insert(issue);
        Assert.assertEquals(1, issueDao.getAll().size());
    }

    @Test
    public void update() throws Exception {
        Assert.assertEquals(0, issueDao.getAll().size());
        Project p = new Project("project",
                new Employee("Alexander", "Krämer", new Date(18, 06, 1995)));
        Issue issue = new Issue(State.CLOSED, Priority.HIGH, Time.valueOf("20:16:55"), 0.55, p);
        issueDao.insert(issue);
        Assert.assertEquals(1, issueDao.getAll().size());
        Issue issue1 = issueDao.findById(issue.getId());
        Employee e = new Employee("Alexadner", "Krämer", new Date(1995, 06, 18));
        issue1.setEmployee(e);
        issueDao.update(issue1);
        Assert.assertNotNull(issue1.getEmployee());
    }

    @Test
    public void delete() throws Exception {
        Project p = new Project("project",
                new Employee("Alexander", "Krämer", new Date(18, 06, 1995)));
        Issue issue = new Issue(State.CLOSED, Priority.HIGH, Time.valueOf("20:16:55"), 0.55, p);
        issueDao.insert(issue);
        Assert.assertEquals(1, issueDao.getAll().size());
        issueDao.delete(issue);
        Assert.assertEquals(0, issueDao.getAll().size());
    }

    @Test
    public void findById() throws Exception {
        Project p = new Project("project",
                new Employee("Alexander", "Krämer", new Date(18, 06, 1995)));
        Issue issue = new Issue(State.CLOSED, Priority.HIGH, Time.valueOf("20:16:55"), 0.55, p);
        issueDao.insert(issue);

        Issue ad = issueDao.findById(issue.getId());
        Assert.assertEquals(ad.getFinished(), issue.getFinished(), 0);
        Assert.assertNull(ad.getEmployee());
        Assert.assertEquals(ad.getPriority(), Priority.HIGH);
    }

    @Test
    public void getAll() throws Exception {
        Assert.assertEquals(0, issueDao.getAll().size());

        Project p1 = new Project("project11",
                new Employee("Alexander", "Krämer1", new Date(18, 7, 1995)));


        Project p2 = new Project("project22",
                new Employee("Markus", "Krämer2", new Date(18, 8, 1995)));


        Project p3 = new Project("project33",
                new Employee("Daniel", "Krämer3", new Date(18, 6, 1995)));

        HibernatePersistenceManager.getPersistenceManager().getDaoFactory().getProjectDao().insert(p1);
        HibernatePersistenceManager.getPersistenceManager().getDaoFactory().getProjectDao().insert(p2);
        HibernatePersistenceManager.getPersistenceManager().getDaoFactory().getProjectDao().insert(p3);



        Issue issue1 = new Issue(State.CLOSED, Priority.HIGH, Time.valueOf("20:16:55"), 0.55, p1);
        Issue issue2 = new Issue(State.OPEN, Priority.NORMAL, Time.valueOf("20:17:55"), 0.55, p2);
        Issue issue3 = new Issue(State.NEW, Priority.LOW, Time.valueOf("20:18:55"), 0.55, p3);

        issueDao.insert(issue1);
        issueDao.insert(issue2);
        issueDao.insert(issue3);

        Assert.assertEquals(3, issueDao.getAll().size());
    }
}