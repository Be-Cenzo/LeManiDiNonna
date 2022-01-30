package acquistoManagement;

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

import catalogoManagement.Prodotto;
import checking.CheckException;
import checking.DBException;

public class RelatoModelDSTest {

	private static String expectedPath = "resources/db/expected/acquistoManagement/";
	private static String initPath = "resources/db/init/acquistoManagement/";
	private static String table = "Relato";
    private static IDatabaseTester tester;
	private DataSource ds;
    private RelatoModelDS relatoModelDS;
    
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
                .build(RelatoModelDSTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Prepara lo stato iniziale di default
        refreshDataSet(initPath + "RelatoInit.xml");
        ds = Mockito.mock(DataSource.class);
        Mockito.when(ds.getConnection())
        .thenReturn(tester.getConnection().getConnection());
        relatoModelDS = new RelatoModelDS(ds);
    }

    @AfterEach
    public void tearDown() throws Exception {
        tester.onTearDown();
    }
    
    @Test
    @DisplayName("TCU3_3_1_1 doRetrieveByKeyTestPresente")
    public void doRetrieveByKeyTestPresente() {
    	int expected = 1;
    	
    	Prodotto prod = new Prodotto();
    	prod.setCodice(1);
    	prod.setTaglia("S");
    	
    	Ordine ord = new Ordine();
    	ord.setID(1);
    	
    	int actual = -1;
    	try {
			actual = relatoModelDS.doRetrieveByKey(prod, ord);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU3_3_1_2 doRetrieveByKeyTestNonPresente")
    public void doRetrieveByKeyTestNonPresente() {
    	int expected = -1;
    	
    	Prodotto prod = new Prodotto();
    	prod.setCodice(10);
    	prod.setTaglia("S");
    	
    	Ordine ord = new Ordine();
    	ord.setID(1);
    	
    	int actual = 0;
    	try {
			actual = relatoModelDS.doRetrieveByKey(prod, ord);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU3_3_2_1 doRetrieveAllTestAsc")
    public void doRetrieveAllTestAsc() {
    	Prodotto prod;
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	prod = new Prodotto();
    	prod.setCodice(1);
    	prod.setTaglia("S");
    	prod.setQuantita(1);
    	expected.add(prod);

    	prod = new Prodotto();
    	prod.setCodice(3);
    	prod.setTaglia("S");
    	prod.setQuantita(5);
    	expected.add(prod);
    	
    	Ordine ord = new Ordine();
    	ord.setID(1);
    	
    	ArrayList<Prodotto> actual = null;
    	try {
			actual = relatoModelDS.doRetrieveAll("ASC", ord);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU3_3_2_2 doRetrieveAllTestDesc")
    public void doRetrieveAllTestDesc() {
    	Prodotto prod;
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	prod = new Prodotto();
    	prod.setCodice(3);
    	prod.setTaglia("S");
    	prod.setQuantita(5);
    	expected.add(prod);
    	
    	prod = new Prodotto();
    	prod.setCodice(1);
    	prod.setTaglia("S");
    	prod.setQuantita(1);
    	expected.add(prod);

    	
    	Ordine ord = new Ordine();
    	ord.setID(1);
    	
    	ArrayList<Prodotto> actual = null;
    	try {
			actual = relatoModelDS.doRetrieveAll("DESC", ord);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU3_3_2_3 doRetrieveAllTestVuota")
    public void doRetrieveAllTestVuota() {
    	Prodotto prod;
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	prod = new Prodotto();
    	prod.setCodice(1);
    	prod.setTaglia("S");
    	prod.setQuantita(1);
    	expected.add(prod);

    	prod = new Prodotto();
    	prod.setCodice(3);
    	prod.setTaglia("S");
    	prod.setQuantita(5);
    	expected.add(prod);
    	
    	Ordine ord = new Ordine();
    	ord.setID(1);
    	
    	ArrayList<Prodotto> actual = null;
    	try {
			actual = relatoModelDS.doRetrieveAll("", ord);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU3_3_2_4 doRetrieveAllTestNull")
    public void doRetrieveAllTestNull() {
    	Prodotto prod;
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	prod = new Prodotto();
    	prod.setCodice(1);
    	prod.setTaglia("S");
    	prod.setQuantita(1);
    	expected.add(prod);

    	prod = new Prodotto();
    	prod.setCodice(3);
    	prod.setTaglia("S");
    	prod.setQuantita(5);
    	expected.add(prod);
    	
    	Ordine ord = new Ordine();
    	ord.setID(1);
    	
    	ArrayList<Prodotto> actual = null;
    	try {
			actual = relatoModelDS.doRetrieveAll(null, ord);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU3_3_2_5 doRetrieveAllTestAltro")
    public void doRetrieveAllTestAltro() {
    	assertThrows(CheckException.class, () -> {
    		Ordine ord = new Ordine();
        	ord.setID(1);
			relatoModelDS.doRetrieveAll("ascendente", ord);
    	});
    }
    
    @Test
    @DisplayName("TCU3_3_2_6 doRetrieveAllTestNonPresente")
    public void doRetrieveAllTestNonPresente() {
    	ArrayList<Prodotto> expected = new ArrayList<Prodotto>();
    	
    	Ordine ord = new Ordine();
    	ord.setID(10);
    	
    	ArrayList<Prodotto> actual = null;
    	try {
			actual = relatoModelDS.doRetrieveAll("ASC", ord);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("TCU3_3_3_1 doSaveTestSalva")
    public void doSaveTestSalva() throws Exception{
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(SpedizioneModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doSaveRelatoCorretto.xml"))
                .getTable(table);
    	
    	Prodotto prod = new Prodotto();
    	prod.setCodice(3);
    	prod.setTaglia("M");
    	
    	int quant = 2;
    	
    	Ordine ord = new Ordine();
    	ord.setID(4);
    	
    	try {
			relatoModelDS.doSave(prod, ord, quant);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}

        ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    @DisplayName("TCU3_3_3_2 doSaveTestNonSalva")
    public void doSaveTestNonSalva() throws Exception{
    	Prodotto prod = new Prodotto();
    	prod.setCodice(1);
    	prod.setTaglia("S");
    	
    	int quant = 2;
    	
    	Ordine ord = new Ordine();
    	ord.setID(1);
    	
    	assertThrows(CheckException.class, () -> {
    		relatoModelDS.doSave(prod, ord, quant);
    	});
    }
    
    @Test
    @DisplayName("TCU3_3_3_3 doSaveTestQuantitaNegativa")
    public void doSaveTestQuantitaNegativa() throws Exception{
    	Prodotto prod = new Prodotto();
    	prod.setCodice(3);
    	prod.setTaglia("M");
    	
    	int quant = -2;
    	
    	Ordine ord = new Ordine();
    	ord.setID(4);
    	
    	assertThrows(CheckException.class, () -> {
    		relatoModelDS.doSave(prod, ord, quant);
    	});
    }
    
    @Test
    @DisplayName("TCU3_3_4_1 doUpdateTestSalva")
    public void doUpdateTestSalva() throws Exception{
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(SpedizioneModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doUpdateRelatoCorretto.xml"))
                .getTable(table);
    	
    	Prodotto prod = new Prodotto();
    	prod.setCodice(2);
    	prod.setTaglia("M");
    	
    	int quant = 5;
    	
    	Ordine ord = new Ordine();
    	ord.setID(3);
    	
    	try {
			relatoModelDS.doUpdate(prod, ord, quant);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}

        ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    @DisplayName("TCU3_3_4_2 doUpdateTestNonSalva")
    public void doUpdateTestNonSalva() throws Exception{
    	Prodotto prod = new Prodotto();
    	prod.setCodice(1);
    	prod.setTaglia("S");
    	
    	int quant = 2;
    	
    	Ordine ord = new Ordine();
    	ord.setID(5);
    	
    	assertThrows(DBException.class, () -> {
    		relatoModelDS.doUpdate(prod, ord, quant);
    	});
    }
    
    @Test
    @DisplayName("TCU3_3_4_3 doUpdateTestQuantitaNegativa")
    public void doUpdateTestQuantitaNegativa() throws Exception{
    	Prodotto prod = new Prodotto();
    	prod.setCodice(1);
    	prod.setTaglia("S");
    	
    	int quant = -2;
    	
    	Ordine ord = new Ordine();
    	ord.setID(1);
    	
    	assertThrows(CheckException.class, () -> {
    		relatoModelDS.doUpdate(prod, ord, quant);
    	});
    }
    
    @Test
    @DisplayName("TCU3_3_5_1 doDeleteTestPresente")
    public void doDeleteTestPresente() throws Exception {
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(SpedizioneModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doDeleteRelato.xml"))
                .getTable(table);
    	
    	Prodotto prod = new Prodotto();
    	prod.setCodice(2);
    	prod.setTaglia("M");
    	
    	Ordine ord = new Ordine();
    	ord.setID(3);
    	
    	try {
			relatoModelDS.doDelete(prod, ord);
		} catch (SQLException e) {
			e.printStackTrace();
		}

        ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
	
    @Test
    @DisplayName("TCU3_3_5_2 doDeleteTestNonPresente")
    public void doDeleteTestNonPresente() throws Exception{
    	Prodotto prod = new Prodotto();
    	prod.setCodice(10);
    	prod.setTaglia("S");
    	
    	Ordine ord = new Ordine();
    	ord.setID(1);
    	
    	assertThrows(DBException.class, () -> {
    		relatoModelDS.doDelete(prod, ord);
    	});
    }
    
}
