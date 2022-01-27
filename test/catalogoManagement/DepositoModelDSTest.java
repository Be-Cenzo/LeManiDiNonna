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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class DepositoModelDSTest {


	private static String expectedPath = "resources/db/expected/catalogoManagement/";
	private static String initPath = "resources/db/init/catalogoManagement/";
	private static String table = "Deposito";
    private static IDatabaseTester tester;
	private DataSource ds;
    private DepositoModelDS depositoModelDS;
    
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
                .build(DepositoModelDSTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Prepara lo stato iniziale di default
        refreshDataSet(initPath + "DepositoInit.xml");
        ds = Mockito.mock(DataSource.class);
        Mockito.when(ds.getConnection())
        .thenReturn(tester.getConnection().getConnection());
        depositoModelDS = new DepositoModelDS(ds);
    }

    @AfterEach
    public void tearDown() throws Exception {
        tester.onTearDown();
    }
    
    @Test
    public void doRetrieveByKeyTestPresente() {
    	Deposito expected = new Deposito();
    	expected.setID(1);
    	expected.setLuogo("San Gennarello");
    	Deposito actual = null;
    	try {
			actual = depositoModelDS.doRetrieveByKey(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveByKeyTestNonPresente() {
    	Deposito expected = new Deposito();
    	Deposito actual = null;
    	try {
			actual = depositoModelDS.doRetrieveByKey(10);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestAsc() {
    	ArrayList<Deposito> expected = new ArrayList<Deposito>();
    	Deposito dep = new Deposito();
    	dep.setID(3);
    	dep.setLuogo("Bari");
    	expected.add(dep);
    	
    	dep = new Deposito();
    	dep.setID(2);
    	dep.setLuogo("Mercato San Severino");
    	expected.add(dep);
    	
    	dep = new Deposito();
    	dep.setID(1);
    	dep.setLuogo("San Gennarello");
    	expected.add(dep);
    	
    	ArrayList<Deposito> actual = null;
    	try {
			actual = depositoModelDS.doRetrieveAll("ASC");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestDesc() {
    	ArrayList<Deposito> expected = new ArrayList<Deposito>();
    	Deposito dep = new Deposito();
    	dep.setID(1);
    	dep.setLuogo("San Gennarello");
    	expected.add(dep);
    	
    	dep = new Deposito();
    	dep.setID(2);
    	dep.setLuogo("Mercato San Severino");
    	expected.add(dep);
    	
    	dep = new Deposito();
    	dep.setID(3);
    	dep.setLuogo("Bari");
    	expected.add(dep);
    	
    	
    	ArrayList<Deposito> actual = null;
    	try {
			actual = depositoModelDS.doRetrieveAll("DESC");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestVuoto() {
    	ArrayList<Deposito> expected = new ArrayList<Deposito>();
    	Deposito dep = new Deposito();
    	dep.setID(1);
    	dep.setLuogo("San Gennarello");
    	expected.add(dep);
    	
    	dep = new Deposito();
    	dep.setID(2);
    	dep.setLuogo("Mercato San Severino");
    	expected.add(dep);
    	
    	dep = new Deposito();
    	dep.setID(3);
    	dep.setLuogo("Bari");
    	expected.add(dep);
    	
    	
    	ArrayList<Deposito> actual = null;
    	try {
			actual = depositoModelDS.doRetrieveAll("");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestNull() {
    	ArrayList<Deposito> expected = new ArrayList<Deposito>();
    	Deposito dep = new Deposito();
    	dep.setID(1);
    	dep.setLuogo("San Gennarello");
    	expected.add(dep);
    	
    	dep = new Deposito();
    	dep.setID(2);
    	dep.setLuogo("Mercato San Severino");
    	expected.add(dep);
    	
    	dep = new Deposito();
    	dep.setID(3);
    	dep.setLuogo("Bari");
    	expected.add(dep);
    	
    	
    	ArrayList<Deposito> actual = null;
    	try {
			actual = depositoModelDS.doRetrieveAll(null);
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
    		depositoModelDS.doRetrieveAll("discendente");
    	});
    }
    
    @Test
    public void doSaveTestSalva() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(DepositoModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doSaveDepositoCorretto.xml"))
                .getTable(table);
    	
    	Deposito save = new Deposito();
    	save.setID(4);
    	save.setLuogo("Roccarainola");
    	try {
			depositoModelDS.doSave(save);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void doSaveTestVuoto() {
    	assertThrows(Exception.class, () -> {
			depositoModelDS.doSave(new Deposito());
    	});
    }
    
    @Test
    public void doSaveTestNull(){
    	assertThrows(Exception.class, () -> {
			depositoModelDS.doSave(null);
    	});
    }
    
    @Test
    public void doUpdateTestSalva() throws Exception{
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(DepositoModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doUpdateDepositoCorretto.xml"))
                .getTable(table);
    	
    	Deposito save = new Deposito();
    	save.setID(3);
    	save.setLuogo("Roccarainola");
    	try {
			depositoModelDS.doUpdate(save);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void doUpdateTestNonPresente() {
    	assertThrows(Exception.class, () -> {
    		Deposito dep = new Deposito();
    		dep.setID(15);
    		dep.setLuogo("Roccarainola");
			depositoModelDS.doUpdate(dep);
    	});
    }
    
    @Test
    public void doUpdateTestVuoto() {
    	assertThrows(Exception.class, () -> {
    		Deposito dep = new Deposito();
    		dep.setID(3);
    		dep.setLuogo("");
			depositoModelDS.doUpdate(dep);
    	});
    }
    
    @Test
    public void doUpdateTestNull(){
    	assertThrows(Exception.class, () -> {
    		Deposito dep = new Deposito();
    		dep.setID(3);
    		dep.setLuogo(null);
			depositoModelDS.doUpdate(dep);
    	});
    }
    
    @Test
    public void doDeleteTestPresente() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(DepositoModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doDeleteDeposito.xml"))
                .getTable(table);
    	
    	Deposito del = new Deposito();
    	del.setID(3);
    	try {
    		depositoModelDS.doDelete(del);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void doDeleteTestNonPresente() {
    	assertThrows(Exception.class, () -> {
    		Deposito del = new Deposito();
        	del.setID(15);
    		depositoModelDS.doDelete(del);
    	});
    }
	
}
