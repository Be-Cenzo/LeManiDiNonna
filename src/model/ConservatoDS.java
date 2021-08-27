package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class ConservatoDS {

	private DataSource ds = null;

	public ConservatoDS(DataSource ds) {
		this.ds = ds;
	}
	
	public int doRetrieveByKey(Prodotto prodotto, Deposito deposito) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int disponibilitÓ = -1;

		String selectSQL = "SELECT * FROM conservato WHERE prodotto = ? AND deposito = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, prodotto.getCodice());
			preparedStatement.setInt(2, deposito.getID());

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				disponibilitÓ = rs.getInt("disponibilitÓ");
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
		return disponibilitÓ;
	}
	
	public void doSave(Prodotto prodotto, Deposito deposito, int disponibilitÓ) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO conservato" + " (prodotto, deposito, disponibilitÓ) VALUES (?, ?, ?)";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);

			preparedStatement.setInt(1, prodotto.getCodice());
			preparedStatement.setInt(2, deposito.getID());
			preparedStatement.setInt(3, disponibilitÓ);
			
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
	
	public void doUpdate(Prodotto prodotto, Deposito deposito, int disponibilitÓ) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE conservato SET disponibilitÓ = ? WHERE prodotto = ? AND deposito = ?";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);

			preparedStatement.setInt(1, disponibilitÓ);
			preparedStatement.setInt(2, prodotto.getCodice());
			preparedStatement.setInt(3, deposito.getID());

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
	
	public void doDelete(Prodotto prodotto, Deposito deposito) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String deleteSQL = "DELETE FROM conservato WHERE prodotto = ? AND deposito = ?";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, prodotto.getCodice());
			preparedStatement.setInt(2, deposito.getID());

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
