package catalogoManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.dbunit.Assertion;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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
    
    @Test
    public void doRetrieveAllTestAscFiltriRicerca() throws Exception{
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	expected.add(new Prodotto(3, "felpa", 25, "grigio", "Live Ride Felpa", "hz", "hoodie", null));
    	expected.add(new Prodotto(1, "felpa", 25, "beige", "Rosa Felpa", "hz", "hoodie", null));
    	
    	ArrayList<String> filtri = new ArrayList<String>();
    	filtri.add("t-shirt");
    	filtri.add("felpa");
    	filtri.add("borsello");
    	
    	String ricerca = "Felpa";
    	
    	ArrayList<Prodotto> actual = null;
    	try {
			actual = prodottoModelDS.doRetrieveAll("ASC", filtri, ricerca);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestAscRicerca() throws Exception{
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	expected.add(new Prodotto(3, "felpa", 25, "grigio", "Live Ride Felpa", "hz", "hoodie", null));
    	expected.add(new Prodotto(1, "felpa", 25, "beige", "Rosa Felpa", "hz", "hoodie", null));
    	
    	/*ArrayList<String> filtri = new ArrayList<String>();
    	filtri.add("t-shirt");
    	filtri.add("felpa");
    	filtri.add("borsello");*/
    	
    	String ricerca = "Felpa";
    	
    	ArrayList<Prodotto> actual = null;
    	try {
			actual = prodottoModelDS.doRetrieveAll("ASC", null, ricerca);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestAscFiltri() throws Exception{
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	expected.add(new Prodotto(2, "t-shirt", 15, "bianco", "Aereoplano T-Shirt", "hz", "normale", null));
    	expected.add(new Prodotto(4, "borsello", 8, "bianco", "Coffee Lover", null, null, "N"));
    	expected.add(new Prodotto(0, "t-shirt", 15, "bianco", "Hakuna Matata T-Shirt", "hz", "normale", null));
    	expected.add(new Prodotto(3, "felpa", 25, "grigio", "Live Ride Felpa", "hz", "hoodie", null));
    	expected.add(new Prodotto(5, "borsello", 8, "bianco", "Renna", null, null, "N"));
    	expected.add(new Prodotto(1, "felpa", 25, "beige", "Rosa Felpa", "hz", "hoodie", null));
    	
    	ArrayList<String> filtri = new ArrayList<String>();
    	filtri.add("t-shirt");
    	filtri.add("felpa");
    	filtri.add("borsello");
    	
    	//String ricerca = "Felpa";
    	
    	ArrayList<Prodotto> actual = null;
    	try {
			actual = prodottoModelDS.doRetrieveAll("ASC", filtri, null);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestAscSenza() throws Exception{
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
			actual = prodottoModelDS.doRetrieveAll("ASC", null, null);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestDescFiltriRicerca() throws Exception{
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	expected.add(new Prodotto(6, "cappello", 8, "nero", "Cloud", null, null, "N"));
    	
    	ArrayList<String> filtri = new ArrayList<String>();
    	filtri.add("shopper");
    	filtri.add("felpa");
    	filtri.add("cappello");
    	
    	String ricerca = "Cloud";
    	
    	ArrayList<Prodotto> actual = null;
    	try {
			actual = prodottoModelDS.doRetrieveAll("DESC", filtri, ricerca);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestDescRicerca() throws Exception{
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	expected.add(new Prodotto(0, "t-shirt", 15, "bianco", "Hakuna Matata T-Shirt", "hz", "normale", null));
    	expected.add(new Prodotto(2, "t-shirt", 15, "bianco", "Aereoplano T-Shirt", "hz", "normale", null));
    	
    	/*ArrayList<String> filtri = new ArrayList<String>();
    	filtri.add("shopper");
    	filtri.add("felpa");
    	filtri.add("cappello");*/
    	
    	String ricerca = "T-Shirt";
    	
    	ArrayList<Prodotto> actual = null;
    	try {
			actual = prodottoModelDS.doRetrieveAll("DESC", null, ricerca);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestDescFiltri() throws Exception{
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	expected.add(new Prodotto(1, "felpa", 25, "beige", "Rosa Felpa", "hz", "hoodie", null));
    	expected.add(new Prodotto(3, "felpa", 25, "grigio", "Live Ride Felpa", "hz", "hoodie", null));
    	expected.add(new Prodotto(7, "shopper", 5, "bianco", "Disney Shopper", null, null, "N"));
    	expected.add(new Prodotto(6, "cappello", 8, "nero", "Cloud", null, null, "N"));
    	
    	ArrayList<String> filtri = new ArrayList<String>();
    	filtri.add("shopper");
    	filtri.add("felpa");
    	filtri.add("cappello");
    	
    	ArrayList<Prodotto> actual = null;
    	try {
			actual = prodottoModelDS.doRetrieveAll("DESC", filtri, null);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestDescSenza() throws Exception{
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
			actual = prodottoModelDS.doRetrieveAll("DESC", null, null);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestVuotoFiltri() throws Exception{
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	expected.add(new Prodotto(4, "borsello", 8, "bianco", "Coffee Lover", null, null, "N"));
    	expected.add(new Prodotto(5, "borsello", 8, "bianco", "Renna", null, null, "N"));
    	expected.add(new Prodotto(6, "cappello", 8, "nero", "Cloud", null, null, "N"));
    	expected.add(new Prodotto(8, "grembiule", 5, "bianco", "Cucina Bene Grembiule", null, null, "N"));
    	
    	ArrayList<String> filtri = new ArrayList<String>();
    	filtri.add("borsello");
    	filtri.add("grembiule");
    	filtri.add("cappello");
    	
    	ArrayList<Prodotto> actual = null;
    	try {
			actual = prodottoModelDS.doRetrieveAll("", filtri, null);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestNullRicerca() throws Exception{
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	expected.add(new Prodotto(3, "felpa", 25, "grigio", "Live Ride Felpa", "hz", "hoodie", null));
    	expected.add(new Prodotto(4, "borsello", 8, "bianco", "Coffee Lover", null, null, "N"));
    	
    	String ricerca = "ve";
    	
    	ArrayList<Prodotto> actual = null;
    	try {
			actual = prodottoModelDS.doRetrieveAll("", null, ricerca);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestAltroFiltri() throws Exception{
    	ArrayList<String> filtri = new ArrayList<String>();
    	filtri.add("borsello");
    	filtri.add("grembiule");
    	filtri.add("cappello");
    	
    	assertThrows(Exception.class, () -> {
    		prodottoModelDS.doRetrieveAll("", filtri, null);
    	});
    }
    
    @Test
    public void doSaveTestAbbigliamento() throws Exception{
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(ProdottoModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doSaveProdottoAbbigliamento.xml"))
                .getTable(table);
    	
    	Prodotto prod = new Prodotto(9, "t-shirt", 15, "nero", "Harry Potter T-Shirt", "hz", "normale", "S");
    	try {
			prodottoModelDS.doSave(prod);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	

    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void doSaveTestNonAbbigliamento() throws Exception{
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(ProdottoModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doSaveProdottoNonAbbigliamento.xml"))
                .getTable(table);
    	
    	Prodotto prod = new Prodotto(9, "cappello", 8, "nero", "Moon", null, null, "S");
    	try {
			prodottoModelDS.doSave(prod);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	

    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @ParameterizedTest
    @MethodSource("doSaveTestProvider")
    public void doSaveTestNonSalva(String tipo, float prezzo, String descrizione, String colore, String marca, String modello) {
    	Prodotto prod = new Prodotto(9, tipo, prezzo, colore, descrizione, marca, modello, "S");
    	assertThrows(Exception.class, () -> {
    		prodottoModelDS.doSave(prod);
    	});
    }
    
    private static Stream<Arguments> doSaveTestProvider(){
    	return Stream.of(
    			//tipo vuoto
    			Arguments.of("", 5, "Peas Shopper", "bianco", "", ""),
    			//tipo null
    			Arguments.of(null, 5, "Peas Shopper", "bianco", "", ""),
    			//tipo non formattato correttamente
    			Arguments.of("cravatta", 5, "Peas Shopper", "bianco", "", ""),
    			//prezzo non positivo
    			Arguments.of("shopper", -5, "Peas Shopper", "bianco", "", ""),
    			//descrizione vuota
    			Arguments.of("shopper", 5, "", "bianco", "", ""),
    			//descrizione null
    			Arguments.of("shopper", 5, null, "bianco", "", ""),
    			//colore vuoto
    			Arguments.of("shopper", 5, "Peas Shopper", "", "", ""),
    			//colore null
    			Arguments.of("shopper", 5, "Peas Shopper", null, "", ""),
    			//marca vuota
    			Arguments.of("t-shirt", 15, "Harry Potter T-Shirt", "nero", "", "normale"),
    			//marca null
    			Arguments.of("t-shirt", 15, "Harry Potter T-Shirt", "nero", null, "normale"),
    			//modello vuoto
    			Arguments.of("t-shirt", 15, "Harry Potter T-Shirt", "nero", "hz", ""),
    			//modello null
    			Arguments.of("t-shirt", 15, "Harry Potter T-Shirt", "nero", "hz", null)
    			);
    }
    
    @Test
    public void doUpdateTestAbbigliamento() throws Exception{
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(ProdottoModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doUpdateProdottoAbbigliamento.xml"))
                .getTable(table);
    	
    	Prodotto prod = new Prodotto(3, "t-shirt", 15, "nero", "Harry Potter T-Shirt", "hz", "normale", "S");
    	try {
			prodottoModelDS.doUpdate(prod);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void doUpdateTestNonAbbigliamento() throws Exception{
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(ProdottoModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doUpdateProdottoNonAbbigliamento.xml"))
                .getTable(table);
    	
    	Prodotto prod = new Prodotto(3, "borsello", 8, "bianco", "Rosa", null,null, "S");
    	try {
			prodottoModelDS.doUpdate(prod);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @ParameterizedTest
    @MethodSource("doUpdateTestProvider")
    public void doUpdateTestNonSalva(int codice, String tipo, float prezzo, String descrizione, String colore, String marca, String modello) {
    	Prodotto prod = new Prodotto(codice, tipo, prezzo, colore, descrizione, marca, modello, "S");
    	assertThrows(Exception.class, () -> {
    		prodottoModelDS.doUpdate(prod);
    	});
    }
    
    private static Stream<Arguments> doUpdateTestProvider(){
    	return Stream.of(
    			//tipo vuoto
    			Arguments.of(4, "", 5, "Peas Shopper", "bianco", "", ""),
    			//tipo null
    			Arguments.of(4, null, 5, "Peas Shopper", "bianco", "", ""),
    			//tipo non formattato correttamente
    			Arguments.of(4, "cravatta", 5, "Peas Shopper", "bianco", "", ""),
    			//prezzo non positivo
    			Arguments.of(4, "shopper", -5, "Peas Shopper", "bianco", "", ""),
    			//descrizione vuota
    			Arguments.of(4, "shopper", 5, "", "bianco", "", ""),
    			//descrizione null
    			Arguments.of(4, "shopper", 5, null, "bianco", "", ""),
    			//colore vuoto
    			Arguments.of(4, "shopper", 5, "Peas Shopper", "", "", ""),
    			//colore null
    			Arguments.of(4, "shopper", 5, "Peas Shopper", null, "", ""),
    			//marca vuota
    			Arguments.of(4, "t-shirt", 15, "Harry Potter T-Shirt", "nero", "", "normale"),
    			//marca null
    			Arguments.of(4, "t-shirt", 15, "Harry Potter T-Shirt", "nero", null, "normale"),
    			//modello vuoto
    			Arguments.of(4, "t-shirt", 15, "Harry Potter T-Shirt", "nero", "hz", ""),
    			//modello null
    			Arguments.of(4, "t-shirt", 15, "Harry Potter T-Shirt", "nero", "hz", null),
    			//codice non presente nel Database
    			Arguments.of(15, "t-shirt", 15, "Harry Potter T-Shirt", "nero", "hz", null)
    			);
    }
    
    @Test
    public void doDeleteTestPresente() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(ProdottoModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doDeleteProdotto.xml"))
                .getTable(table);
    	Prodotto del = new Prodotto();
    	del.setCodice(1);    	
    	
    	try {
			prodottoModelDS.doDelete(del);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void doDeleteTestNonPresente() throws Exception {
    	assertThrows(Exception.class, () -> {
    		Prodotto del = new Prodotto();
        	del.setCodice(17); 
			prodottoModelDS.doDelete(del);
    	});
    }
	
}
