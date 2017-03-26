package swt6.orm.hibernate.dao;

import org.hibernate.Session;
import org.junit.*;
import swt6.orm.domain.Address;
import swt6.orm.domain.AddressId;
import swt6.orm.hibernate.HibernatePersistenceManager;
import swt6.orm.persistence.PersistenceManager;


public class AddressDaoTest {
    private static PersistenceManager manager;
    private static AddressDao addressDao;


    @BeforeClass
    public static void setUpClass() throws Exception {
        manager = HibernatePersistenceManager.getPersistenceManager();
        addressDao = manager.getDaoFactory().getAddressDao();
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
        Assert.assertEquals(0, addressDao.getAll().size());
        Address addr = new Address("zip", "street", "city");
        addressDao.insert(addr);
        Assert.assertEquals(1, addressDao.getAll().size());
    }

    @Test
    public void update() throws Exception {
        Assert.assertEquals(0, addressDao.getAll().size());
        Address addr = new Address("zip", "street", "city");
        addressDao.insert(addr);
        Assert.assertEquals(1, addressDao.getAll().size());
        Address addr1 = addressDao.findById(new AddressId(addr.getStreet(), addr.getZipCode()));
        addr1.setCity("neue City");
        addressDao.update(addr1);
        Assert.assertEquals("neue City", addressDao.findById(new AddressId(addr.getStreet(), addr.getZipCode())).getCity());
    }

    @Test
    public void delete() throws Exception {
        Address addr = new Address("zip", "street", "city");
        addressDao.insert(addr);
        Assert.assertEquals(1, addressDao.getAll().size());
        addressDao.delete(addr);
        Assert.assertEquals(0, addressDao.getAll().size());
    }

    @Test
    public void findById() throws Exception {
        Address addr = new Address("zip", "street", "city");
        addressDao.insert(addr);

        Address ad = addressDao.findById(new AddressId("street", "zip"));
        Assert.assertEquals(ad.getCity(), "city");
        Assert.assertEquals(ad.getStreet(), "street");
        Assert.assertEquals(ad.getZipCode(), "zip");
    }

    @Test
    public void getAll() throws Exception {
        Assert.assertEquals(0, addressDao.getAll().size());
        addressDao.insert(new Address("zip1", "street1", "city1"));
        addressDao.insert(new Address("zip2", "street2", "city2"));
        addressDao.insert(new Address("zip3", "street3", "city3"));
        Assert.assertEquals(3, addressDao.getAll().size());
    }
}