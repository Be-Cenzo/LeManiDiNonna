package catalogoManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;
import java.util.ArrayList;

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
import org.mockito.Mockito;

import checking.CheckException;

public class ConservatoModelDSTest {

	private static String expectedPath = "resources/db/expected/catalogoManagement/";
	private static String initPath = "resources/db/init/catalogoManagement/";
	private static String table = "Conservato";
    private static IDatabaseTester tester;
	private DataSource ds;
    private ConservatoModelDS conservatoModelDS;
    
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
                .build(ConservatoModelDSTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Prepara lo stato iniziale di default
        refreshDataSet(initPath + "ConservatoInit.xml");
        ds = Mockito.mock(DataSource.class);
        Mockito.when(ds.getConnection())
        .thenReturn(tester.getConnection().getConnection());
        conservatoModelDS = new ConservatoModelDS(ds);
    }

    @AfterEach
    public void tearDown() throws Exception {
        tester.onTearDown();
    }
    
    @Test
    @DisplayName("TCU2_4_1_1 doRetrieveByKeyTestPresente")
    public void doRetrieveByKeyTestPresente() {
    	int expected = 30;
    	
    	Prodotto prod = new Prodotto();
    	prod.setCodice(1);
    	prod.setTaglia("M");
    	Deposito dep = new Deposito();
    	dep.setID(2);
    	int actual = -1;
    	try {
			actual = conservatoModelDS.doRetrieveByKey(prod, dep);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU2_4_1_2 doRetrieveByKeyTestNonPresente")
    public void doRetrieveByKeyTestNonPresente() {
    	int expected = 30;
    	
    	Prodotto prod = new Prodotto();
    	prod.setCodice(1);
    	prod.setTaglia("M");
    	Deposito dep = new Deposito();
    	dep.setID(2);
    	int actual = -1;
    	try {
			actual = conservatoModelDS.doRetrieveByKey(prod, dep);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU2_4_2_1 doSaveTestSalva")
    public void doSaveTestSalva() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(ConservatoModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doSaveConservatoCorretto.xml"))
                .getTable(table);
    	
    	Prodotto prod = new Prodotto();
    	prod.setCodice(3);
    	prod.setTaglia("L");
    	Deposito dep = new Deposito();
    	dep.setID(1);
    	int disponibilita = 10;
    	try {
			conservatoModelDS.doSave(prod, dep, disponibilita);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}

        ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    @DisplayName("TCU2_4_2_2 doSaveTestNonSalva")
    public void doSaveTestNonSalva() {
    	assertThrows(CheckException.class, () -> {
    		Prodotto prod = new Prodotto();
        	prod.setCodice(1);
        	prod.setTaglia("S");
        	Deposito dep = new Deposito();
        	dep.setID(1);
        	int disponibilita = 5;
        	conservatoModelDS.doSave(prod, dep, disponibilita);
    	});
    }
    
    @Test
    @DisplayName("TCU2_4_2_3 doSaveTestNegativa")
    public void doSaveTestNegativa() {
    	assertThrows(CheckException.class, () -> {
    		Prodotto prod = new Prodotto();
        	prod.setCodice(1);
        	prod.setTaglia("M");
        	Deposito dep = new Deposito();
        	dep.setID(1);
        	int disponibilita = -15;
        	conservatoModelDS.doSave(prod, dep, disponibilita);
    	});
    }
    
    @Test
    @DisplayName("TCU2_4_3_1 doUpdateTestSalva")
    public void doUpdateTestSalva() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(ConservatoModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doUpdateConservatoCorretto.xml"))
                .getTable(table);
    	
    	Prodotto prod = new Prodotto();
    	prod.setCodice(1);
    	prod.setTaglia("S");
    	Deposito dep = new Deposito();
    	dep.setID(1);
    	int disponibilita = 50;
    	try {
			conservatoModelDS.doUpdate(prod, dep, disponibilita);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}

        ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    @DisplayName("TCU2_4_3_2 doUpdateTestNonSalva")
    public void doUpdateTestNonSalva() {
    	assertThrows(CheckException.class, () -> {
    		Prodotto prod = new Prodotto();
        	prod.setCodice(1);
        	prod.setTaglia("S");
        	Deposito dep = new Deposito();
        	dep.setID(3);
        	int disponibilita = 5;
        	conservatoModelDS.doUpdate(prod, dep, disponibilita);
    	});
    }
    
    @Test
    @DisplayName("TCU2_4_3_3 doUpdateTestNegativa")
    public void doUpdateTestNegativa() {
    	assertThrows(CheckException.class, () -> {
    		Prodotto prod = new Prodotto();
        	prod.setCodice(1);
        	prod.setTaglia("S");
        	Deposito dep = new Deposito();
        	dep.setID(1);
        	int disponibilita = -15;
        	conservatoModelDS.doUpdate(prod, dep, disponibilita);
    	});
    }
    
    @Test
    @DisplayName("TCU2_4_4_1 doDeleteTestSalva")
    public void doDeleteTestSalva() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(ConservatoModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doDeleteConservato.xml"))
                .getTable(table);
    	
    	Prodotto prod = new Prodotto();
    	prod.setCodice(1);
    	prod.setTaglia("S");
    	Deposito dep = new Deposito();
    	dep.setID(1);
    	try {
			conservatoModelDS.doDelete(prod, dep);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}

        ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    @DisplayName("TCU2_4_4_2 doDeleteTestNonSalva")
    public void doDeleteTestNonSalva() {
    	assertThrows(CheckException.class, () -> {
    		Prodotto prod = new Prodotto();
        	prod.setCodice(1);
        	prod.setTaglia("S");
        	Deposito dep = new Deposito();
        	dep.setID(3);
        	conservatoModelDS.doDelete(prod, dep);
    	});
    }
    
    @Test
    @DisplayName("TCU2_4_5_1 checkDisponibilitaTestPresenti")
    public void checkDisponibilitaTestPresenti() {
    	ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();
    	Prodotto prod = new Prodotto();
    	prod.setCodice(1);
    	prod.setTaglia("S");
    	prod.setQuantita(2);
    	prodotti.add(prod);
    	
    	prod = new Prodotto();
    	prod.setCodice(3);
    	prod.setTaglia("M");
    	prod.setQuantita(2);
    	prodotti.add(prod);
    	
    	prod = new Prodotto();
    	prod.setCodice(2);
    	prod.setTaglia("M");
    	prod.setQuantita(2);
    	prodotti.add(prod);
    	

    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	expected.add(prod);
    	
    	ArrayList<Prodotto> actual = null;
    	actual = conservatoModelDS.checkDisponibilita(prodotti);
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU2_4_5_2 checkDisponibilitaTestVuota")
    public void checkDisponibilitaTestVuota() {
    	ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	ArrayList<Prodotto> actual = null;
    	actual = conservatoModelDS.checkDisponibilita(prodotti);
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU2_4_5_3 checkDisponibilitaTestNull")
    public void checkDisponibilitaTestNull() {
    	ArrayList<Prodotto> prodotti = null;
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	ArrayList<Prodotto> actual = null;
    	actual = conservatoModelDS.checkDisponibilita(prodotti);
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU2_4_5_4 checkDisponibilitaTestNonEsiste")
    public void checkDisponibilitaTestNonEsiste() {
    	ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();
    	Prodotto prod = new Prodotto();
    	prod.setCodice(15);
    	prod.setTaglia("S");
    	prod.setQuantita(2);
    	prodotti.add(prod);
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	
    	ArrayList<Prodotto> actual = null;
    	actual = conservatoModelDS.checkDisponibilita(prodotti);
    	assertEquals(expected, actual);
    }
	
}
