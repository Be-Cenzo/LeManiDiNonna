package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

public class CorriereModelDS implements Model<Corriere>{

	private DataSource ds = null;

	public CorriereModelDS(DataSource ds) {
		this.ds = ds;
	}
	
	@Override
	public Corriere doRetrieveByKey(String nome) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Corriere corriere = new Corriere();

		String selectSQL = "SELECT * FROM corriere WHERE nome = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, nome);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				corriere.setNome(rs.getString("nome"));
				corriere.setTempo(rs.getString("tempo"));
				corriere.setPrezzo(rs.getFloat("costoSped"));
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
		return corriere;
	}

	@Override
	public ArrayList<Corriere> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<Corriere> corrieri = new ArrayList<Corriere>();

		String selectSQL = "SELECT * FROM corriere";

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Corriere corriere = new Corriere();

				corriere.setNome(rs.getString("nome"));
				corriere.setTempo(rs.getString("tempo"));
				corriere.setPrezzo(rs.getFloat("costoSped"));

				corrieri.add(corriere);
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
		return corrieri;
	}

	@Override
	public void doSave(Corriere corriere) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO corriere VALUES (?, ?, ?)";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);

			preparedStatement.setString(1, corriere.getNome());
			preparedStatement.setString(2, corriere.getTempo());
			preparedStatement.setFloat(3, corriere.getPrezzo());
			
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

	@Override
	public void doUpdate(Corriere corriere) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE corriere SET " + " tempo = ?, costoSped = ? WHERE nome = ?";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);

			preparedStatement.setString(1, corriere.getTempo());
			preparedStatement.setFloat(2, corriere.getPrezzo());
			preparedStatement.setString(3, corriere.getNome());

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

	@Override
	public void doDelete(Corriere corriere) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String deleteSQL = "DELETE FROM corriere WHERE nome = ?";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, corriere.getNome());

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
