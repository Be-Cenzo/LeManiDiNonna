package catalogoManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.dbunit.Assertion;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class ProdottoModelDSTest{

	private static String expectedPath = "resources/db/expected/catalogoManagement/";
	private static String initPath = "resources/db/init/catalogoManagement/";
	private static String table = "Prodotto";
    private static IDatabaseTester tester;
	private DataSource ds;
    private ProdottoModelDS prodottoModelDS;
    
    @BeforeAll
    static void setUpAll() throws ClassNotFoundException {
        // mem indica che il DB deve andare in memoria
        // test indica il nome del DB
        // DB_CLOSE_DELAY=-1 impone ad H2 di eliminare il DB solo quando il processo della JVM termina
        tester = new JdbcDatabaseTester(org.h2.Driver.class.getName(),
                "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:resources/db/init/init_schema.sql'",
                "prova",
                ""
        );
        // Refresh permette di svuotare la cache dopo un modifica con setDataSet
        // DeleteAll ci svuota il DB manteneno lo schema
        tester.setSetUpOperation(DatabaseOperation.REFRESH);
        tester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
    }

    private static void refreshDataSet(String filename) throws Exception {
        IDataSet initialState = new FlatXmlDataSetBuilder()
                .build(ProdottoModelDSTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Prepara lo stato iniziale di default
        refreshDataSet(initPath + "ProdottoInit.xml");
        ds = Mockito.mock(DataSource.class);
        Mockito.when(ds.getConnection())
        .thenReturn(tester.getConnection().getConnection());
        prodottoModelDS = new ProdottoModelDS(ds);
    }

    @AfterEach
    public void tearDown() throws Exception {
        tester.onTearDown();
    }

    @Test
    public void doRetrieveByKeyTestPresente() {
    	Prodotto expected = new Prodotto(0, "t-shirt", 15f, "bianco", "Hakuna Matata T-Shirt", "hz", "normale", null);
    	Prodotto actual = null;
    	try {
			actual = prodottoModelDS.doRetrieveByKey(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }

    @Test
    public void doRetrieveByKeyTestNonPresente() {
    	Prodotto expected = null;
    	Prodotto actual = null;
    	try {
			actual = prodottoModelDS.doRetrieveByKey(10);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestAsc() throws Exception{
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	expected.add(new Prodotto(2, "t-shirt", 15, "bianco", "Aereoplano T-Shirt", "hz", "normale", null));
    	expected.add(new Prodotto(6, "cappello", 8, "nero", "Cloud", null, null, "N"));
    	expected.add(new Prodotto(4, "borsello", 8, "bianco", "Coffee Lover", null, null, "N"));
    	expected.add(new Prodotto(8, "grembiule", 5, "bianco", "Cucina Bene Grembiule", null, null, "N"));
    	expected.add(new Prodotto(7, "shopper", 5, "bianco", "Disney Shopper", null, null, "N"));
    	expected.add(new Prodotto(0, "t-shirt", 15, "bianco", "Hakuna Matata T-Shirt", "hz", "normale", null));
    	expected.add(new Prodotto(3, "felpa", 25, "grigio", "Live Ride Felpa", "hz", "hoodie", null));
    	expected.add(new Prodotto(5, "borsello", 8, "bianco", "Renna", null, null, "N"));
    	expected.add(new Prodotto(1, "felpa", 25, "beige", "Rosa Felpa", "hz", "hoodie", null));
    	
    	ArrayList<Prodotto> actual = null;
    	try {
			actual = prodottoModelDS.doRetrieveAll("ASC");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestDesc() throws Exception{
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	expected.add(new Prodotto(1, "felpa", 25, "beige", "Rosa Felpa", "hz", "hoodie", null));
    	expected.add(new Prodotto(5, "borsello", 8, "bianco", "Renna", null, null, "N"));
    	expected.add(new Prodotto(3, "felpa", 25, "grigio", "Live Ride Felpa", "hz", "hoodie", null));
    	expected.add(new Prodotto(0, "t-shirt", 15, "bianco", "Hakuna Matata T-Shirt", "hz", "normale", null));
    	expected.add(new Prodotto(7, "shopper", 5, "bianco", "Disney Shopper", null, null, "N"));
    	expected.add(new Prodotto(8, "grembiule", 5, "bianco", "Cucina Bene Grembiule", null, null, "N"));
    	expected.add(new Prodotto(4, "borsello", 8, "bianco", "Coffee Lover", null, null, "N"));
    	expected.add(new Prodotto(6, "cappello", 8, "nero", "Cloud", null, null, "N"));
    	expected.add(new Prodotto(2, "t-shirt", 15, "bianco", "Aereoplano T-Shirt", "hz", "normale", null));
    	
    	ArrayList<Prodotto> actual = null;
    	try {
			actual = prodottoModelDS.doRetrieveAll("DESC");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestVuoto() throws Exception{
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	expected.add(new Prodotto(0, "t-shirt", 15, "bianco", "Hakuna Matata T-Shirt", "hz", "normale", null));
    	expected.add(new Prodotto(1, "felpa", 25, "beige", "Rosa Felpa", "hz", "hoodie", null));
    	expected.add(new Prodotto(2, "t-shirt", 15, "bianco", "Aereoplano T-Shirt", "hz", "normale", null));
    	expected.add(new Prodotto(3, "felpa", 25, "grigio", "Live Ride Felpa", "hz", "hoodie", null));
    	expected.add(new Prodotto(4, "borsello", 8, "bianco", "Coffee Lover", null, null, "N"));
    	expected.add(new Prodotto(5, "borsello", 8, "bianco", "Renna", null, null, "N"));
    	expected.add(new Prodotto(6, "cappello", 8, "nero", "Cloud", null, null, "N"));
    	expected.add(new Prodotto(7, "shopper", 5, "bianco", "Disney Shopper", null, null, "N"));
    	expected.add(new Prodotto(8, "grembiule", 5, "bianco", "Cucina Bene Grembiule", null, null, "N"));
    	
    	ArrayList<Prodotto> actual = null;
    	try {
			actual = prodottoModelDS.doRetrieveAll("");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestNull() throws Exception{
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	expected.add(new Prodotto(0, "t-shirt", 15, "bianco", "Hakuna Matata T-Shirt", "hz", "normale", null));
    	expected.add(new Prodotto(1, "felpa", 25, "beige", "Rosa Felpa", "hz", "hoodie", null));
    	expected.add(new Prodotto(2, "t-shirt", 15, "bianco", "Aereoplano T-Shirt", "hz", "normale", null));
    	expected.add(new Prodotto(3, "felpa", 25, "grigio", "Live Ride Felpa", "hz", "hoodie", null));
    	expected.add(new Prodotto(4, "borsello", 8, "bianco", "Coffee Lover", null, null, "N"));
    	expected.add(new Prodotto(5, "borsello", 8, "bianco", "Renna", null, null, "N"));
    	expected.add(new Prodotto(6, "cappello", 8, "nero", "Cloud", null, null, "N"));
    	expected.add(new Prodotto(7, "shopper", 5, "bianco", "Disney Shopper", null, null, "N"));
    	expected.add(new Prodotto(8, "grembiule", 5, "bianco", "Cucina Bene Grembiule", null, null, "N"));
    	
    	ArrayList<Prodotto> actual = null;
    	try {
			actual = prodottoModelDS.doRetrieveAll(null);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestAltro() {
    	assertThrows(Exception.class, () -> {
    		prodottoModelDS.doRetrieveAll("ascendente");
    	});
    }
	
}
