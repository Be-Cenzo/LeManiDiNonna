package acquistoManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.DataSource;

public class OrdineModelDS {

	private DataSource ds = null;
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;

	public OrdineModelDS(DataSource ds) {
		this.ds = ds;
	}
	
	
	public Ordine doRetrieveByKey(String ID) throws SQLException {
		connection = null;
		preparedStatement = null;

		Ordine ordine = new Ordine();

		String selectSQL = "SELECT * FROM ordine WHERE ID = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, Integer.parseInt(ID));

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ordine.setID(rs.getInt("ID"));
				ordine.setData(rs.getDate("data"));
				ordine.setPrezzo(rs.getFloat("prezzo"));
				ordine.setCostoSped(rs.getFloat("costoSped"));
				ordine.setNote(rs.getString("note"));
				ordine.setStato(rs.getString("stato"));
				ordine.setEmail(rs.getString("email"));
				ordine.setIndirizzo(rs.getInt("indirizzo"));
				ordine.setCorriere(rs.getString("corriere"));
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
		return ordine;
	}

	
	public ArrayList<Ordine> doRetrieveAll(String order) throws SQLException {
		connection = null;
		preparedStatement = null;

		ArrayList<Ordine> ordini = new ArrayList<Ordine>();

		String selectSQL = "SELECT * FROM ordine";

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Ordine ordine = new Ordine();

				ordine.setID(rs.getInt("ID"));
				ordine.setData(rs.getDate("data"));
				ordine.setPrezzo(rs.getFloat("prezzo"));
				ordine.setCostoSped(rs.getFloat("costoSped"));
				ordine.setNote(rs.getString("note"));
				ordine.setStato(rs.getString("stato"));
				ordine.setEmail(rs.getString("email"));
				ordine.setIndirizzo(rs.getInt("indirizzo"));
				ordine.setCorriere(rs.getString("corriere"));

				ordini.add(ordine);
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
		return ordini;
	}
	
	public ArrayList<Ordine> doRetrieveAll(String order, String email) throws SQLException {
		connection = null;
		preparedStatement = null;

		ArrayList<Ordine> ordini = new ArrayList<Ordine>();

		String selectSQL = "SELECT * FROM ordine WHERE email = ?";

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, email);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Ordine ordine = new Ordine();

				ordine.setID(rs.getInt("ID"));
				ordine.setData(rs.getDate("data"));
				ordine.setPrezzo(rs.getFloat("prezzo"));
				ordine.setCostoSped(rs.getFloat("costoSped"));
				ordine.setNote(rs.getString("note"));
				ordine.setStato(rs.getString("stato"));
				ordine.setEmail(rs.getString("email"));
				ordine.setIndirizzo(rs.getInt("indirizzo"));
				ordine.setCorriere(rs.getString("corriere"));

				ordini.add(ordine);
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
		return ordini;
	}

	
	public void doSave(Ordine ordine) throws SQLException {
		connection = null;
		preparedStatement = null;

		String insertSQL = "INSERT INTO Ordine (ID, data, prezzo, costoSped, note, stato, email, indirizzo, corriere) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);

			preparedStatement.setInt(1, ordine.getID());
			preparedStatement.setDate(2, ordine.getData());
			preparedStatement.setFloat(3, ordine.getPrezzo());
			preparedStatement.setFloat(4, ordine.getCostoSped());
			preparedStatement.setString(5, ordine.getNote());
			preparedStatement.setString(6, ordine.getStato());
			preparedStatement.setString(7, ordine.getEmail());
			preparedStatement.setInt(8, ordine.getIndirizzo());
			preparedStatement.setString(9, ordine.getCorriere());
			
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

	
	public void doUpdate(Ordine ordine) throws SQLException {
		connection = null;
		preparedStatement = null;

		String updateSQL = "UPDATE ordine SET data = ?, prezzo = ?, costoSped = ?, note = ?, stato = ?, email = ?, indirizzo = ?, corriere = ? WHERE ID = ?";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);

			preparedStatement.setDate(1, ordine.getData());
			preparedStatement.setFloat(2, ordine.getPrezzo());
			preparedStatement.setFloat(3, ordine.getCostoSped());
			preparedStatement.setString(4, ordine.getNote());
			preparedStatement.setString(5, ordine.getStato());
			preparedStatement.setString(6, ordine.getEmail());
			preparedStatement.setInt(7, ordine.getIndirizzo());
			preparedStatement.setString(8, ordine.getCorriere());
			preparedStatement.setInt(9, ordine.getID());

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

	
	public void doDelete(Ordine ordine) throws SQLException {
		connection = null;
		preparedStatement = null;

		String deleteSQL = "DELETE FROM ordine WHERE ID = ?";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, ordine.getID());

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
	
	//crea ordine si trova in una classe apposita
	/*public void creaOrdine(Ordine ordine) throws SQLException {
		doSave(ordine);
		connection = null;
		preparedStatement = null;

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			HashMap<Prodotto, Integer> quantità = ordine.getQuantità();
			quantità.forEach((prod, quant) -> {
				String insertSQL = "INSERT INTO relato (ordine, prodotto, quantità) VALUES (?, ?, ?)";
				try {
					preparedStatement = connection.prepareStatement(insertSQL);

					preparedStatement.setInt(1, ordine.getID());
					preparedStatement.setInt(2, prod.getCodice());
					preparedStatement.setFloat(3, quant);
					preparedStatement.executeUpdate();

					connection.commit();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});

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
	}*/

}
