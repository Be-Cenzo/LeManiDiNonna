package acquistoManagement;

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
import checking.DBException;

public class SpedizioneModelDSTest {

	private static String expectedPath = "resources/db/expected/acquistoManagement/";
	private static String initPath = "resources/db/init/acquistoManagement/";
	private static String table = "Corriere";
    private static IDatabaseTester tester;
	private DataSource ds;
    private SpedizioneModelDS spedizioneModelDS;
    
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
                .build(SpedizioneModelDSTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Prepara lo stato iniziale di default
        refreshDataSet(initPath + "SpedizioneInit.xml");
        ds = Mockito.mock(DataSource.class);
        Mockito.when(ds.getConnection())
        .thenReturn(tester.getConnection().getConnection());
        spedizioneModelDS = new SpedizioneModelDS(ds);
    }

    @AfterEach
    public void tearDown() throws Exception {
        tester.onTearDown();
    }
    
    @Test
    @DisplayName("TCU3_2_1_1 doRetrieveByKeyTestPresente")
    public void doRetrieveByKeyTestPresente() {
    	Spedizione expected = new Spedizione("veloce", "4 giorni", 10);
    	
    	Spedizione actual = null;
    	try {
			actual = spedizioneModelDS.doRetrieveByKey("veloce");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU3_2_1_2 doRetrieveByKeyTestNonPresente")
    public void doRetrieveByKeyTestNonPresente() {
    	Spedizione expected = new Spedizione();
    	
    	Spedizione actual = null;
    	try {
			actual = spedizioneModelDS.doRetrieveByKey("molto veloce");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU3_2_1_3 doRetrieveByKeyTestVuoto")
    public void doRetrieveByKeyTestVuoto() {
    	assertThrows(CheckException.class, () -> {
			spedizioneModelDS.doRetrieveByKey("");
    	});
    }
    
    @Test
    @DisplayName("TCU3_2_1_4 doRetrieveByKeyTestNull")
    public void doRetrieveByKeyTestNull() {
    	assertThrows(CheckException.class, () -> {
			spedizioneModelDS.doRetrieveByKey(null);
    	});
    }
    
    @Test
    @DisplayName("TCU3_2_2_1 doRetrieveAllTestAsc")
    public void doRetrieveAllTestAsc() {
    	ArrayList<Spedizione> expected = new ArrayList<Spedizione>();
    	expected.add(new Spedizione("lenta", "7 giorni", 7));
    	expected.add(new Spedizione("veloce", "4 giorni", 10));
    	
    	ArrayList<Spedizione> actual = null;
    	
    	try {
			actual = spedizioneModelDS.doRetrieveAll("ASC");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU3_2_2_2 doRetrieveAllTestDesc")
    public void doRetrieveAllTestDesc() {
    	ArrayList<Spedizione> expected = new ArrayList<Spedizione>();
    	expected.add(new Spedizione("veloce", "4 giorni", 10));
    	expected.add(new Spedizione("lenta", "7 giorni", 7));
    	
    	ArrayList<Spedizione> actual = null;
    	
    	try {
			actual = spedizioneModelDS.doRetrieveAll("DESC");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU3_2_2_3 doRetrieveAllTestVuota")
    public void doRetrieveAllTestVuota() {
    	ArrayList<Spedizione> expected = new ArrayList<Spedizione>();
    	expected.add(new Spedizione("lenta", "7 giorni", 7));
    	expected.add(new Spedizione("veloce", "4 giorni", 10));
    	
    	ArrayList<Spedizione> actual = null;
    	
    	try {
			actual = spedizioneModelDS.doRetrieveAll("");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU3_2_2_4 doRetrieveAllTestNull")
    public void doRetrieveAllTestNull() {
    	ArrayList<Spedizione> expected = new ArrayList<Spedizione>();
    	expected.add(new Spedizione("lenta", "7 giorni", 7));
    	expected.add(new Spedizione("veloce", "4 giorni", 10));
    	
    	ArrayList<Spedizione> actual = null;
    	
    	try {
			actual = spedizioneModelDS.doRetrieveAll(null);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU3_2_2_5 doRetrieveAllTestAltro")
    public void doRetrieveAllTestAltro() {
    	assertThrows(CheckException.class, () -> {
			spedizioneModelDS.doRetrieveAll("ascendente");
    	});
    }
    
    @Test
    @DisplayName("TCU3_2_3_1 doSaveTestSalva")
    public void doSaveTestSalva() throws Exception{
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(SpedizioneModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doSaveSpedizioneCorretto.xml"))
                .getTable(table);
    	
    	Spedizione sav = new Spedizione("molto veloce", "2 giorni", 13);
    	
    	try {
    		spedizioneModelDS.doSave(sav);
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
    @DisplayName("TCU3_2_3_2 doSaveTestNonSalva")
    public void doSaveTestNonSalva(String nome, String tempo, float prezzo) {
    	assertThrows(CheckException.class, () -> {
    		Spedizione sav = new Spedizione(nome, tempo, prezzo);
    		spedizioneModelDS.doSave(sav);
    	});
    }
    
    private static Stream<Arguments> doSaveTestProvider(){
    	return Stream.of(
    			//nome già presente nel Database
    			Arguments.of("lenta", "10 giorni", 5),
    			//tempo vuoto
    			Arguments.of("molto veloce", "", 15),
    			//tempo null
    			Arguments.of("molto veloce", null, 15),
    			//prezzo non positivo
    			Arguments.of("molto veloce", "2 giorni", -15),
    			//nome vuoto
    			Arguments.of("", "2 giorni", 15),
    			//nome null
    			Arguments.of(null, "2 giorni", 15)
    			);
    }
    
    @Test
    @DisplayName("TCU3_2_4_1 doUpdateTestSalva")
    public void doUpdateTestSalva() throws Exception{
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(SpedizioneModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doUpdateSpedizioneCorretto.xml"))
                .getTable(table);
    	
    	Spedizione upd = new Spedizione("lenta", "12 giorni", 5);
    	
    	try {
    		spedizioneModelDS.doUpdate(upd);
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
    @DisplayName("TCU3_2_4_2 doUpdateTestNonSalva")
    public void doUpdateTestNonSalva(String nome, String tempo, float prezzo) {
    	assertThrows(CheckException.class, () -> {
    		Spedizione sav = new Spedizione(nome, tempo, prezzo);
    		spedizioneModelDS.doUpdate(sav);
    	});
    }
    
    private static Stream<Arguments> doUpdateTestProvider(){
    	return Stream.of(
    			//nome non presente nel Database
    			Arguments.of("molto veloce", "10 giorni", 5),
    			//tempo vuoto
    			Arguments.of("lenta", "", 15),
    			//tempo null
    			Arguments.of("lenta", null, 15),
    			//prezzo non positivo
    			Arguments.of("lenta", "2 giorni", -15),
    			//nome vuoto
    			Arguments.of("", "2 giorni", 15),
    			//nome null
    			Arguments.of(null, "2 giorni", 15)
    			);
    }
    
    @Test
    @DisplayName("TCU3_2_5_1 doDeleteTestPresente")
    public void doDeleteTestPresente() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(SpedizioneModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doDeleteSpedizione.xml"))
                .getTable(table);
    	
    	Spedizione del = new Spedizione("lenta", "", 0);
    	
    	try {
    		spedizioneModelDS.doDelete(del);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
        ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    @DisplayName("TCU3_2_5_2 doDeleteTestNonPresente")
    public void doDeleteTestNonPresente() {	
    	assertThrows(DBException.class, () -> {
    		Spedizione del = new Spedizione("lenta", "", 0);
    		spedizioneModelDS.doDelete(del);
    	});

    }
	
}
