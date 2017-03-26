package swt6.orm.hibernate.dao;

import org.junit.*;
import swt6.orm.domain.*;
import swt6.orm.hibernate.HibernatePersistenceManager;
import swt6.orm.persistence.PersistenceManager;

import java.sql.Date;
import java.sql.Time;


public class LogbookEntryDaoTest {
    private static PersistenceManager manager;
    private static LogbookEntryDao logbookEntryDao;


    @BeforeClass
    public static void setUpClass() throws Exception {
        manager = HibernatePersistenceManager.getPersistenceManager();
        logbookEntryDao = manager.getDaoFactory().getLogbookEntryDao();
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
        Assert.assertEquals(0, logbookEntryDao.getAll().size());
        LogbookEntry logbookEntry = new LogbookEntry("activity",
//                new Date(2017, 01, 01),
//                new Date(2017, 01, 03),
                new Time(10, 00, 00),
                new Time(11, 00, 00),
                new Employee("Alexander", "Krämer", new Date(1995, 06, 18)),
                new Phase("name"),
                new Module("name", new Project("project",
                        new Employee("Alexander", "Krämer", new Date(18, 06, 1995)))));


        logbookEntryDao.insert(logbookEntry);
        Assert.assertEquals(1, logbookEntryDao.getAll().size());
    }

    @Test
    public void update() throws Exception {
        Assert.assertEquals(0, logbookEntryDao.getAll().size());
        LogbookEntry logbookEntry = new LogbookEntry("activity",
//                new Date(2017, 01, 01),
//                new Date(2017, 01, 03),
                new Time(10, 00, 00),
                new Time(11, 00, 00),
                new Employee("Alexander", "Krämer", new Date(1995, 06, 18)),
                new Phase("name"),
                new Module("name", new Project("project",
                        new Employee("Alexander", "Krämer", new Date(18, 06, 1995)))));

        logbookEntryDao.insert(logbookEntry);
        Assert.assertEquals(1, logbookEntryDao.getAll().size());

        LogbookEntry entry = logbookEntryDao.findById(logbookEntry.getId());

        entry.setActivity("act");
        logbookEntryDao.update(entry);
        Assert.assertEquals("act", entry.getActivity());
    }

    @Test
    public void delete() throws Exception {
        LogbookEntry logbookEntry = new LogbookEntry("activity",
//                new Date(2017, 01, 01),
//                new Date(2017, 01, 03),
                new Time(10, 00, 00),
                new Time(11, 00, 00),
                new Employee("Alexander", "Krämer", new Date(1995, 06, 18)),
                new Phase("name"),
                new Module("name", new Project("project",
                        new Employee("Alexander", "Krämer", new Date(18, 06, 1995)))));

        logbookEntryDao.insert(logbookEntry);
        Assert.assertEquals(1, logbookEntryDao.getAll().size());
        logbookEntryDao.delete(logbookEntry);
        Assert.assertEquals(0, logbookEntryDao.getAll().size());
    }

    @Test
    public void findById() throws Exception {
        LogbookEntry logbookEntry = new LogbookEntry("activity",
//                new Date(2017, 01, 01),
//                new Date(2017, 01, 03),
                new Time(10, 00, 00),
                new Time(11, 00, 00),
                new Employee("Alexander", "Krämer", new Date(1995, 06, 18)),
                new Phase("name"),
                new Module("name", new Project("project",
                        new Employee("Alexander", "Krämer", new Date(18, 06, 1995)))));
        logbookEntryDao.insert(logbookEntry);

        LogbookEntry ad = logbookEntryDao.findById(logbookEntry.getId());
        Assert.assertEquals(ad.getActivity(), logbookEntry.getActivity());
    }

    @Test
    public void getAll() throws Exception {
        Assert.assertEquals(0, logbookEntryDao.getAll().size());

        LogbookEntry logbookEntry = new LogbookEntry("activity1",
//                new Date(2017, 01, 01),
//                new Date(2017, 01, 03),
                new Time(10, 00, 00),
                new Time(11, 00, 00),
                new Employee("Alexander", "Krämer", new Date(1995, 06, 18)),
                new Phase("name"),
                new Module("name", new Project("project",
                        new Employee("Alexander", "Krämer", new Date(18, 06, 1995)))));
        logbookEntryDao.insert(logbookEntry);


        LogbookEntry logbookEntry2 = new LogbookEntry("activity2",
//                new Date(2017, 01, 01),
//                new Date(2017, 01, 03),
                new Time(12, 00, 00),
                new Time(13, 00, 00),

                new Employee("Alexander", "Krämer", new Date(1995, 06, 18)),
                new Phase("name"),
                new Module("name", new Project("project",
                        new Employee("Alexander", "Krämer", new Date(18, 06, 1995)))));
        logbookEntryDao.insert(logbookEntry2);

        Assert.assertEquals(2, logbookEntryDao.getAll().size());
    }
}