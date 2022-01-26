package utenteManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

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
    public void doRetrieveByKeyTestPresente(){
    	String expected = "+393885948313";
    	String actual = null;
    	try {
			actual = numeroModelDS.doRetrieveByKey("+393885948313");
			//System.out.println(expected.getEmail() + " " + expected.getPassword());
			//System.out.println(actual.getEmail() + " " + actual.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveByKeyTestNonPresente(){
    	String expected = null;
    	String actual = null;
    	try {
			actual = numeroModelDS.doRetrieveByKey("+393885948888");
			//System.out.println(expected.getEmail() + " " + expected.getPassword());
			//System.out.println(actual.getEmail() + " " + actual.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveByKeyTestVuoto(){
    	String expected = null;
    	String actual = null;
    	try {
			actual = numeroModelDS.doRetrieveByKey("");
			//System.out.println(expected.getEmail() + " " + expected.getPassword());
			//System.out.println(actual.getEmail() + " " + actual.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveByKeyTestNull(){
    	String expected = null;
    	String actual = null;
    	try {
			actual = numeroModelDS.doRetrieveByKey(null);
			//System.out.println(expected.getEmail() + " " + expected.getPassword());
			//System.out.println(actual.getEmail() + " " + actual.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestAsc() throws DataSetException {
    	ArrayList<String> expected = new ArrayList<String>();
    	expected.add("+393125641111");
    	expected.add("+393125642855");
    	expected.add("+393885948313");
    	
    	ArrayList<String> actual = null;
    	try {
			actual = numeroModelDS.doRetrieveAll("ASC");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestDesc() throws DataSetException {
    	ArrayList<String> expected = new ArrayList<String>();
    	expected.add("+393885948313");
    	expected.add("+393125642855");
    	expected.add("+393125641111");
    	
    	ArrayList<String> actual = null;
    	try {
			actual = numeroModelDS.doRetrieveAll("DESC");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestVuota() throws DataSetException {
    	ArrayList<String> expected = new ArrayList<String>();
    	expected.add("+393125641111");
    	expected.add("+393125642855");
    	expected.add("+393885948313");
    	
    	ArrayList<String> actual = null;
    	try {
			actual = numeroModelDS.doRetrieveAll("");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestNull() throws DataSetException {
    	ArrayList<String> expected = new ArrayList<String>();
    	expected.add("+393125641111");
    	expected.add("+393125642855");
    	expected.add("+393885948313");
    	
    	ArrayList<String> actual = null;
    	try {
			actual = numeroModelDS.doRetrieveAll(null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestAltro() throws DataSetException {
    	ArrayList<String> expected = new ArrayList<String>();
    	expected.add("+393125641111");
    	expected.add("+393125642855");
    	expected.add("+393885948313");
    	
    	ArrayList<String> actual = null;
    	try {
			actual = numeroModelDS.doRetrieveAll("ascendente");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doSaveTestSalva() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(NumeroModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doSaveNumeroCorretto.xml"))
                .getTable(table);
    	
    	String num = "+393668782843";
    	
    	try {
    		numeroModelDS.doSave(num);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void doSaveTestNonSalva() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(NumeroModelDSTest.class.getClassLoader().getResourceAsStream(initPath + "NumeroInit.xml"))
                .getTable(table);
    	
    	String num = "+393125641111";
    	
    	try {
    		numeroModelDS.doSave(num);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void doSaveTestVuoto() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(NumeroModelDSTest.class.getClassLoader().getResourceAsStream(initPath + "NumeroInit.xml"))
                .getTable(table);
    	
    	String num = "";
    	
    	try {
    		numeroModelDS.doSave(num);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void doSaveTestNull() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(NumeroModelDSTest.class.getClassLoader().getResourceAsStream(initPath + "NumeroInit.xml"))
                .getTable(table);
    	
    	String num = null;
    	
    	try {
    		numeroModelDS.doSave(num);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void doSaveTestFormatoIncorretto() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(NumeroModelDSTest.class.getClassLoader().getResourceAsStream(initPath + "NumeroInit.xml"))
                .getTable(table);
    	
    	String num = "32145";
    	
    	try {
    		numeroModelDS.doSave(num);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void doUpdateTestSalva() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(NumeroModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doUpdateNumeroCorretto.xml"))
                .getTable(table);
    	
    	String oldNum = "+393125641111";
    	String newNum = "+393125644545";
    	
    	try {
    		numeroModelDS.doUpdate(oldNum, newNum);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @ParameterizedTest
    @MethodSource("doUpdateTestProvider")
    public void doUpdateTestNonSalva(String oldNum, String newNum) throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(NumeroModelDSTest.class.getClassLoader().getResourceAsStream(initPath + "NumeroInit.xml"))
                .getTable(table);
    	
    	try {
    		numeroModelDS.doUpdate(oldNum, newNum);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    private static Stream<Arguments> doUpdateTestProvider(){
    	return Stream.of(
    			//oldNumero vuoto
    			Arguments.of("", "+393125644545"),
    			//oldNumero vuota
    			Arguments.of(null, "+393125644545"),
    			//oldNumero formato incorretto
    			Arguments.of("+3931", "+393125644545"),
    			//newNumero vuoto
    			Arguments.of("+393125641111", ""),
    			//newNumero null
    			Arguments.of("+393125641111", null),
    			//newNumero formato incorretto
    			Arguments.of("+393125641111", "+393125")
    			);
    }
    
    @Test
    public void doUpdateTestNewNumPresente() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(NumeroModelDSTest.class.getClassLoader().getResourceAsStream(initPath + "NumeroInit.xml"))
                .getTable(table);
    	
    	String oldNum = "+393125641111";
    	String newNum = "+393125642855";
    	
    	try {
    		numeroModelDS.doUpdate(oldNum, newNum);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
	
    @Test
    public void doUpdateTestOldNumNonPresente() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(NumeroModelDSTest.class.getClassLoader().getResourceAsStream(initPath + "NumeroInit.xml"))
                .getTable(table);
    	
    	String oldNum = "+393125641115";
    	String newNum = "+393125644545";
    	
    	try {
    		numeroModelDS.doUpdate(oldNum, newNum);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void doDeleteTestPresente() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(NumeroModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doDeleteNumero.xml"))
                .getTable(table);
    	
    	try {
    		numeroModelDS.doDelete("+393885948313");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void doDeleteTestNonPresente() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(NumeroModelDSTest.class.getClassLoader().getResourceAsStream(initPath + "NumeroInit.xml"))
                .getTable(table);
    	
    	try {
    		numeroModelDS.doDelete("+393885948");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
}
