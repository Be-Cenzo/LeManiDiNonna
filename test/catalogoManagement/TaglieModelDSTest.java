package catalogoManagement;

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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import checking.CheckException;


public class TaglieModelDSTest {

	private static String expectedPath = "resources/db/expected/catalogoManagement/";
	private static String initPath = "resources/db/init/catalogoManagement/";
	private static String table = "Taglie";
    private static IDatabaseTester tester;
	private DataSource ds;
    private TaglieModelDS taglieModelDS;
    
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
                .build(TaglieModelDSTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Prepara lo stato iniziale di default
        refreshDataSet(initPath + "TaglieInit.xml");
        ds = Mockito.mock(DataSource.class);
        Mockito.when(ds.getConnection())
        .thenReturn(tester.getConnection().getConnection());
        taglieModelDS = new TaglieModelDS(ds);
    }

    @AfterEach
    public void tearDown() throws Exception {
        tester.onTearDown();
    }
    
    @Test
    @DisplayName("TCU2_3_1_1 doSaveTestSalva")
    public void doSaveTestSalva() throws Exception{
    	ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(TaglieModelDSTest.class.getClassLoader().getResourceAsStream(expectedPath + "doSaveTaglieCorretto.xml"))
                .getTable(table);
    	
    	try {
    		taglieModelDS.doSave(3, "S");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CheckException e) {
			e.printStackTrace();
		}
    	ITable actualTable = tester.getConnection().createDataSet().getTable(table);
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
    }
    
    @Test
    @DisplayName("TCU2_3_1_2 doSaveTestNonSalva")
    public void doSaveTestNonSalva() {
    	assertThrows(CheckException.class, () ->{
    		taglieModelDS.doSave(1, "M");
    	});
    }
    
    @Test
    @DisplayName("TCU2_3_1_2 doSaveTestAltro")
    public void doSaveTestAltro() {
    	assertThrows(CheckException.class, () ->{
    		taglieModelDS.doSave(1, "Y");
    	});
    }
	
}
