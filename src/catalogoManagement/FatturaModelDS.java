package catalogoManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

public class FatturaModelDS {

	private DataSource ds = null;

	public FatturaModelDS(DataSource ds) {
		this.ds = ds;
	}

	
	public Fattura doRetrieveByKey(String codice) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Fattura fattura = new Fattura();

		String selectSQL = "SELECT * FROM fattura WHERE ID = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, Integer.parseInt(codice));

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				fattura.setID(rs.getInt("ID"));
				fattura.setData(rs.getDate("data"));
				fattura.setAmmontare(rs.getFloat("ammontare"));
				fattura.setModalità(rs.getString("modalità"));
				fattura.setOrdine(rs.getInt("ordine"));
			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}
		return fattura;
	}

	
	public ArrayList<Fattura> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<Fattura> fatture = new ArrayList<Fattura>();

		String selectSQL = "SELECT * FROM fattura";

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Fattura fattura = new Fattura();

				fattura.setID(rs.getInt("ID"));
				fattura.setData(rs.getDate("data"));
				fattura.setAmmontare(rs.getFloat("ammontare"));
				fattura.setModalità(rs.getString("modalità"));
				fattura.setOrdine(rs.getInt("ordine"));

				fatture.add(fattura);
			}
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}
		return fatture;
	}

	
	public void doSave(Fattura fattura) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO fattura (data, ammontare, modalità, ordine) VALUES (?, ?, ?, ?)";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);

			preparedStatement.setDate(1, fattura.getData());
			preparedStatement.setFloat(2, fattura.getAmmontare());
			preparedStatement.setString(3, fattura.getModalità());
			preparedStatement.setInt(4, fattura.getOrdine());
			preparedStatement.executeUpdate();

			connection.commit();

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}
	}

	
	public void doUpdate(Fattura fattura) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE fattura SET data = ?, ammontare = ?, modalità = ?, ordine = ? WHERE ID = ?";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);

			preparedStatement.setDate(1, fattura.getData());
			preparedStatement.setFloat(2, fattura.getAmmontare());
			preparedStatement.setString(3, fattura.getModalità());
			preparedStatement.setInt(4, fattura.getOrdine());
			
			preparedStatement.setInt(5, fattura.getID());

			preparedStatement.executeUpdate();

			connection.commit();

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}
	}

	
	public void doDelete(Fattura fattura) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String deleteSQL = "DELETE FROM fattura WHERE ID = ?";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, fattura.getID());

			preparedStatement.executeUpdate();

			connection.commit();

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}
	}
	
	

}
