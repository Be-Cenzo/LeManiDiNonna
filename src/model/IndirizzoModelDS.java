package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

public class IndirizzoModelDS implements Model<Indirizzo>{

	private DataSource ds = null;
	private String email = null;

	public IndirizzoModelDS(DataSource ds, String email) {
		this.ds = ds;
		this.email = email;
	}
	
	@Override
	public Indirizzo doRetrieveByKey(String id) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Indirizzo indirizzo = null;

		String selectSQL = "SELECT * FROM indirizzo WHERE ID = ? AND email = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, Integer.parseInt(id));
			preparedStatement.setString(2, email);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int ID = (rs.getInt("ID"));
				String email = (rs.getString("email"));
				String provincia = (rs.getString("provincia"));
				String comune = (rs.getString("comune"));
				String via = (rs.getString("via"));
				int civico = (rs.getInt("civico"));
				String cap = (rs.getString("cap"));
				indirizzo = new Indirizzo(ID, email, provincia, comune, via, civico, cap);
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

		return indirizzo;
	}

	@Override
	public ArrayList<Indirizzo> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<Indirizzo> indirizzi = new ArrayList<Indirizzo>();

		String selectSQL = "SELECT * FROM indirizzo WHERE email = ?";

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, email);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int ID = (rs.getInt("ID"));
				String email = (rs.getString("email"));
				String provincia = (rs.getString("provincia"));
				String comune = (rs.getString("comune"));
				String via = (rs.getString("via"));
				int civico = (rs.getInt("civico"));
				String cap = (rs.getString("cap"));
				Indirizzo indirizzo = new Indirizzo(ID, email, provincia, comune, via, civico, cap);

				indirizzi.add(indirizzo);
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

		return indirizzi;

	}

	@Override
	public void doSave(Indirizzo indirizzo) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO indirizzo (ID, email, provincia, comune, via, civico, cap) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);

			preparedStatement.setInt(1, indirizzo.getID());
			preparedStatement.setString(2, email);
			preparedStatement.setString(3, indirizzo.getProvincia());
			preparedStatement.setString(4, indirizzo.getComune());
			preparedStatement.setString(5, indirizzo.getVia());
			preparedStatement.setInt(6, indirizzo.getCivico());
			preparedStatement.setString(7, indirizzo.getCAP());
			
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
	public void doUpdate(Indirizzo indirizzo) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE indirizzo SET provincia = ?, comune = ?, civico = ?, cap = ? WHERE ID = ? AND email = ?";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);

			preparedStatement.setString(1, indirizzo.getProvincia());
			preparedStatement.setString(2, indirizzo.getComune());
			preparedStatement.setFloat(3, indirizzo.getCivico());
			preparedStatement.setString(4, indirizzo.getCAP());
			
			preparedStatement.setInt(5, indirizzo.getID());
			preparedStatement.setString(6, email);

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
	public void doDelete(Indirizzo indirizzo) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String deleteSQL = "DELETE FROM indirizzo WHERE ID = ? AND email = ?";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, indirizzo.getID());
			preparedStatement.setString(2, email);

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
