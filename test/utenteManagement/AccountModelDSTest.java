package utenteManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.dbunit.Assertion;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.DataSetException;
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

public class AccountModelDSTest {

	private static String expectedPath = "resources/db/expected/utenteManagement/";
	private static String initPath = "resources/db/init/utenteManagement/";
	private static String table = "Account";
    private static IDatabaseTester tester;
	private DataSource ds;
    private AccountModelDS accountModelDS;
    
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
                .build(AccountModelDSTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Prepara lo stato iniziale di default
        refreshDataSet(initPath + "AccountInit.xml");
        ds = Mockito.mock(DataSource.class);
        Mockito.when(ds.getConnection())
        .thenReturn(tester.getConnection().getConnection());
        accountModelDS = new AccountModelDS(ds);
    }

    @AfterEach
    public void tearDown() throws Exception {
        tester.onTearDown();
    }

    @Test
    public void doRetrieveByKeyTestPresente() {
    	Account expected = new Account("vincenzo.offertucci@gmail.com", "Vincenzo", "Offertucci", "2000-10-31", "d0a845a8304784446b1a261ba3b59e27", "BeCenzo");
    	Account actual = null;
    	try {
			actual = accountModelDS.doRetrieveByKey("vincenzo.offertucci@gmail.com");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveByKeyTestNonPresente() {
    	Account expected = new Account();
    	Account actual = null;
    	try {
			actual = accountModelDS.doRetrieveByKey("vincenzo@gmail.com");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveByKeyTestVuota() {
    	Account expected = new Account();
    	Account actual = null;
    	try {
			actual = accountModelDS.doRetrieveByKey("");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveByKeyTestFormatoIncorretto() {
    	Account expected = new Account();
    	Account actual = null;
    	try {
			actual = accountModelDS.doRetrieveByKey("vincenzo.com");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }

    @Test
    public void doRetrieveAllTestAsc() {
        ArrayList<Account> expected = new ArrayList<Account>();
        expected.add(new Account("christian.gambardella@gmail.com", "Christian", "Gambardella", "2000-12-11", "93f6fc14ab347f6dd1ea7de992877097", "UnruhMaker"));
        expected.add(new Account("vincenzo.offertucci@gmail.com", "Vincenzo", "Offertucci", "2000-10-31", "d0a845a8304784446b1a261ba3b59e27", "BeCenzo"));
        expected.add(new Account("vittorio.armenante@gmail.com", "Vittorio", "Armenante", "2000-09-11", "8e11bf5b2f5ab41eba58fe8b1e6ab436", "Peppe$on"));

        ArrayList<Account> actual = null;
        try {
			actual = accountModelDS.doRetrieveAll("ASC");
		} catch (SQLException e) {
			e.printStackTrace();
		}
        assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestDesc() {
        ArrayList<Account> expected = new ArrayList<Account>();
        expected.add(new Account("vittorio.armenante@gmail.com", "Vittorio", "Armenante", "2000-09-11", "8e11bf5b2f5ab41eba58fe8b1e6ab436", "Peppe$on"));
        expected.add(new Account("vincenzo.offertucci@gmail.com", "Vincenzo", "Offertucci", "2000-10-31", "d0a845a8304784446b1a261ba3b59e27", "BeCenzo"));
        expected.add(new Account("christian.gambardella@gmail.com", "Christian", "Gambardella", "2000-12-11", "93f6fc14ab347f6dd1ea7de992877097", "UnruhMaker"));

        ArrayList<Account> actual = null;
        try {
			actual = accountModelDS.doRetrieveAll("DESC");
		} catch (SQLException e) {
			e.printStackTrace();
		}
        assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestVuoto() {
        ArrayList<Account> expected = new ArrayList<Account>();
        expected.add(new Account("vincenzo.offertucci@gmail.com", "Vincenzo", "Offertucci", "2000-10-31", "d0a845a8304784446b1a261ba3b59e27", "BeCenzo"));
        expected.add(new Account("christian.gambardella@gmail.com", "Christian", "Gambardella", "2000-12-11", "93f6fc14ab347f6dd1ea7de992877097", "UnruhMaker"));
        expected.add(new Account("vittorio.armenante@gmail.com", "Vittorio", "Armenante", "2000-09-11", "8e11bf5b2f5ab41eba58fe8b1e6ab436", "Peppe$on"));

        ArrayList<Account> actual = null;
        try {
			actual = accountModelDS.doRetrieveAll("");
		} catch (SQLException e) {
			e.printStackTrace();
		}
        assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestNull() {
        ArrayList<Account> expected = new ArrayList<Account>();
        expected.add(new Account("vincenzo.offertucci@gmail.com", "Vincenzo", "Offertucci", "2000-10-31", "d0a845a8304784446b1a261ba3b59e27", "BeCenzo"));
        expected.add(new Account("christian.gambardella@gmail.com", "Christian", "Gambardella", "2000-12-11", "93f6fc14ab347f6dd1ea7de992877097", "UnruhMaker"));
        expected.add(new Account("vittorio.armenante@gmail.com", "Vittorio", "Armenante", "2000-09-11", "8e11bf5b2f5ab41eba58fe8b1e6ab436", "Peppe$on"));

        ArrayList<Account> actual = null;
        try {
			actual = accountModelDS.doRetrieveAll(null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        assertEquals(expected, actual);
    }
    
    @Test
    public void doRetrieveAllTestAltro() {
        ArrayList<Account> expected = new ArrayList<Account>();
        expected.add(new Account("vincenzo.offertucci@gmail.com", "Vincenzo", "Offertucci", "2000-10-31", "d0a845a8304784446b1a261ba3b59e27", "BeCenzo"));
        expected.add(new Account("christian.gambardella@gmail.com", "Christian", "Gambardella", "2000-12-11", "93f6fc14ab347f6dd1ea7de992877097", "UnruhMaker"));
        expected.add(new Account("vittorio.armenante@gmail.com", "Vittorio", "Armenante", "2000-09-11", "8e11bf5b2f5ab41eba58fe8b1e6ab436", "Peppe$on"));

        ArrayList<Account> actual = null;
        try {
			actual = accountModelDS.doRetrieveAll("discendente");
		} catch (SQLException e) {
			e.printStackTrace();
		}
        assertEquals(expected, actual);
    }
    
    @Test
    public void doSaveTestSalva() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(AccountModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doSaveAccountCorretto.xml"))
                .getTable(table);
    	
    	Account acc = new Account("vincenzo@gmail.com", "Vincenzo", "Offertucci", "2000-08-15", "Provaa", "ProvaIG");
    	
    	try {
			accountModelDS.doSave(acc);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @ParameterizedTest
    @MethodSource("doSaveTestProvider")
    public void doSaveTestNonSalva(String email, String nome, String cognome, String dataNascita, String password, String nomeIG) throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(AccountModelDSTest.class.getClassLoader().getResourceAsStream(initPath + "AccountInit.xml"))
                .getTable(table);
    	
    	Account acc = new Account(email, nome, cognome, dataNascita, password, nomeIG);
    	
    	try {
			accountModelDS.doSave(acc);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    private static Stream<Arguments> doSaveTestProvider(){
    	return Stream.of(
    			//formato email non corretto
    			Arguments.of("vincenzo.com", "Vincenzo", "Offertucci", "2000-08-15", "Provaa", "ProvaIG"),
    			//email vuota
    			Arguments.of("", "Vincenzo", "Offertucci", "2000-08-15", "Provaa", "ProvaIG"),
    			//email presente nel DB
    			Arguments.of("vincenzo.offertucci@gmail.com", "Vincenzo", "Offertucci", "2000-08-15", "Provaa", "ProvaIG"),
    			//nome vuoto
    			Arguments.of("vincenzo@gmail.com", "", "Offertucci", "2000-08-15", "Provaa", "ProvaIG"),
    			//nome null
    			Arguments.of("vincenzo@gmail.com", null, "Offertucci", "2000-08-15", "Provaa", "ProvaIG"),
    			//cognome vuoto
    			Arguments.of("vincenzo@gmail.com", "Vincenzo", "", "2000-08-15", "Provaa", "ProvaIG"),
    			//cognome null
    			Arguments.of("vincenzo@gmail.com", "Vincenzo", null, "2000-08-15", "Provaa", "ProvaIG"),
    			//formato data di nascita non corretto
    			Arguments.of("vincenzo@gmail.com", "Vincenzo", "Offertucci", "2000-13-15", "Provaa", "ProvaIG"),
    			//formato password non corretto
    			Arguments.of("vincenzo@gmail.com", "Vincenzo", "Offertucci", "2000-08-15", "Pr", "ProvaIG")
    			);
    }
    
    @Test
    public void doSaveTestNull() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(AccountModelDSTest.class.getClassLoader().getResourceAsStream(initPath + "AccountInit.xml"))
                .getTable(table);
    	
    	Account acc = new Account();
    	
    	try {
			accountModelDS.doSave(acc);
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
                .build(AccountModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doUpdateAccountCorretto.xml"))
                .getTable(table);
    	
    	Account acc = new Account("vincenzo.offertucci@gmail.com", "Enzo", "Off", "2000-08-15", "Provaa", "ProvaIG");
    	try {
			accountModelDS.doUpdate(acc);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @ParameterizedTest
    @MethodSource("doUpdateTestProvider")
    public void doUpdateTestNonSalva(String email, String nome, String cognome, String dataNascita, String password, String nomeIG) throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(AccountModelDSTest.class.getClassLoader().getResourceAsStream(initPath + "AccountInit.xml"))
                .getTable(table);
    	
    	Account acc = new Account(email, nome, cognome, dataNascita, password, nomeIG);
    	
    	try {
			accountModelDS.doUpdate(acc);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    private static Stream<Arguments> doUpdateTestProvider(){
    	return Stream.of(
    			//formato email non corretto
    			Arguments.of("vincenzo.com", "Enzo", "Off", "2000-08-15", "Provaa", "ProvaIG"),
    			//email vuota
    			Arguments.of("", "Enzo", "Off", "2000-08-15", "Provaa", "ProvaIG"),
    			//email non presente nel DB
    			Arguments.of("vincenzo@gmail.com", "Enzo", "Off", "2000-08-15", "Provaa", "ProvaIG"),
    			//nome vuoto
    			Arguments.of("vincenzo.offertucci@gmail.com", "", "Off", "2000-08-15", "Provaa", "ProvaIG"),
    			//nome null
    			Arguments.of("vincenzo.offertucci@gmail.com", null, "Off", "2000-08-15", "Provaa", "ProvaIG"),
    			//cognome vuoto
    			Arguments.of("vincenzo.offertucci@gmail.com", "Enzo", "", "2000-08-15", "Provaa", "ProvaIG"),
    			//cognome null
    			Arguments.of("vincenzo.offertucci@gmail.com", "Enzo", null, "2000-08-15", "Provaa", "ProvaIG"),
    			//formato data di nascita non corretto
    			Arguments.of("vincenzo.offertucci@gmail.com", "Enzo", "Off", "2000-13-15", "Provaa", "ProvaIG"),
    			//formato password non corretto
    			Arguments.of("vincenzo.offertucci@gmail.com", "Enzo", "Off", "2000-08-15", "Pr", "ProvaIG")
    			);
    }
    
    @Test
    public void doUpdateTestNull() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(AccountModelDSTest.class.getClassLoader().getResourceAsStream(initPath + "AccountInit.xml"))
                .getTable(table);
    	
    	Account acc = new Account();
    	try {
			accountModelDS.doUpdate(acc);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void doUpdateInfoTestSalva() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(AccountModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doUpdateInfoAccountCorretto.xml"))
                .getTable(table);
    	
    	Account acc = new Account("vincenzo.offertucci@gmail.com", "Enzo", "Off", "2000-08-15", "", "ProvaIG");
    	try {
			accountModelDS.doUpdateInfo(acc);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @ParameterizedTest
    @MethodSource("doUpdateInfoTestProvider")
    public void doUpdateInfoTestNonSalva(String email, String nome, String cognome, String dataNascita, String nomeIG) throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(AccountModelDSTest.class.getClassLoader().getResourceAsStream(initPath + "AccountInit.xml"))
                .getTable(table);
    	
    	Account acc = new Account(email, nome, cognome, dataNascita, "", nomeIG);
    	
    	try {
			accountModelDS.doUpdateInfo(acc);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    private static Stream<Arguments> doUpdateInfoTestProvider(){
    	return Stream.of(
    			//formato email non corretto
    			Arguments.of("vincenzo.com", "Enzo", "Off", "2000-08-15", "ProvaIG"),
    			//email vuota
    			Arguments.of("", "Enzo", "Off", "2000-08-15", "ProvaIG"),
    			//email non presente nel DB
    			Arguments.of("vincenzo@gmail.com", "Enzo", "Off", "2000-08-15", "ProvaIG"),
    			//nome vuoto
    			Arguments.of("vincenzo.offertucci@gmail.com", "", "Off", "2000-08-15", "ProvaIG"),
    			//nome null
    			Arguments.of("vincenzo.offertucci@gmail.com", null, "Off", "2000-08-15", "ProvaIG"),
    			//cognome vuoto
    			Arguments.of("vincenzo.offertucci@gmail.com", "Enzo", "", "2000-08-15", "ProvaIG"),
    			//cognome null
    			Arguments.of("vincenzo.offertucci@gmail.com", "Enzo", null, "2000-08-15", "ProvaIG"),
    			//formato data di nascita non corretto
    			Arguments.of("vincenzo.offertucci@gmail.com", "Enzo", "Off", "2000-13-15", "ProvaIG")
    			);
    }
    
    @Test
    public void doUpdateInfoTestNull() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(AccountModelDSTest.class.getClassLoader().getResourceAsStream(initPath + "AccountInit.xml"))
                .getTable(table);
    	
    	Account acc = new Account();
    	try {
			accountModelDS.doUpdateInfo(acc);
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
                .build(AccountModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doDeleteAccount.xml"))
                .getTable(table);
        
        Account del = new Account();
        del.setEmail("vincenzo.offertucci@gmail.com");
        try {
			accountModelDS.doDelete(del);
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
                .build(AccountModelDSTest.class.getClassLoader().getResourceAsStream(initPath + "AccountInit.xml"))
                .getTable(table);
        
        Account del = new Account();
        del.setEmail("vincenzo@gmail.com");
        try {
			accountModelDS.doDelete(del);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }

    @Test
    public void doDeleteTestFormatoIncorretto() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(AccountModelDSTest.class.getClassLoader().getResourceAsStream(initPath + "AccountInit.xml"))
                .getTable(table);
        
        Account del = new Account();
        del.setEmail("vincenzo.com");
        try {
			accountModelDS.doDelete(del);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    public void doDeleteTestVuota() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(AccountModelDSTest.class.getClassLoader().getResourceAsStream(initPath + "AccountInit.xml"))
                .getTable(table);
        
        Account del = new Account();
        del.setEmail("");
        try {
			accountModelDS.doDelete(del);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
}
