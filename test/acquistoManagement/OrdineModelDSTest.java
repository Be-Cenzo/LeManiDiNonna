package acquistoManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import catalogoManagement.Prodotto;

public class OrdineModelDSTest {

	private static String expectedPath = "resources/db/expected/acquistoManagement/";
	private static String initPath = "resources/db/init/acquistoManagement/";
	private static String table = "Ordine";
    private static IDatabaseTester tester;
	private DataSource ds;
    private OrdineModelDS ordineModelDS;
    
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
                .build(OrdineModelDSTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Prepara lo stato iniziale di default
        refreshDataSet(initPath + "OrdineInit.xml");
        ds = Mockito.mock(DataSource.class);
        Mockito.when(ds.getConnection())
        .thenReturn(tester.getConnection().getConnection());
        ordineModelDS = new OrdineModelDS(ds);
    }

    @AfterEach
    public void tearDown() throws Exception {
        tester.onTearDown();
    }
    
    @Test
    public void doRetrieveByKeyPresente() {
    	Ordine expected = new Ordine(3, new Date(1610319600000L), 30, 10, "", "Contabilizzato", "christian.gambardella@gmail.com", 1, "veloce");
    	
    	Ordine actual = null;
    	try {
			actual = ordineModelDS.doRetrieveByKey(3);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveByKeyNonPresente() {
    	Ordine expected = new Ordine();
    	
    	Ordine actual = null;
    	try {
			actual = ordineModelDS.doRetrieveByKey(6);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestAsc() {
    	Ordine ord;
    	ArrayList<Ordine> expected = new ArrayList<Ordine>();
  
    	ord = new Ordine(4, new Date(1595800800000L), 36, 7, "", "Contabilizzato", "vincenzo.offertucci@gmail.com", 2, "lento");
    	expected.add(ord);

    	ord = new Ordine(5, new Date(1602712800000L), 24, 10, "", "Contabilizzato", "vittorio.armenante@gmail.com", 1, "veloce");
    	expected.add(ord);
    	
    	ord = new Ordine(2, new Date(1606690800000L), 63, 7, "", "Contabilizzato", "vincenzo.offertucci@gmail.com", 1, "lento");
    	expected.add(ord);
    	
    	ord = new Ordine(3, new Date(1610319600000L), 30, 10, "", "Contabilizzato", "christian.gambardella@gmail.com", 1, "veloce");
    	expected.add(ord);
    	
    	ArrayList<Ordine> actual = null;
    	try {
			actual = ordineModelDS.doRetrieveAll("ASC");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestDesc() {
    	Ordine ord;
    	ArrayList<Ordine> expected = new ArrayList<Ordine>();
  
    	ord = new Ordine(3, new Date(1610319600000L), 30, 10, "", "Contabilizzato", "christian.gambardella@gmail.com", 1, "veloce");
    	expected.add(ord);
    	
    	ord = new Ordine(2, new Date(1606690800000L), 63, 7, "", "Contabilizzato", "vincenzo.offertucci@gmail.com", 1, "lento");
    	expected.add(ord);
    	
    	ord = new Ordine(5, new Date(1602712800000L), 24, 10, "", "Contabilizzato", "vittorio.armenante@gmail.com", 1, "veloce");
    	expected.add(ord);
    	
    	ord = new Ordine(4, new Date(1595800800000L), 36, 7, "", "Contabilizzato", "vincenzo.offertucci@gmail.com", 2, "lento");
    	expected.add(ord);
    	
    	ArrayList<Ordine> actual = null;
    	try {
			actual = ordineModelDS.doRetrieveAll("DESC");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestVuoto() {
    	Ordine ord;
    	ArrayList<Ordine> expected = new ArrayList<Ordine>();

    	ord = new Ordine(2, new Date(1606690800000L), 63, 7, "", "Contabilizzato", "vincenzo.offertucci@gmail.com", 1, "lento");
    	expected.add(ord);
    	
    	ord = new Ordine(3, new Date(1610319600000L), 30, 10, "", "Contabilizzato", "christian.gambardella@gmail.com", 1, "veloce");
    	expected.add(ord);
    	
    	ord = new Ordine(4, new Date(1595800800000L), 36, 7, "", "Contabilizzato", "vincenzo.offertucci@gmail.com", 2, "lento");
    	expected.add(ord);
    	
    	ord = new Ordine(5, new Date(1602712800000L), 24, 10, "", "Contabilizzato", "vittorio.armenante@gmail.com", 1, "veloce");
    	expected.add(ord);
    	
    	ArrayList<Ordine> actual = null;
    	try {
			actual = ordineModelDS.doRetrieveAll("");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestNull() {
    	Ordine ord;
    	ArrayList<Ordine> expected = new ArrayList<Ordine>();

    	ord = new Ordine(2, new Date(1606690800000L), 63, 7, "", "Contabilizzato", "vincenzo.offertucci@gmail.com", 1, "lento");
    	expected.add(ord);
    	
    	ord = new Ordine(3, new Date(1610319600000L), 30, 10, "", "Contabilizzato", "christian.gambardella@gmail.com", 1, "veloce");
    	expected.add(ord);
    	
    	ord = new Ordine(4, new Date(1595800800000L), 36, 7, "", "Contabilizzato", "vincenzo.offertucci@gmail.com", 2, "lento");
    	expected.add(ord);
    	
    	ord = new Ordine(5, new Date(1602712800000L), 24, 10, "", "Contabilizzato", "vittorio.armenante@gmail.com", 1, "veloce");
    	expected.add(ord);
    	
    	ArrayList<Ordine> actual = null;
    	try {
			actual = ordineModelDS.doRetrieveAll(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllAltro() {
    	assertThrows(Exception.class, () -> {
    		ordineModelDS.doRetrieveAll("discendente");
    	});
    }
    
    @Test
    public void doRetrieveAllTestAscEmailCorretta() {
    	Ordine ord;
    	ArrayList<Ordine> expected = new ArrayList<Ordine>();
  
    	ord = new Ordine(4, new Date(1595800800000L), 36, 7, "", "Contabilizzato", "vincenzo.offertucci@gmail.com", 2, "lento");
    	expected.add(ord);
    	
    	ord = new Ordine(2, new Date(1606690800000L), 63, 7, "", "Contabilizzato", "vincenzo.offertucci@gmail.com", 1, "lento");
    	expected.add(ord);
    	
    	String email = "vincenzo.offertucci@gmail.com";
    	
    	ArrayList<Ordine> actual = null;
    	try {
			actual = ordineModelDS.doRetrieveAll("ASC", email);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestDescEmailCorretta() {
    	Ordine ord;
    	ArrayList<Ordine> expected = new ArrayList<Ordine>();
  
    	ord = new Ordine(2, new Date(1606690800000L), 63, 7, "", "Contabilizzato", "vincenzo.offertucci@gmail.com", 1, "lento");
    	expected.add(ord);
    	
    	ord = new Ordine(4, new Date(1595800800000L), 36, 7, "", "Contabilizzato", "vincenzo.offertucci@gmail.com", 2, "lento");
    	expected.add(ord);

    	String email = "vincenzo.offertucci@gmail.com";
    	
    	ArrayList<Ordine> actual = null;
    	try {
			actual = ordineModelDS.doRetrieveAll("DESC", email);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestVuotoEmailCorretta() {
    	Ordine ord;
    	ArrayList<Ordine> expected = new ArrayList<Ordine>();

    	ord = new Ordine(2, new Date(1606690800000L), 63, 7, "", "Contabilizzato", "vincenzo.offertucci@gmail.com", 1, "lento");
    	expected.add(ord);
    	
    	ord = new Ordine(4, new Date(1595800800000L), 36, 7, "", "Contabilizzato", "vincenzo.offertucci@gmail.com", 2, "lento");
    	expected.add(ord);
    	
    	String email = "vincenzo.offertucci@gmail.com";
    	
    	ArrayList<Ordine> actual = null;
    	try {
			actual = ordineModelDS.doRetrieveAll("", email);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestNullEmailCorretta() {
    	Ordine ord;
    	ArrayList<Ordine> expected = new ArrayList<Ordine>();

    	ord = new Ordine(2, new Date(1606690800000L), 63, 7, "", "Contabilizzato", "vincenzo.offertucci@gmail.com", 1, "lento");
    	expected.add(ord);
    	
    	ord = new Ordine(4, new Date(1595800800000L), 36, 7, "", "Contabilizzato", "vincenzo.offertucci@gmail.com", 2, "lento");
    	expected.add(ord);
    	
    	String email = "vincenzo.offertucci@gmail.com";
    	
    	ArrayList<Ordine> actual = null;
    	try {
			actual = ordineModelDS.doRetrieveAll(null, email);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllAltroEmailCorretta() {
    	assertThrows(Exception.class, () -> {
        	String email = "vincenzo.offertucci@gmail.com";
    		ordineModelDS.doRetrieveAll("discendente", email);
    	});
    }
    
    @Test
    public void doRetrieveAllAltroEmailVuota() {
    	assertThrows(Exception.class, () -> {
        	String email = "";
    		ordineModelDS.doRetrieveAll("discendente", email);
    	});
    }
    
    @Test
    public void doRetrieveAllAltroEmailNull() {
    	assertThrows(Exception.class, () -> {
        	String email = null;
    		ordineModelDS.doRetrieveAll("discendente", email);
    	});
    }
    
    @Test
    public void doSaveTestSalva() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(OrdineModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doSaveOrdineCorretto.xml"))
                .getTable(table);

    	Prodotto prod;
    	String email = "vincenzo.offertucci@gmail.com";
    	String note = "";
    	int indirizzo = 1;
    	String spedizione = "veloce";
    	ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();
    	float totale = 45;
    	
    	prod = new Prodotto();
    	prod.setCodice(1);
    	prod.setTaglia("M");
    	prod.setQuantita(1);
    	prod.setPrezzo(15);
    	prodotti.add(prod);
    	
    	prod = new Prodotto();
    	prod.setCodice(2);
    	prod.setTaglia("L");
    	prod.setQuantita(2);
    	prod.setPrezzo(15);
    	prodotti.add(prod);
    	
    	try {
			ordineModelDS.doSave(email, note, indirizzo, spedizione, prodotti, totale);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
        ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @ParameterizedTest
    @MethodSource("doSaveTestProvider")
    public void doSaveTestNonSalva(Date data, float prezzo, String note, String email, int indirizzo, String spedizione, ArrayList<Prodotto> prodotti) {
    	assertThrows(Exception.class, () -> {
    		ordineModelDS.doSave(email, note, indirizzo, spedizione, prodotti, prezzo);
    	});
    }
    
    private static Stream<Arguments> doSaveTestProvider(){
    	ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();
    	Prodotto prod = new Prodotto();
    	prod.setCodice(1);
    	prod.setTaglia("S");
    	prod.setQuantita(1);
    	prod.setPrezzo(15);
    	prodotti.add(prod);
    	
    	ArrayList<Prodotto> listaVuota = new ArrayList<Prodotto>();
    	return Stream.of(
    			//email vuota
    			Arguments.of(new Date(1643390580000L), 15, "", "", 2, "veloce", prodotti),
    			//email null
    			Arguments.of(new Date(1643390580000L), 15, "", null, 2, "veloce", prodotti),
    			//spedizione vuota
    			Arguments.of(new Date(1643390580000L), 15, "", "vincenzo.offertucci@gmail.com", 2, "", prodotti),
    			//spedizione null
    			Arguments.of(new Date(1643390580000L), 15, "", "vincenzo.offertucci@gmail.com", 2, null, prodotti),
    			//totale inconsistente
    			Arguments.of(new Date(1643390580000L), 150, "", "vincenzo.offertucci@gmail.com", 2, "veloce", prodotti),
    			//lista di prodotti vuota
    			Arguments.of(new Date(1643390580000L), 15, "", "vincenzo.offertucci@gmail.com", 2, "veloce", listaVuota),
    			//lista di prodotti null
    			Arguments.of(new Date(1643390580000L), 15, "", "vincenzo.offertucci@gmail.com", 2, "veloce", null)
    			);
    }
    
    @Test
    public void doUpdateTestSalva() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(OrdineModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doUpdateOrdineCorretto.xml"))
                .getTable(table);

    	ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();
    	Prodotto prod = new Prodotto();
    	prod.setCodice(1);
    	prod.setTaglia("S");
    	prod.setQuantita(1);
    	prod.setPrezzo(15);
    	prodotti.add(prod);
    	Ordine ord = new Ordine(4, new Date(1643299200000L), 15, 10, "", "Contabilizzato", "vincenzo.offertucci@gmail.com",  1, "veloce");
    	ord.setProdotti(prodotti);
    	
    	
    	try {
			ordineModelDS.doUpdate(ord);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
        ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @ParameterizedTest
    @MethodSource("doUpdateTestProvider")
    public void doUpdateTestNonSalva(int codice, Date data, float prezzo, float costoSped, String note, String stato, String email, int indirizzo, String spedizione, ArrayList<Prodotto> prodotti) {
    	assertThrows(Exception.class, () -> {
    		Ordine upd = new Ordine(codice, data, prezzo, costoSped, note, stato, email, indirizzo, spedizione);
    		upd.setProdotti(prodotti);
    		ordineModelDS.doUpdate(upd);
    	});
    }
    
    private static Stream<Arguments> doUpdateTestProvider(){
    	ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();
    	Prodotto prod = new Prodotto();
    	prod.setCodice(1);
    	prod.setTaglia("S");
    	prod.setQuantita(1);
    	prod.setPrezzo(15);
    	prodotti.add(prod);
    	
    	ArrayList<Prodotto> listaVuota = new ArrayList<Prodotto>();
    	return Stream.of(
    			//codice ordine non presente nel Datbase
    			Arguments.of(10, new Date(1643390580000L), 15, 10, "", "Contabilizzato", "vincenzo.offertucci@gmail.com", 2, "veloce", prodotti),
    			//email vuota
    			Arguments.of(2, new Date(1643390580000L), 15, 10, "", "Contabilizzato", "", 2, "veloce", prodotti),
    			//email null
    			Arguments.of(2, new Date(1643390580000L), 15, 10, "", "Contabilizzato", null, 2, "veloce", prodotti),
    			//spedizione vuota
    			Arguments.of(2, new Date(1643390580000L), 15, 10, "", "Contabilizzato", "vincenzo.offertucci@gmail.com", 2, "", prodotti),
    			//spedizione null
    			Arguments.of(2, new Date(1643390580000L), 15, 10, "", "Contabilizzato", "vincenzo.offertucci@gmail.com", 2, null, prodotti),
    			//totale inconsistente
    			Arguments.of(2, new Date(1643390580000L), 150, 10, "", "Contabilizzato", "vincenzo.offertucci@gmail.com", 2, "veloce", prodotti),
    			//lista di prodotti vuota
    			Arguments.of(2, new Date(1643390580000L), 15, 10, "", "Contabilizzato", "vincenzo.offertucci@gmail.com", 2, "veloce", listaVuota),
    			//lista di prodotti null
    			Arguments.of(2, new Date(1643390580000L), 15, 10, "", "Contabilizzato", "vincenzo.offertucci@gmail.com", 2, "veloce", null)
    			);
    }
    
    @Test
    public void doDeleteTestPresente() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(OrdineModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doDeleteOrdine.xml"))
                .getTable(table);
    	
    	Ordine del = new Ordine();
    	del.setID(5);
    	
    	try {
			ordineModelDS.doDelete(del);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
        ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void doDeleteTestNonPresente() {
    	assertThrows(Exception.class, () -> {
    		Ordine del = new Ordine();
        	del.setID(10);
        	
        	ordineModelDS.doDelete(del);
    	});
    }
	
}
