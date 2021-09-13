package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

public class TaglieModelDS{
	
	private DataSource ds = null;

	public TaglieModelDS(DataSource ds) {
		this.ds = ds;
	}

	/*@Override
	public Prodotto doRetrieveByKey(String codice) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Prodotto prodotto = null;

		String selectSQL = "SELECT * FROM prodotto WHERE codice = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, Integer.parseInt(codice));

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				if(prodotto == null)
					prodotto = new Prodotto();
				prodotto.setCodice(rs.getInt("codice"));
				prodotto.setTipo(rs.getString("tipo"));
				prodotto.setPrezzo(rs.getFloat("prezzo"));
				prodotto.setColore(rs.getString("colore"));
				prodotto.setDescrizione(rs.getString("descrizione"));
				prodotto.setMarca(rs.getString("marca"));
				prodotto.setModello(rs.getString("modello"));
				String tipo = prodotto.getTipo();
				if(!tipo.equals("t-shirt") && !tipo.equals("felpa")) {
					prodotto.setTaglia("N");
				}
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
				String tipo = prodotto.getTipo();
				if(!tipo.equals("t-shirt") && !tipo.equals("felpa")) {
					prodotto.setTaglia("N");
				}

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
	*/
	

	
	public void doSave(int codice, String taglia) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO taglie (prodotto, taglia) VALUES (?, ?)";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);

			preparedStatement.setInt(1, codice);
			preparedStatement.setString(2, taglia);
			
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
