package swt6.orm.hibernate.dao;

import org.junit.*;
import swt6.orm.domain.Employee;
import swt6.orm.domain.Project;
import swt6.orm.domain.Project;
import swt6.orm.hibernate.HibernatePersistenceManager;
import swt6.orm.persistence.PersistenceManager;

import java.sql.Date;


public class ProjectDaoTest {
    private static PersistenceManager manager;
    private static ProjectDao projectDao;
    private static EmployeeDao employeeDao;


    @BeforeClass
    public static void setUpClass() throws Exception {
        manager = HibernatePersistenceManager.getPersistenceManager();
        projectDao = manager.getDaoFactory().getProjectDao();
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
        Assert.assertEquals(0, projectDao.getAll().size());
        Project project = new Project("project",
                new Employee("Alexander", "Krämer", new Date(18, 06, 1995)));


        projectDao.insert(project);
        Assert.assertEquals(1, projectDao.getAll().size());
    }

    @Test
    public void update() throws Exception {
        Assert.assertEquals(0, projectDao.getAll().size());
        Project project = new Project("project",
                new Employee("Alexander", "Krämer", new Date(18, 06, 1995)));
        projectDao.insert(project);
        Assert.assertEquals(1, projectDao.getAll().size());
        Project project1 = projectDao.findById(project.getId());
        project1.setName("new project");
        projectDao.update(project1);
        Assert.assertEquals("new project", project1.getName());
    }

    @Test
    public void delete() throws Exception {
        Project project = new Project("project",
                new Employee("Alexander", "Krämer", new Date(18, 06, 1995)));
        projectDao.insert(project);
        Assert.assertEquals(1, projectDao.getAll().size());
        projectDao.delete(project);
        Assert.assertEquals(0, projectDao.getAll().size());
    }

    @Test
    public void findById() throws Exception {
        Project project = new Project("project",
                new Employee("Alexander", "Krämer", new Date(18, 06, 1995)));
        projectDao.insert(project);

        Project ad = projectDao.findById(project.getId());
        Assert.assertEquals(ad.getName(), project.getName());
    }

    @Test
    public void getAll() throws Exception {
        Employee e1 = new Employee("Alexander", "Krämer", new Date(18, 6, 1995));
        Employee e2 = new Employee("Markus", "Krämer", new Date(18, 6, 1995));
        Employee e3 = new Employee("Daniel", "Krämer", new Date(18, 6, 1995));

        employeeDao.insert(e1);
        employeeDao.insert(e2);
        employeeDao.insert(e3);

        Assert.assertEquals(0, projectDao.getAll().size());


        Project project1 = new Project("project1", e1);
        projectDao.insert(project1);

        Project project2 = new Project("project2", e2);
        projectDao.insert(project2);

        Project project3 = new Project("project3", e3);
        projectDao.insert(project3);



        Assert.assertEquals(3, projectDao.getAll().size());
    }
}