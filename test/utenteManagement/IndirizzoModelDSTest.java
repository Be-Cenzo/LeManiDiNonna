package utenteManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.dbunit.Assertion;
import org.dbunit.DataSourceBasedDBTestCase;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import checking.CheckException;

public class IndirizzoModelDSTest{

	private static String expectedPath = "resources/db/expected/utenteManagement/";
	private static String initPath = "resources/db/init/utenteManagement/";
	private static String table = "Indirizzo";
    private static IDatabaseTester tester;
	private DataSource ds;
    private IndirizzoModelDS indirizzoModelDS;
    
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
                .build(IndirizzoModelDSTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Prepara lo stato iniziale di default
        refreshDataSet(initPath + "IndirizzoInit.xml");
        ds = Mockito.mock(DataSource.class);
        Mockito.when(ds.getConnection())
        .thenReturn(tester.getConnection().getConnection());
        indirizzoModelDS = new IndirizzoModelDS(ds, "vincenzo.offertucci@gmail.com");
    }

    @AfterEach
    public void tearDown() throws Exception {
        tester.onTearDown();
    }
    
    @Test
    @DisplayName("TCU1_2_1_1 doRetrieveByKeyTestPresente")
    public void doRetrieveByKeyTestPresente(){
    	Indirizzo expected = new Indirizzo(1, "vincenzo.offertucci@gmail.com", "Napoli", "Ottaviano", "Caracelli", 8, "80044");
    	Indirizzo actual = null;
    	try {
			actual = indirizzoModelDS.doRetrieveByKey(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU1_2_1_2 doRetrieveByKeyTestNonPresente")
    public void doRetrieveByKeyTestNonPresente(){
    	Indirizzo expected = null;
    	Indirizzo actual = null;
    	try {
			actual = indirizzoModelDS.doRetrieveByKey(5);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU1_2_2_1 doRetrieveAllTestAsc")
    public void doRetrieveAllTestAsc() {
    	ArrayList<Indirizzo> expected = new ArrayList<Indirizzo>();
    	expected.add(new Indirizzo(2, "vincenzo.offertucci@gmail.com", "Milano", "Milano", "Montenapoleone", 27, "20121"));
    	expected.add(new Indirizzo(1, "vincenzo.offertucci@gmail.com", "Napoli", "Ottaviano", "Caracelli", 8, "80044"));
    	
    	ArrayList<Indirizzo> actual = null;
    	try {
			actual = indirizzoModelDS.doRetrieveAll("ASC");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU1_2_2_2 doRetrieveAllTestDesc")
    public void doRetrieveAllTestDesc() {
    	ArrayList<Indirizzo> expected = new ArrayList<Indirizzo>();
    	expected.add(new Indirizzo(1, "vincenzo.offertucci@gmail.com", "Napoli", "Ottaviano", "Caracelli", 8, "80044"));
    	expected.add(new Indirizzo(2, "vincenzo.offertucci@gmail.com", "Milano", "Milano", "Montenapoleone", 27, "20121"));
    	
    	ArrayList<Indirizzo> actual = null;
    	try {
			actual = indirizzoModelDS.doRetrieveAll("DESC");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU1_2_2_3 doRetrieveAllTestVuota")
    public void doRetrieveAllTestVuota() {
    	ArrayList<Indirizzo> expected = new ArrayList<Indirizzo>();
    	expected.add(new Indirizzo(1, "vincenzo.offertucci@gmail.com", "Napoli", "Ottaviano", "Caracelli", 8, "80044"));
    	expected.add(new Indirizzo(2, "vincenzo.offertucci@gmail.com", "Milano", "Milano", "Montenapoleone", 27, "20121"));
    	
    	ArrayList<Indirizzo> actual = null;
    	try {
			actual = indirizzoModelDS.doRetrieveAll("");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU1_2_2_4 doRetrieveAllTestNull")
    public void doRetrieveAllTestNull() {
    	ArrayList<Indirizzo> expected = new ArrayList<Indirizzo>();
    	expected.add(new Indirizzo(1, "vincenzo.offertucci@gmail.com", "Napoli", "Ottaviano", "Caracelli", 8, "80044"));
    	expected.add(new Indirizzo(2, "vincenzo.offertucci@gmail.com", "Milano", "Milano", "Montenapoleone", 27, "20121"));
    	
    	ArrayList<Indirizzo> actual = null;
    	try {
			actual = indirizzoModelDS.doRetrieveAll(null);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU1_2_2_5 doRetrieveAllTestAltro")
    public void doRetrieveAllTestAltro() {
    	assertThrows(CheckException.class, () -> {
    		ArrayList<Indirizzo> actual = indirizzoModelDS.doRetrieveAll("ascendente");
    	});
    }
    
    @Test
    @DisplayName("TCU1_2_3_1 doSaveTestSalva")
    public void doSaveTestSalva() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(IndirizzoModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doSaveIndirizzoCorretto.xml"))
                .getTable(table);
    	
    	Indirizzo ind = new Indirizzo(3, "vincenzo.offertucci@gmail.com", "Salerno", "Mercato San Severino", "Fenizia e Marotta", 5, "84085");
    	
    	try {
    		indirizzoModelDS.doSave(ind);
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
    @DisplayName("TCU1_2_3_2 doSaveTestNonSalva")
    public void doSaveTestNonSalva(int id, String email, String provincia, String comune, String via, int civico, String CAP) {
    	Indirizzo ind = new Indirizzo(id, email, provincia, comune, via, civico, CAP);
    	
    	assertThrows(CheckException.class, () -> {
    		indirizzoModelDS.doSave(ind);
    	});
    	
    }
    
    private static Stream<Arguments> doSaveTestProvider(){
    	return Stream.of(
    			//id ed email presenti nel database
    			Arguments.of(1, "vincenzo.offertucci@gmail.com", "Salerno", "Mercato San Severino", "Fenizia e Marotta", 5, "84085"),
    			//provincia vuota
    			Arguments.of(3, "vincenzo.offertucci@gmail.com", "", "Mercato San Severino", "Fenizia e Marotta", 5, "84085"),
    			//provincia null
    			Arguments.of(3, "vincenzo.offertucci@gmail.com", null, "Mercato San Severino", "Fenizia e Marotta", 5, "84085"),
    			//comune vuoto
    			Arguments.of(3, "vincenzo.offertucci@gmail.com", "Salerno", "", "Fenizia e Marotta", 5, "84085"),
    			//comune null
    			Arguments.of(3, "vincenzo.offertucci@gmail.com", "Salerno", null, "Fenizia e Marotta", 5, "84085"),
    			//via vuota
    			Arguments.of(3, "vincenzo.offertucci@gmail.com", "Salerno", "Mercato San Severino", "", 5, "84085"),
    			//via null
    			Arguments.of(3, "vincenzo.offertucci@gmail.com", "Salerno", "Mercato San Severino", null, 5, "84085"),
    			//cap vuoto
    			Arguments.of(3, "vincenzo.offertucci@gmail.com", "Salerno", "Mercato San Severino", "Fenizia e Marotta", 5, ""),
    			//cap null
    			Arguments.of(3, "vincenzo.offertucci@gmail.com", "Salerno", "Mercato San Severino", "Fenizia e Marotta", 5, null)
    			);
    }
    
    @Test
    @DisplayName("TCU1_2_3_3 doSaveTestNonSalva")
    public void doSaveTestNonSalva() {
    	Indirizzo ind = null;
    	
    	assertThrows(CheckException.class, () -> {
    		indirizzoModelDS.doSave(ind);
    	});
  
    }
    
    @Test
    @DisplayName("TCU1_2_4_1 doUpdateTestSalva")
    public void doUpdateTestSalva() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(IndirizzoModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doUpdateIndirizzoCorretto.xml"))
                .getTable(table);
    	
    	Indirizzo ind = new Indirizzo(2, "vincenzo.offertucci@gmail.com", "Salerno", "Mercato San Severino", "Fenizia e Marotta", 5, "84085");
    	
    	try {
    		indirizzoModelDS.doUpdate(ind);
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
    @DisplayName("TCU1_2_4_2 doUpdateTestNonSalva")
    public void doUpdateTestNonSalva(int id, String email, String provincia, String comune, String via, int civico, String CAP){
    	Indirizzo ind = new Indirizzo(id, email, provincia, comune, via, civico, CAP);
    	
    	assertThrows(CheckException.class, () -> {
    		indirizzoModelDS.doUpdate(ind);
    	});
    }
    
    private static Stream<Arguments> doUpdateTestProvider(){
    	return Stream.of(
    			//id ed email non presenti nel database
    			Arguments.of(3, "vincenzo.offertucci@gmail.com", "Salerno", "Mercato San Severino", "Fenizia e Marotta", 5, "84085"),
    			//provincia vuota
    			Arguments.of(2, "vincenzo.offertucci@gmail.com", "", "Mercato San Severino", "Fenizia e Marotta", 5, "84085"),
    			//provincia null
    			Arguments.of(2, "vincenzo.offertucci@gmail.com", null, "Mercato San Severino", "Fenizia e Marotta", 5, "84085"),
    			//comune vuoto
    			Arguments.of(2, "vincenzo.offertucci@gmail.com", "Salerno", "", "Fenizia e Marotta", 5, "84085"),
    			//comune null
    			Arguments.of(2, "vincenzo.offertucci@gmail.com", "Salerno", null, "Fenizia e Marotta", 5, "84085"),
    			//via vuota
    			Arguments.of(2, "vincenzo.offertucci@gmail.com", "Salerno", "Mercato San Severino", "", 5, "84085"),
    			//via null
    			Arguments.of(2, "vincenzo.offertucci@gmail.com", "Salerno", "Mercato San Severino", null, 5, "84085"),
    			//cap vuoto
    			Arguments.of(2, "vincenzo.offertucci@gmail.com", "Salerno", "Mercato San Severino", "Fenizia e Marotta", 5, ""),
    			//cap null
    			Arguments.of(2, "vincenzo.offertucci@gmail.com", "Salerno", "Mercato San Severino", "Fenizia e Marotta", 5, null)
    			);
    }
    
    @Test
    @DisplayName("TCU1_2_4_3 doUpdateTestNonSalva")
    public void doUpdateTestNonSalva() {
    	Indirizzo ind = null;
    	
    	assertThrows(CheckException.class, () -> {
    		indirizzoModelDS.doUpdate(ind);
    	});
    }
    
    @Test
    @DisplayName("TCU1_2_5_1 doDeleteTestPresente")
    public void doDeleteTestPresente() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(IndirizzoModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doDeleteIndirizzo.xml"))
                .getTable(table);
    	Indirizzo del = new Indirizzo(1, "vincenzo.offertucci@gmail.com", "Napoli", "Ottaviano", "Caracelli", 8, "80044");
    	try {
			indirizzoModelDS.doDelete(del);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    @DisplayName("TCU1_2_5_2 doDeleteTestNonPresente")
    public void doDeleteTestNonPresente() {
    	Indirizzo del = new Indirizzo(15, "vincenzo.offertucci@gmail.com", "Napoli", "Ottaviano", "Caracelli", 8, "80044");
    	
    	assertThrows(CheckException.class, () -> {
    		indirizzoModelDS.doDelete(del);
    	});
    }
	
}
