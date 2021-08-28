package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;


public class ProdottoModelDS implements Model<Prodotto> {
	
	private DataSource ds = null;

	public ProdottoModelDS(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public Prodotto doRetrieveByKey(String codice) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Prodotto prodotto = new Prodotto();

		String selectSQL = "SELECT * FROM prodotto WHERE codice = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, Integer.parseInt(codice));

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				prodotto.setCodice(rs.getInt("codice"));
				prodotto.setTipo(rs.getString("tipo"));
				prodotto.setPrezzo(rs.getFloat("prezzo"));
				prodotto.setColore(rs.getString("colore"));
				prodotto.setDescrizione(rs.getString("descrizione"));
				prodotto.setMarca(rs.getString("marca"));
				prodotto.setModello(rs.getString("modello"));
				prodotto.setTaglia(rs.getString("taglia"));
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
		return prodotto;
	}

	@Override
	public ArrayList<Prodotto> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();

		String selectSQL = "SELECT * FROM prodotto";

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Prodotto prodotto = new Prodotto();

				prodotto.setCodice(rs.getInt("codice"));
				prodotto.setTipo(rs.getString("tipo"));
				prodotto.setPrezzo(rs.getFloat("prezzo"));
				prodotto.setColore(rs.getString("colore"));
				prodotto.setDescrizione(rs.getString("descrizione"));
				prodotto.setMarca(rs.getString("marca"));
				prodotto.setModello(rs.getString("modello"));
				prodotto.setTaglia(rs.getString("taglia"));

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

	@Override
	public void doSave(Prodotto prodotto) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO prodotto (tipo, prezzo, colore, descrizione, marca, modello, taglia) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);

			preparedStatement.setString(1, prodotto.getTipo());
			preparedStatement.setFloat(2, prodotto.getPrezzo());
			preparedStatement.setString(3, prodotto.getColore());
			preparedStatement.setString(4, prodotto.getDescrizione());
			preparedStatement.setString(5, prodotto.getMarca());
			preparedStatement.setString(6, prodotto.getModello());
			preparedStatement.setString(7, prodotto.getTaglia());
			
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
	public void doUpdate(Prodotto prodotto) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE prodotto SET " + " tipo = ?, descrizione = ?, prezzo = ?, colore = ?, marca = ?, modello = ?, taglia = ? WHERE codice = ?";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);

			preparedStatement.setString(1, prodotto.getTipo());
			preparedStatement.setString(2, prodotto.getDescrizione());
			preparedStatement.setFloat(3, prodotto.getPrezzo());
			preparedStatement.setString(4, prodotto.getColore());
			preparedStatement.setString(5, prodotto.getMarca());
			preparedStatement.setString(6, prodotto.getModello());
			preparedStatement.setString(7, prodotto.getTaglia());
			
			preparedStatement.setInt(8, prodotto.getCodice());

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
	public void doDelete(Prodotto prodotto) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String deleteSQL = "DELETE FROM prodotto WHERE codice = ?";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, prodotto.getCodice());

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
