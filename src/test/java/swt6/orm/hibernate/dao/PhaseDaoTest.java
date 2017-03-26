package swt6.orm.hibernate.dao;

import org.junit.*;
import swt6.orm.domain.Phase;
import swt6.orm.domain.Project;
import swt6.orm.hibernate.HibernatePersistenceManager;
import swt6.orm.persistence.PersistenceManager;


public class PhaseDaoTest {
    private static PersistenceManager manager;
    private static PhaseDao phaseDao;


    @BeforeClass
    public static void setUpClass() throws Exception {
        manager = HibernatePersistenceManager.getPersistenceManager();
        phaseDao = manager.getDaoFactory().getPhaseDao();
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
        Assert.assertEquals(0, phaseDao.getAll().size());
        Phase phase = new Phase("phase");


        phaseDao.insert(phase);
        Assert.assertEquals(1, phaseDao.getAll().size());
    }

    @Test
    public void update() throws Exception {
        Assert.assertEquals(0, phaseDao.getAll().size());
        Phase phase = new Phase("phase");
        phaseDao.insert(phase);
        Assert.assertEquals(1, phaseDao.getAll().size());
        Phase phase1 = phaseDao.findById(phase.getId());
        phase1.setName("new phase");
        phaseDao.update(phase1);
        Assert.assertEquals("new phase", phase1.getName());
    }

    @Test
    public void delete() throws Exception {
        Phase phase = new Phase("phase");
        phaseDao.insert(phase);
        Assert.assertEquals(1, phaseDao.getAll().size());
        phaseDao.delete(phase);
        Assert.assertEquals(0, phaseDao.getAll().size());
    }

    @Test
    public void findById() throws Exception {
        Phase phase = new Phase("phase");
        phaseDao.insert(phase);

        Phase ad = phaseDao.findById(phase.getId());
        Assert.assertEquals(ad.getName(), phase.getName());
    }

    @Test
    public void getAll() throws Exception {
        Assert.assertEquals(0, phaseDao.getAll().size());
        phaseDao.insert(new Phase("phase1"));
        phaseDao.insert(new Phase("phase2"));
        phaseDao.insert(new Phase("phase3"));
        Assert.assertEquals(3, phaseDao.getAll().size());
    }
}