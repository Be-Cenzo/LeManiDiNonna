package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.DataSource;

public class RelatoDS {
	
	private DataSource ds = null;

	public RelatoDS(DataSource ds) {
		this.ds = ds;
	}
	
	public int doRetrieveByKey(Prodotto prodotto, Ordine ordine) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String selectSQL = "SELECT * FROM relato WHERE prodotto = ? AND ordine = ?";
		int quantità = -1;

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, prodotto.getCodice());
			preparedStatement.setInt(2, ordine.getID());

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				quantità = rs.getInt("quantità");
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
		return quantità;
	}
	
	/**
	 * recupera tutti i prodotti relativi ad un determinato ordine
	 * @param order l'ordinamento della lista
	 * @param ordine l'ordine di riferimento
	 * @return una hashmap di prodotti con relativa quantità appartenenti all'ordine
	 * @throws SQLException
	 */
	public HashMap<Prodotto, Integer> doRetrieveAll(String order, Ordine ordine) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		HashMap<Prodotto, Integer> prodotti = new HashMap<Prodotto, Integer>();

		String selectSQL = "SELECT * FROM relato WHERE ordine = ?";

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, ordine.getID());

			ResultSet rs = preparedStatement.executeQuery();
			ProdottoModelDS mod = new ProdottoModelDS(ds);
			
			while (rs.next()) {
				Prodotto prodotto = null;

				int idProdotto = rs.getInt("prodotto");
				int quantità = rs.getInt("quantità");
				prodotto = mod.doRetrieveByKey("" + idProdotto);
				
				prodotti.put(prodotto, quantità);
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
		return prodotti;
	}
	
	public void doSave(Prodotto prodotto, Ordine ordine, int quantità) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO relato (prodotto, ordine, quantità) VALUES (?, ?, ?)";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);

			preparedStatement.setInt(1, prodotto.getCodice());
			preparedStatement.setInt(2, ordine.getID());
			preparedStatement.setInt(3, quantità);
			
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
	
	public void doUpdate(Prodotto prodotto, Ordine ordine, int quantità) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE relato SET quantità = ? WHERE prodotto = ? AND ordine = ?";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);

			preparedStatement.setInt(1, quantità);
			preparedStatement.setInt(2, prodotto.getCodice());
			preparedStatement.setInt(3, ordine.getID());

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

	public void doDelete(Prodotto prodotto, Ordine ordine) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String deleteSQL = "DELETE FROM relato WHERE prodotto = ? AND ordine = ?";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, prodotto.getCodice());
			preparedStatement.setInt(2, ordine.getID());

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
