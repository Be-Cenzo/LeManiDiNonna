package catalogoManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;

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
import org.mockito.Mockito;

public class ConservatoDSTest {

	private static String expectedPath = "resources/db/expected/catalogoManagement/";
	private static String initPath = "resources/db/init/catalogoManagement/";
	private static String table = "Conservato";
    private static IDatabaseTester tester;
	private DataSource ds;
    private ConservatoDS conservatoDS;
    
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
                .build(ConservatoDSTest.class.getClassLoader().getResourceAsStream(filename));
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
        conservatoDS = new ConservatoDS(ds);
    }

    @AfterEach
    public void tearDown() throws Exception {
        tester.onTearDown();
    }
    
    @Test
    public void doRetrieveByKeyTestPresente() {
    	int expected = 30;
    	
    	Prodotto prod = new Prodotto();
    	prod.setCodice(1);
    	prod.setTaglia("M");
    	Deposito dep = new Deposito();
    	dep.setID(2);
    	int actual = -1;
    	try {
			actual = conservatoDS.doRetrieveByKey(prod, dep);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveByKeyTestNonPresente() {
    	int expected = 30;
    	
    	Prodotto prod = new Prodotto();
    	prod.setCodice(1);
    	prod.setTaglia("M");
    	Deposito dep = new Deposito();
    	dep.setID(2);
    	int actual = -1;
    	try {
			actual = conservatoDS.doRetrieveByKey(prod, dep);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doSaveTestSalva() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(ConservatoDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doSaveConservatoCorretto.xml"))
                .getTable(table);
    	
    	Prodotto prod = new Prodotto();
    	prod.setCodice(3);
    	prod.setTaglia("L");
    	Deposito dep = new Deposito();
    	dep.setID(1);
    	int disponibilita = 10;
    	try {
			conservatoDS.doSave(prod, dep, disponibilita);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

        ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void doSaveTestNonSalva() {
    	assertThrows(Exception.class, () -> {
    		Prodotto prod = new Prodotto();
        	prod.setCodice(1);
        	prod.setTaglia("S");
        	Deposito dep = new Deposito();
        	dep.setID(1);
        	int disponibilita = 5;
        	conservatoDS.doSave(prod, dep, disponibilita);
    	});
    }
    
    @Test
    public void doSaveTestNegativa() {
    	assertThrows(Exception.class, () -> {
    		Prodotto prod = new Prodotto();
        	prod.setCodice(1);
        	prod.setTaglia("M");
        	Deposito dep = new Deposito();
        	dep.setID(1);
        	int disponibilita = -15;
        	conservatoDS.doSave(prod, dep, disponibilita);
    	});
    }
    
    @Test
    public void doUpdateTestSalva() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(ConservatoDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doUpdateConservatoCorretto.xml"))
                .getTable(table);
    	
    	Prodotto prod = new Prodotto();
    	prod.setCodice(1);
    	prod.setTaglia("S");
    	Deposito dep = new Deposito();
    	dep.setID(1);
    	int disponibilita = 50;
    	try {
			conservatoDS.doUpdate(prod, dep, disponibilita);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

        ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void doUpdateTestNonSalva() {
    	assertThrows(Exception.class, () -> {
    		Prodotto prod = new Prodotto();
        	prod.setCodice(1);
        	prod.setTaglia("S");
        	Deposito dep = new Deposito();
        	dep.setID(3);
        	int disponibilita = 5;
        	conservatoDS.doUpdate(prod, dep, disponibilita);
    	});
    }
    
    @Test
    public void doUpdateTestNegativa() {
    	assertThrows(Exception.class, () -> {
    		Prodotto prod = new Prodotto();
        	prod.setCodice(1);
        	prod.setTaglia("S");
        	Deposito dep = new Deposito();
        	dep.setID(1);
        	int disponibilita = -15;
        	conservatoDS.doUpdate(prod, dep, disponibilita);
    	});
    }
    
    @Test
    public void doDeleteTestSalva() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(ConservatoDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doDeleteConservato.xml"))
                .getTable(table);
    	
    	Prodotto prod = new Prodotto();
    	prod.setCodice(1);
    	prod.setTaglia("S");
    	Deposito dep = new Deposito();
    	dep.setID(1);
    	try {
			conservatoDS.doDelete(prod, dep);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

        ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void doDeleteTestNonSalva() {
    	assertThrows(Exception.class, () -> {
    		Prodotto prod = new Prodotto();
        	prod.setCodice(1);
        	prod.setTaglia("S");
        	Deposito dep = new Deposito();
        	dep.setID(3);
        	conservatoDS.doDelete(prod, dep);
    	});
    }
	
}
