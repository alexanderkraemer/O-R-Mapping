package swt6.orm.hibernate.dao;

import org.junit.*;
import swt6.orm.domain.*;
import swt6.orm.hibernate.HibernatePersistenceManager;
import swt6.orm.persistence.PersistenceManager;

import java.sql.Date;
import java.sql.Time;


public class ModuleDaoTest {
    private static PersistenceManager manager;
    private static ModuleDao moduleDao;


    @BeforeClass
    public static void setUpClass() throws Exception {
        manager = HibernatePersistenceManager.getPersistenceManager();
        moduleDao = manager.getDaoFactory().getModuleDao();
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
        Assert.assertEquals(0, moduleDao.getAll().size());
        Project p = new Project("project",
                new Employee("Alexander", "Krämer", new Date(18, 06, 1995)));
        Module module = new Module("module", p);


        moduleDao.insert(module);
        Assert.assertEquals(1, moduleDao.getAll().size());
    }

    @Test
    public void update() throws Exception {
        Assert.assertEquals(0, moduleDao.getAll().size());
        Project p = new Project("project",
                new Employee("Alexander", "Krämer", new Date(18, 06, 1995)));
        Module module = new Module("module", p);
        moduleDao.insert(module);
        Assert.assertEquals(1, moduleDao.getAll().size());
        Module module1 = moduleDao.findById(module.getId());
        module1.setName("new module");
        moduleDao.update(module1);
        Assert.assertEquals("new module", module1.getName());
    }

    @Test
    public void delete() throws Exception {
        Project p = new Project("project",
                new Employee("Alexander", "Krämer", new Date(18, 06, 1995)));
        Module module = new Module("module", p);
        moduleDao.insert(module);
        Assert.assertEquals(1, moduleDao.getAll().size());
        moduleDao.delete(module);
        Assert.assertEquals(0, moduleDao.getAll().size());
    }

    @Test
    public void findById() throws Exception {
        Project p = new Project("project",
                new Employee("Alexander", "Krämer", new Date(18, 06, 1995)));
        Module module = new Module("module", p);
        moduleDao.insert(module);

        Module ad = moduleDao.findById(module.getId());
        Assert.assertEquals(ad.getName(), module.getName());
        Assert.assertEquals(ad.getProject(), p);
    }

    @Test
    public void getAll() throws Exception {
        Project p1 = new Project("project",
                new Employee("Alexander", "Krämer", new Date(18, 06, 1995)));
        HibernatePersistenceManager.getPersistenceManager().getDaoFactory().getProjectDao().insert(p1);
        Module module1 = new Module("module", p1);

        Project p2 = new Project("project",
                new Employee("Alexander", "Krämer", new Date(18, 06, 1995)));
        HibernatePersistenceManager.getPersistenceManager().getDaoFactory().getProjectDao().insert(p2);
        Module module2 = new Module("module", p2);

        Project p3 = new Project("project",
                new Employee("Alexander", "Krämer", new Date(18, 06, 1995)));
        HibernatePersistenceManager.getPersistenceManager().getDaoFactory().getProjectDao().insert(p3);
        Module module3 = new Module("module", p3);


        Assert.assertEquals(0, moduleDao.getAll().size());
        moduleDao.insert(module1);
        moduleDao.insert(module2);
        moduleDao.insert(module3);

        Assert.assertEquals(3, moduleDao.getAll().size());
    }
}