package utenteManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.DataSetException;
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

public class NumeroModelDSTest {

	private static String expectedPath = "resources/db/expected/utenteManagement/";
	private static String initPath = "resources/db/init/utenteManagement/";
	private static String table = "NumTel";
    private static IDatabaseTester tester;
	private DataSource ds;
    private NumeroModelDS numeroModelDS;
    
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
                .build(NumeroModelDSTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Prepara lo stato iniziale di default
        refreshDataSet(initPath + "NumeroInit.xml");
        ds = Mockito.mock(DataSource.class);
        Mockito.when(ds.getConnection())
        .thenReturn(tester.getConnection().getConnection());
        numeroModelDS = new NumeroModelDS(ds, "christian.gambardella@gmail.com");
    }

    @AfterEach
    public void tearDown() throws Exception {
        tester.onTearDown();
    }
    
    @Test
    @DisplayName("TCU1_3_1_1 doRetrieveByKeyTestPresente")
    public void doRetrieveByKeyTestPresente(){
    	String expected = "+393885948313";
    	String actual = null;
    	try {
			actual = numeroModelDS.doRetrieveByKey("+393885948313");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU1_3_1_2 doRetrieveByKeyTestNonPresente")
    public void doRetrieveByKeyTestNonPresente(){
    	String expected = null;
    	String actual = null;
    	try {
			actual = numeroModelDS.doRetrieveByKey("+393885948888");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU1_3_1_3 doRetrieveByKeyTestVuoto")
    public void doRetrieveByKeyTestVuoto(){
    	assertThrows(CheckException.class, () -> {
    		numeroModelDS.doRetrieveByKey("");
    	});
    }
    
    @Test
    @DisplayName("TCU1_3_1_4 doRetrieveByKeyTestNull")
    public void doRetrieveByKeyTestNull(){
    	assertThrows(CheckException.class, () -> {
    		numeroModelDS.doRetrieveByKey(null);
    	});
    }
    
    @Test
    @DisplayName("TCU1_3_2_1 doRetrieveAllTestAsc")
    public void doRetrieveAllTestAsc() {
    	ArrayList<String> expected = new ArrayList<String>();
    	expected.add("+393125641111");
    	expected.add("+393125642855");
    	expected.add("+393885948313");
    	
    	ArrayList<String> actual = null;
    	try {
			actual = numeroModelDS.doRetrieveAll("ASC");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU1_3_2_2 doRetrieveAllTestDesc")
    public void doRetrieveAllTestDesc() {
    	ArrayList<String> expected = new ArrayList<String>();
    	expected.add("+393885948313");
    	expected.add("+393125642855");
    	expected.add("+393125641111");
    	
    	ArrayList<String> actual = null;
    	try {
			actual = numeroModelDS.doRetrieveAll("DESC");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU1_3_2_3 doRetrieveAllTestVuota")
    public void doRetrieveAllTestVuota() {
    	ArrayList<String> expected = new ArrayList<String>();
    	expected.add("+393125641111");
    	expected.add("+393125642855");
    	expected.add("+393885948313");
    	
    	ArrayList<String> actual = null;
    	try {
			actual = numeroModelDS.doRetrieveAll("");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU1_3_2_4 doRetrieveAllTestNull")
    public void doRetrieveAllTestNull() {
    	ArrayList<String> expected = new ArrayList<String>();
    	expected.add("+393125641111");
    	expected.add("+393125642855");
    	expected.add("+393885948313");
    	
    	ArrayList<String> actual = null;
    	try {
			actual = numeroModelDS.doRetrieveAll(null);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU1_3_2_5 doRetrieveAllTestAltro")
    public void doRetrieveAllTestAltro() {
    	assertThrows(CheckException.class, () -> {
    		numeroModelDS.doRetrieveAll("ascendente");
    	});
    }
    
    @Test
    @DisplayName("TCU1_3_3_1 doSaveTestSalva")
    public void doSaveTestSalva() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(NumeroModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doSaveNumeroCorretto.xml"))
                .getTable(table);
    	
    	String num = "+393668782843";
    	
    	try {
    		numeroModelDS.doSave(num);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    @DisplayName("TCU1_3_3_2 doSaveTestNonSalva")
    public void doSaveTestNonSalva() {
    	String num = "+393125641111";
    	
    	assertThrows(CheckException.class, () -> {
    		numeroModelDS.doSave(num);
    	});
    }
    
    @Test
    @DisplayName("TCU1_3_3_3 doSaveTestVuoto")
    public void doSaveTestVuoto() {
    	assertThrows(CheckException.class, () -> {
    		numeroModelDS.doSave("");
    	});
    }
    
    @Test
    @DisplayName("TCU1_3_3_4 doSaveTestNull")
    public void doSaveTestNull() {
    	assertThrows(CheckException.class, () -> {
    		numeroModelDS.doSave(null);
    	});
    }
    
    @Test
    @DisplayName("TCU1_3_3_5 doSaveTestFormatoIncorretto")
    public void doSaveTestFormatoIncorretto() {
    	assertThrows(CheckException.class, () -> {
    		numeroModelDS.doSave("32145");
    	});
    }
    
    @Test
    @DisplayName("TCU1_3_4_1 doUpdateTestSalva")
    public void doUpdateTestSalva() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(NumeroModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doUpdateNumeroCorretto.xml"))
                .getTable(table);
    	
    	String oldNum = "+393125641111";
    	String newNum = "+393125644545";
    	
    	try {
    		numeroModelDS.doUpdate(oldNum, newNum);
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
    @DisplayName("TCU1_3_4_2 doUpdateTestNonSalva")
    public void doUpdateTestNonSalva(String oldNum, String newNum) {
    	assertThrows(CheckException.class, () -> {
    		numeroModelDS.doUpdate(oldNum, newNum);
    	});
    }
    
    private static Stream<Arguments> doUpdateTestProvider(){
    	return Stream.of(
    			//newNumero vuoto
    			Arguments.of("+393125641111", ""),
    			//newNumero null
    			Arguments.of("+393125641111", null),
    			//newNumero formato incorretto
    			Arguments.of("+393125641111", "+393125"),
    			//newNumero presente nel Database
    			Arguments.of("+393125641111", "+393125642855"),
    			//oldNumero non presente nel Database
    			Arguments.of("+393125641115", "+393125644545")
    			);
    }
    
    @Test
    @DisplayName("TCU1_3_5_1 doDeleteTestPresente")
    public void doDeleteTestPresente() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(NumeroModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doDeleteNumero.xml"))
                .getTable(table);
    	
    	try {
    		numeroModelDS.doDelete("+393885948313");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    @DisplayName("TCU1_3_5_2 doDeleteTestNonPresente")
    public void doDeleteTestNonPresente() {
    	assertThrows(CheckException.class, () -> {
    		numeroModelDS.doDelete("+393885948");
    	});
    }
    
    @Test
    @DisplayName("TCU1_3_5_4 doDeleteTestVuoto")
    public void doDeleteTestVuoto() {
    	assertThrows(CheckException.class, () -> {
    		numeroModelDS.doDelete("");
    	});
    }
    
    @Test
    @DisplayName("TCU1_3_5_5 doDeleteTestNull")
    public void doDeleteTestNull() throws Exception {
    	assertThrows(CheckException.class, () -> {
    		numeroModelDS.doDelete(null);
    	});
    }
    
}
