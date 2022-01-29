package catalogoManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import checking.CheckException;


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
                "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:resources/db/init/prodotto.sql'",
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
    @DisplayName("TCU2_1_1_1 doRetrieveByKeyTestPresente")
    public void doRetrieveByKeyTestPresente() {
    	Prodotto expected = new Prodotto(2, "t-shirt", 15f, "bianco", "Hakuna Matata T-Shirt", "hz", "normale", null);
    	Prodotto actual = null;
    	try {
			actual = prodottoModelDS.doRetrieveByKey(2);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }

    @Test
    @DisplayName("TCU2_1_1_2 doRetrieveByKeyTestNonPresente")
    public void doRetrieveByKeyTestNonPresente() {
    	Prodotto expected = null;
    	Prodotto actual = null;
    	try {
			actual = prodottoModelDS.doRetrieveByKey(100);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU2_1_2_1 doRetrieveAllTestAsc")
    public void doRetrieveAllTestAsc() throws Exception{
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	expected.add(new Prodotto(4, "t-shirt", 15, "bianco", "Aereoplano T-Shirt", "hz", "normale", null));
    	expected.add(new Prodotto(8, "cappello", 8, "nero", "Cloud", null, null, "N"));
    	expected.add(new Prodotto(6, "borsello", 8, "bianco", "Coffee Lover", null, null, "N"));
    	expected.add(new Prodotto(10, "grembiule", 5, "bianco", "Cucina Bene Grembiule", null, null, "N"));
    	expected.add(new Prodotto(9, "shopper", 5, "bianco", "Disney Shopper", null, null, "N"));
    	expected.add(new Prodotto(2, "t-shirt", 15, "bianco", "Hakuna Matata T-Shirt", "hz", "normale", null));
    	expected.add(new Prodotto(5, "felpa", 25, "grigio", "Live Ride Felpa", "hz", "hoodie", null));
    	expected.add(new Prodotto(7, "borsello", 8, "bianco", "Renna", null, null, "N"));
    	expected.add(new Prodotto(3, "felpa", 25, "beige", "Rosa Felpa", "hz", "hoodie", null));
    	
    	ArrayList<Prodotto> actual = null;
    	try {
			actual = prodottoModelDS.doRetrieveAll("ASC");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU2_1_2_2 doRetrieveAllTestDesc")
    public void doRetrieveAllTestDesc() throws Exception{
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	expected.add(new Prodotto(3, "felpa", 25, "beige", "Rosa Felpa", "hz", "hoodie", null));
    	expected.add(new Prodotto(7, "borsello", 8, "bianco", "Renna", null, null, "N"));
    	expected.add(new Prodotto(5, "felpa", 25, "grigio", "Live Ride Felpa", "hz", "hoodie", null));
    	expected.add(new Prodotto(2, "t-shirt", 15, "bianco", "Hakuna Matata T-Shirt", "hz", "normale", null));
    	expected.add(new Prodotto(9, "shopper", 5, "bianco", "Disney Shopper", null, null, "N"));
    	expected.add(new Prodotto(10, "grembiule", 5, "bianco", "Cucina Bene Grembiule", null, null, "N"));
    	expected.add(new Prodotto(6, "borsello", 8, "bianco", "Coffee Lover", null, null, "N"));
    	expected.add(new Prodotto(8, "cappello", 8, "nero", "Cloud", null, null, "N"));
    	expected.add(new Prodotto(4, "t-shirt", 15, "bianco", "Aereoplano T-Shirt", "hz", "normale", null));
    	
    	ArrayList<Prodotto> actual = null;
    	try {
			actual = prodottoModelDS.doRetrieveAll("DESC");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU2_1_2_3 doRetrieveAllTestVuoto")
    public void doRetrieveAllTestVuoto() throws Exception{
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	expected.add(new Prodotto(2, "t-shirt", 15, "bianco", "Hakuna Matata T-Shirt", "hz", "normale", null));
    	expected.add(new Prodotto(3, "felpa", 25, "beige", "Rosa Felpa", "hz", "hoodie", null));
    	expected.add(new Prodotto(4, "t-shirt", 15, "bianco", "Aereoplano T-Shirt", "hz", "normale", null));
    	expected.add(new Prodotto(5, "felpa", 25, "grigio", "Live Ride Felpa", "hz", "hoodie", null));
    	expected.add(new Prodotto(6, "borsello", 8, "bianco", "Coffee Lover", null, null, "N"));
    	expected.add(new Prodotto(7, "borsello", 8, "bianco", "Renna", null, null, "N"));
    	expected.add(new Prodotto(8, "cappello", 8, "nero", "Cloud", null, null, "N"));
    	expected.add(new Prodotto(9, "shopper", 5, "bianco", "Disney Shopper", null, null, "N"));
    	expected.add(new Prodotto(10, "grembiule", 5, "bianco", "Cucina Bene Grembiule", null, null, "N"));
    	
    	ArrayList<Prodotto> actual = null;
    	try {
			actual = prodottoModelDS.doRetrieveAll("");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU2_1_2_4 doRetrieveAllTestNull")
    public void doRetrieveAllTestNull() throws Exception{
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	expected.add(new Prodotto(2, "t-shirt", 15, "bianco", "Hakuna Matata T-Shirt", "hz", "normale", null));
    	expected.add(new Prodotto(3, "felpa", 25, "beige", "Rosa Felpa", "hz", "hoodie", null));
    	expected.add(new Prodotto(4, "t-shirt", 15, "bianco", "Aereoplano T-Shirt", "hz", "normale", null));
    	expected.add(new Prodotto(5, "felpa", 25, "grigio", "Live Ride Felpa", "hz", "hoodie", null));
    	expected.add(new Prodotto(6, "borsello", 8, "bianco", "Coffee Lover", null, null, "N"));
    	expected.add(new Prodotto(7, "borsello", 8, "bianco", "Renna", null, null, "N"));
    	expected.add(new Prodotto(8, "cappello", 8, "nero", "Cloud", null, null, "N"));
    	expected.add(new Prodotto(9, "shopper", 5, "bianco", "Disney Shopper", null, null, "N"));
    	expected.add(new Prodotto(10, "grembiule", 5, "bianco", "Cucina Bene Grembiule", null, null, "N"));
    	
    	ArrayList<Prodotto> actual = null;
    	try {
			actual = prodottoModelDS.doRetrieveAll(null);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU2_1_2_5 doRetrieveAllTestAltro")
    public void doRetrieveAllTestAltro() {
    	assertThrows(CheckException.class, () -> {
    		prodottoModelDS.doRetrieveAll("ascendente");
    	});
    }
    
    @Test
    @DisplayName("TCU2_1_3_1 doRetrieveAllTestAscFiltriRicerca")
    public void doRetrieveAllTestAscFiltriRicerca() throws Exception{
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	expected.add(new Prodotto(5, "felpa", 25, "grigio", "Live Ride Felpa", "hz", "hoodie", null));
    	expected.add(new Prodotto(3, "felpa", 25, "beige", "Rosa Felpa", "hz", "hoodie", null));
    	
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
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU2_1_3_2 doRetrieveAllTestAscRicerca")
    public void doRetrieveAllTestAscRicerca() throws Exception{
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	expected.add(new Prodotto(5, "felpa", 25, "grigio", "Live Ride Felpa", "hz", "hoodie", null));
    	expected.add(new Prodotto(3, "felpa", 25, "beige", "Rosa Felpa", "hz", "hoodie", null));
    	
    	String ricerca = "Felpa";
    	
    	ArrayList<Prodotto> actual = null;
    	try {
			actual = prodottoModelDS.doRetrieveAll("ASC", null, ricerca);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU2_1_3_3 doRetrieveAllTestAscFiltri")
    public void doRetrieveAllTestAscFiltri() throws Exception{
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	expected.add(new Prodotto(4, "t-shirt", 15, "bianco", "Aereoplano T-Shirt", "hz", "normale", null));
    	expected.add(new Prodotto(6, "borsello", 8, "bianco", "Coffee Lover", null, null, "N"));
    	expected.add(new Prodotto(2, "t-shirt", 15, "bianco", "Hakuna Matata T-Shirt", "hz", "normale", null));
    	expected.add(new Prodotto(5, "felpa", 25, "grigio", "Live Ride Felpa", "hz", "hoodie", null));
    	expected.add(new Prodotto(7, "borsello", 8, "bianco", "Renna", null, null, "N"));
    	expected.add(new Prodotto(3, "felpa", 25, "beige", "Rosa Felpa", "hz", "hoodie", null));
    	
    	ArrayList<String> filtri = new ArrayList<String>();
    	filtri.add("t-shirt");
    	filtri.add("felpa");
    	filtri.add("borsello");
    	
    	ArrayList<Prodotto> actual = null;
    	try {
			actual = prodottoModelDS.doRetrieveAll("ASC", filtri, null);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU2_1_3_4 doRetrieveAllTestAscSenza")
    public void doRetrieveAllTestAscSenza() throws Exception{
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	expected.add(new Prodotto(4, "t-shirt", 15, "bianco", "Aereoplano T-Shirt", "hz", "normale", null));
    	expected.add(new Prodotto(8, "cappello", 8, "nero", "Cloud", null, null, "N"));
    	expected.add(new Prodotto(6, "borsello", 8, "bianco", "Coffee Lover", null, null, "N"));
    	expected.add(new Prodotto(10, "grembiule", 5, "bianco", "Cucina Bene Grembiule", null, null, "N"));
    	expected.add(new Prodotto(9, "shopper", 5, "bianco", "Disney Shopper", null, null, "N"));
    	expected.add(new Prodotto(2, "t-shirt", 15, "bianco", "Hakuna Matata T-Shirt", "hz", "normale", null));
    	expected.add(new Prodotto(5, "felpa", 25, "grigio", "Live Ride Felpa", "hz", "hoodie", null));
    	expected.add(new Prodotto(7, "borsello", 8, "bianco", "Renna", null, null, "N"));
    	expected.add(new Prodotto(3, "felpa", 25, "beige", "Rosa Felpa", "hz", "hoodie", null));
    	
    	ArrayList<Prodotto> actual = null;
    	try {
			actual = prodottoModelDS.doRetrieveAll("ASC", null, null);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU2_1_3_5 doRetrieveAllTestDescFiltriRicerca")
    public void doRetrieveAllTestDescFiltriRicerca() throws Exception{
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	expected.add(new Prodotto(8, "cappello", 8, "nero", "Cloud", null, null, "N"));
    	
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
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU2_1_3_6 doRetrieveAllTestDescRicerca")
    public void doRetrieveAllTestDescRicerca() throws Exception{
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	expected.add(new Prodotto(2, "t-shirt", 15, "bianco", "Hakuna Matata T-Shirt", "hz", "normale", null));
    	expected.add(new Prodotto(4, "t-shirt", 15, "bianco", "Aereoplano T-Shirt", "hz", "normale", null));
    	
    	String ricerca = "T-Shirt";
    	
    	ArrayList<Prodotto> actual = null;
    	try {
			actual = prodottoModelDS.doRetrieveAll("DESC", null, ricerca);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU2_1_3_7 doRetrieveAllTestDescFiltri")
    public void doRetrieveAllTestDescFiltri() throws Exception{
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	expected.add(new Prodotto(3, "felpa", 25, "beige", "Rosa Felpa", "hz", "hoodie", null));
    	expected.add(new Prodotto(5, "felpa", 25, "grigio", "Live Ride Felpa", "hz", "hoodie", null));
    	expected.add(new Prodotto(9, "shopper", 5, "bianco", "Disney Shopper", null, null, "N"));
    	expected.add(new Prodotto(8, "cappello", 8, "nero", "Cloud", null, null, "N"));
    	
    	ArrayList<String> filtri = new ArrayList<String>();
    	filtri.add("shopper");
    	filtri.add("felpa");
    	filtri.add("cappello");
    	
    	ArrayList<Prodotto> actual = null;
    	try {
			actual = prodottoModelDS.doRetrieveAll("DESC", filtri, null);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU2_1_3_8 doRetrieveAllTestDescSenza")
    public void doRetrieveAllTestDescSenza() throws Exception{
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	expected.add(new Prodotto(3, "felpa", 25, "beige", "Rosa Felpa", "hz", "hoodie", null));
    	expected.add(new Prodotto(7, "borsello", 8, "bianco", "Renna", null, null, "N"));
    	expected.add(new Prodotto(5, "felpa", 25, "grigio", "Live Ride Felpa", "hz", "hoodie", null));
    	expected.add(new Prodotto(2, "t-shirt", 15, "bianco", "Hakuna Matata T-Shirt", "hz", "normale", null));
    	expected.add(new Prodotto(9, "shopper", 5, "bianco", "Disney Shopper", null, null, "N"));
    	expected.add(new Prodotto(10, "grembiule", 5, "bianco", "Cucina Bene Grembiule", null, null, "N"));
    	expected.add(new Prodotto(6, "borsello", 8, "bianco", "Coffee Lover", null, null, "N"));
    	expected.add(new Prodotto(8, "cappello", 8, "nero", "Cloud", null, null, "N"));
    	expected.add(new Prodotto(4, "t-shirt", 15, "bianco", "Aereoplano T-Shirt", "hz", "normale", null));
    	
    	ArrayList<Prodotto> actual = null;
    	try {
			actual = prodottoModelDS.doRetrieveAll("DESC", null, null);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU2_1_3_9 doRetrieveAllTestVuotoFiltri")
    public void doRetrieveAllTestVuotoFiltri() throws Exception{
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	expected.add(new Prodotto(6, "borsello", 8, "bianco", "Coffee Lover", null, null, "N"));
    	expected.add(new Prodotto(7, "borsello", 8, "bianco", "Renna", null, null, "N"));
    	expected.add(new Prodotto(8, "cappello", 8, "nero", "Cloud", null, null, "N"));
    	expected.add(new Prodotto(10, "grembiule", 5, "bianco", "Cucina Bene Grembiule", null, null, "N"));
    	
    	ArrayList<String> filtri = new ArrayList<String>();
    	filtri.add("borsello");
    	filtri.add("grembiule");
    	filtri.add("cappello");
    	
    	ArrayList<Prodotto> actual = null;
    	try {
			actual = prodottoModelDS.doRetrieveAll("", filtri, null);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU2_1_3_10 doRetrieveAllTestNullRicerca")
    public void doRetrieveAllTestNullRicerca() throws Exception{
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	expected.add(new Prodotto(5, "felpa", 25, "grigio", "Live Ride Felpa", "hz", "hoodie", null));
    	expected.add(new Prodotto(6, "borsello", 8, "bianco", "Coffee Lover", null, null, "N"));
    	
    	String ricerca = "ve";
    	
    	ArrayList<Prodotto> actual = null;
    	try {
			actual = prodottoModelDS.doRetrieveAll("", null, ricerca);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU2_1_3_11 doRetrieveAllTestAltroFiltri")
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
    @DisplayName("TCU2_1_4_1 doSaveTestAbbigliamento")
    public void doSaveTestAbbigliamento() throws Exception{
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(ProdottoModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doSaveProdottoAbbigliamento.xml"))
                .getTable(table);
    	
    	Prodotto prod = new Prodotto(1, "t-shirt", 15, "nero", "Harry Potter T-Shirt", "hz", "normale", "S");
    	try {
			prodottoModelDS.doSave(prod);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	

    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    @DisplayName("TCU2_1_4_2 doSaveTestNonAbbigliamento")
    public void doSaveTestNonAbbigliamento() throws Exception{
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(ProdottoModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doSaveProdottoNonAbbigliamento.xml"))
                .getTable(table);
    	
    	Prodotto prod = new Prodotto(1, "cappello", 8, "nero", "Moon", "", "", "S");
    	try {
			prodottoModelDS.doSave(prod);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	

    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @ParameterizedTest
    @MethodSource("doSaveTestProvider")
    @DisplayName("TCU2_1_4_3 doSaveTestNonSalva")
    public void doSaveTestNonSalva(String tipo, float prezzo, String descrizione, String colore, String marca, String modello) {
    	Prodotto prod = new Prodotto(1, tipo, prezzo, colore, descrizione, marca, modello, "S");
    	assertThrows(CheckException.class, () -> {
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
    @DisplayName("TCU2_1_5_1 doUpdateTestAbbigliamento")
    public void doUpdateTestAbbigliamento() throws Exception{
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(ProdottoModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doUpdateProdottoAbbigliamento.xml"))
                .getTable(table);
    	
    	Prodotto prod = new Prodotto(5, "t-shirt", 15, "nero", "Harry Potter T-Shirt", "hz", "normale", "S");
    	try {
			prodottoModelDS.doUpdate(prod);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}

    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    @DisplayName("TCU2_1_5_2 doUpdateTestNonAbbigliamento")
    public void doUpdateTestNonAbbigliamento() throws Exception{
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(ProdottoModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doUpdateProdottoNonAbbigliamento.xml"))
                .getTable(table);
    	
    	Prodotto prod = new Prodotto(5, "borsello", 8, "bianco", "Rosa", null,null, "S");
    	try {
			prodottoModelDS.doUpdate(prod);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}

    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @ParameterizedTest
    @MethodSource("doUpdateTestProvider")
    @DisplayName("TCU2_1_5_3 doUpdateTestNonSalva")
    public void doUpdateTestNonSalva(int codice, String tipo, float prezzo, String descrizione, String colore, String marca, String modello) {
    	Prodotto prod = new Prodotto(codice, tipo, prezzo, colore, descrizione, marca, modello, "S");
    	assertThrows(CheckException.class, () -> {
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
    @DisplayName("TCU2_1_6_1 doDeleteTestPresente")
    public void doDeleteTestPresente() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(ProdottoModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doDeleteProdotto.xml"))
                .getTable(table);
    	Prodotto del = new Prodotto();
    	del.setCodice(3);    	
    	
    	try {
			prodottoModelDS.doDelete(del);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    @DisplayName("TCU2_1_6_2 doDeleteTestNonPresente")
    public void doDeleteTestNonPresente() throws Exception {
    	assertThrows(Exception.class, () -> {
    		Prodotto del = new Prodotto();
        	del.setCodice(17); 
			prodottoModelDS.doDelete(del);
    	});
    }
	
}
