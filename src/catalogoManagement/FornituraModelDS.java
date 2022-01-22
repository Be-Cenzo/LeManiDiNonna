package catalogoManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

public class FornituraModelDS {
	
	private DataSource ds = null;

	public FornituraModelDS(DataSource ds) {
		this.ds = ds;
	}

	
	public Fornitura doRetrieveByKey(String ID) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Fornitura fornitura = new Fornitura();

		String selectSQL = "SELECT * FROM fornitura WHERE ID = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, Integer.parseInt(ID));

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				fornitura.setID(rs.getInt("ID"));
				fornitura.setFornitore(rs.getString("fornitore"));
				fornitura.setData(rs.getDate("data"));
				fornitura.setQuantità(rs.getInt("quantità"));
				fornitura.setProdotto(rs.getInt("prodotto"));
				fornitura.setPrezzo(rs.getFloat("prezzo"));
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
		return fornitura;
	}

	
	public ArrayList<Fornitura> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<Fornitura> forniture = new ArrayList<Fornitura>();

		String selectSQL = "SELECT * FROM fornitura";

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Fornitura fornitura = new Fornitura();

				fornitura.setID(rs.getInt("ID"));
				fornitura.setFornitore(rs.getString("fornitore"));
				fornitura.setData(rs.getDate("data"));
				fornitura.setQuantità(rs.getInt("quantità"));
				fornitura.setProdotto(rs.getInt("prodotto"));
				fornitura.setPrezzo(rs.getFloat("prezzo"));

				forniture.add(fornitura);
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
		return forniture;
	}

	
	public void doSave(Fornitura fornitura) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO fornitura (fornitore, data, quantità, prodotto, prezzo) VALUES (?, ?, ?, ?, ?)";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);

			preparedStatement.setString(1, fornitura.getFornitore());
			preparedStatement.setDate(2, fornitura.getData());
			preparedStatement.setInt(3, fornitura.getQuantità());
			preparedStatement.setInt(4, fornitura.getProdotto());
			preparedStatement.setFloat(5, fornitura.getPrezzo());
			
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

	
	public void doUpdate(Fornitura fornitura) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE fornitura SET fornitore = ?, data = ?, quantità = ?, prodotto = ?, prezzo = ? WHERE ID = ?";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);

			preparedStatement.setString(1, fornitura.getFornitore());
			preparedStatement.setDate(2, fornitura.getData());
			preparedStatement.setInt(3, fornitura.getQuantità());
			preparedStatement.setInt(4, fornitura.getProdotto());
			preparedStatement.setFloat(5, fornitura.getPrezzo());
			
			preparedStatement.setInt(6, fornitura.getID());

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

	
	public void doDelete(Fornitura fornitura) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String deleteSQL = "DELETE FROM fornitura WHERE ID = ?";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, fornitura.getID());

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
