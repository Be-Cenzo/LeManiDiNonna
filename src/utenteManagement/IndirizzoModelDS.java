package utenteManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.DataSource;
import checking.*;

public class IndirizzoModelDS {

	private DataSource ds = null;
	private String email = null;

	public IndirizzoModelDS(DataSource ds, String email) {
		this.ds = ds;
		this.email = email;
	}
	
	
	public Indirizzo doRetrieveByKey(int id) throws CheckException, SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Indirizzo indirizzo = null;

		String selectSQL = "SELECT * FROM indirizzo WHERE ID = ? AND email = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id);
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

	
	public ArrayList<Indirizzo> doRetrieveAll(String order) throws CheckException, SQLException {
		//pre-condition
		if(order != null && order != "" && order != "ASC" && order != "DESC")
			throw new CheckException("Invalid order");
		//fine
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<Indirizzo> indirizzi = new ArrayList<Indirizzo>();

		String selectSQL = "SELECT * FROM indirizzo WHERE email = ?";

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY provincia " + order;
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

	
	public void doSave(Indirizzo indirizzo) throws CheckException, SQLException {
		//pre-condition
		if(indirizzo == null)
			throw new CheckException();
		Validazione.checkStringaVuota(indirizzo.getProvincia());
		Validazione.checkStringaVuota(indirizzo.getComune());
		Validazione.checkStringaVuota(indirizzo.getVia());
		Validazione.checkStringaVuota(indirizzo.getCAP());
		//fine
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

	
	public void doUpdate(Indirizzo indirizzo) throws CheckException, SQLException, DBException {
		//pre-condition
		if(indirizzo == null)
			throw new CheckException();
		Validazione.checkStringaVuota(indirizzo.getProvincia());
		Validazione.checkStringaVuota(indirizzo.getComune());
		Validazione.checkStringaVuota(indirizzo.getVia());
		Validazione.checkStringaVuota(indirizzo.getCAP());
		//fine
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE indirizzo SET provincia = ?, comune = ?, via = ?, civico = ?, cap = ? WHERE ID = ? AND email = ?";
		int err;
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);

			preparedStatement.setString(1, indirizzo.getProvincia());
			preparedStatement.setString(2, indirizzo.getComune());
			preparedStatement.setString(3, indirizzo.getVia());
			preparedStatement.setFloat(4, indirizzo.getCivico());
			preparedStatement.setString(5, indirizzo.getCAP());
			
			preparedStatement.setInt(6, indirizzo.getID());
			preparedStatement.setString(7, email);

			err = preparedStatement.executeUpdate();

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
		if(err == 0)
			throw new DBException("Update failed");
		
	}

	
	public void doDelete(Indirizzo indirizzo) throws CheckException, SQLException, DBException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		if(indirizzo == null)
			throw new CheckException();

		String deleteSQL = "DELETE FROM indirizzo WHERE ID = ? AND email = ?";
		int err;
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, indirizzo.getID());
			preparedStatement.setString(2, email);

			err = preparedStatement.executeUpdate();

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
		if(err == 0)
			throw new DBException("Update failed");
		
	}
	
	

}
