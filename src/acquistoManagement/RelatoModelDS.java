package acquistoManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.DataSource;

import catalogoManagement.Prodotto;
import catalogoManagement.ProdottoModelDS;
import checking.CheckException;
import checking.DBException;
import checking.Validazione;

public class RelatoModelDS {
	
	private DataSource ds = null;

	public RelatoModelDS(DataSource ds) {
		this.ds = ds;
	}
	
	public int doRetrieveByKey(Prodotto prodotto, Ordine ordine) throws CheckException, SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String selectSQL = "SELECT * FROM relato WHERE prodotto = ? AND ordine = ? AND taglia = ?";
		int quantita = -1;

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, prodotto.getCodice());
			preparedStatement.setInt(2, ordine.getID());
			preparedStatement.setString(3, prodotto.getTaglia());

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				quantita = rs.getInt("quantita");
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
		return quantita;
	}
	
	/**
	 * recupera tutti i prodotti relativi ad un determinato ordine
	 * @param order l'ordinamento della lista
	 * @param ordine l'ordine di riferimento
	 * @return un'arraylist di prodotti appartenenti all'ordine
	 * @throws SQLException
	 */
	public ArrayList<Prodotto> doRetrieveAll(String order, Ordine ordine) throws CheckException, SQLException {
		//pre-condition
		if(order != null && order != "" && order != "ASC" && order != "DESC")
			throw new CheckException("Invalid order");
		//fine
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();

		String selectSQL = "SELECT * FROM relato WHERE ordine = ?";

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY prodotto " + order;
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
				int quantita = rs.getInt("quantita");
				prodotto = mod.doRetrieveByKey(idProdotto);
				prodotto.setTaglia(rs.getString("taglia"));
				prodotto.setQuantita(quantita);
				
				prodotti.add(prodotto);
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
	
	public void doSave(Prodotto prodotto, Ordine ordine, int quantita) throws CheckException, SQLException {
		//pre-condition
		if(quantita <= 0)
			throw new CheckException("invalid quantita");
		//fine
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO relato (prodotto, ordine, quantita, taglia) VALUES (?, ?, ?, ?)";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);

			preparedStatement.setInt(1, prodotto.getCodice());
			preparedStatement.setInt(2, ordine.getID());
			preparedStatement.setInt(3, quantita);
			preparedStatement.setString(4, prodotto.getTaglia());
			
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
	
	public void doUpdate(Prodotto prodotto, Ordine ordine, int quantita) throws CheckException, SQLException, DBException {
		//pre-condition
		if(quantita <= 0)
			throw new CheckException("invalid quantita");
		//fine
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE relato SET quantita = ? WHERE prodotto = ? AND ordine = ? AND taglia = ?";
		int err;
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);

			preparedStatement.setInt(1, quantita);
			preparedStatement.setInt(2, prodotto.getCodice());
			preparedStatement.setInt(3, ordine.getID());
			preparedStatement.setString(4, prodotto.getTaglia());

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

	public void doDelete(Prodotto prodotto, Ordine ordine) throws SQLException, DBException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String deleteSQL = "DELETE FROM relato WHERE prodotto = ? AND ordine = ? AND taglia = ?";
		int err;
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, prodotto.getCodice());
			preparedStatement.setInt(2, ordine.getID());
			preparedStatement.setString(3, prodotto.getTaglia());

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
