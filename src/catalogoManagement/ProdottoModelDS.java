package catalogoManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.DataSource;
import checking.*;


public class ProdottoModelDS {
	
	private DataSource ds = null;

	public ProdottoModelDS(DataSource ds) {
		this.ds = ds;
	}

	
	public Prodotto doRetrieveByKey(int codice) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Prodotto prodotto = null;

		String selectSQL = "SELECT * FROM prodotto WHERE codice = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, codice);

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

	
	public ArrayList<Prodotto> doRetrieveAll(String order) throws CheckException, SQLException {
		//pre-condition
		if(order != null && order != "" && order != "ASC" && order != "DESC")
			throw new CheckException("Invalid order");
		//fine
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();

		String selectSQL = "SELECT * FROM prodotto";

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY descrizione " + order;
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
	
	public ArrayList<Prodotto> doRetrieveAll(String order, ArrayList<String> filter, String search) throws CheckException, SQLException {
		//pre-condition
		if(order != null && order != "" && order != "ASC" && order != "DESC")
			throw new CheckException("Invalid order");
		//fine
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();

		String selectSQL = "SELECT * FROM prodotto";
		if(filter != null) {
			if(!filter.isEmpty()) {
				selectSQL += " WHERE (tipo = '" + filter.get(0) + "'";
			}
			
			for(int i=1; i<filter.size(); i++) {
				selectSQL += " || tipo = '" + filter.get(i) + "'";
			}
			
			if(!filter.isEmpty()) {
				selectSQL += ")";
			}
		}
		
		if(search != null)
			if(!search.equals("")) {
				if(selectSQL.contains("WHERE"))
					selectSQL += " && descrizione LIKE ?";
				else
					selectSQL += " WHERE descrizione LIKE ?";
			}

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY descrizione " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			
			if(search != null) 
				if(!search.equals(""))
					preparedStatement.setString(1, "%" + search + "%");

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

	
	public void doSave(Prodotto prodotto) throws CheckException, SQLException {
		//pre-condition
		Validazione.checkStringaVuota(prodotto.getTipo());
		Validazione.checkTipo(prodotto.getTipo());
		if(prodotto.getPrezzo() <= 0)
			throw new CheckException("Invalid price");
		Validazione.checkStringaVuota(prodotto.getColore());
		Validazione.checkStringaVuota(prodotto.getDescrizione());
		if(prodotto.getTipo().equals("felpa") || prodotto.getTipo().equals("t-shirt")) {
			Validazione.checkStringaVuota(prodotto.getMarca());
			Validazione.checkStringaVuota(prodotto.getModello());
		}
		
		//fine
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO prodotto (tipo, prezzo, colore, descrizione, marca, modello) VALUES (?, ?, ?, ?, ?, ?)";

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

	
	public void doUpdate(Prodotto prodotto) throws CheckException, SQLException, DBException {
		//pre-condition
		Validazione.checkStringaVuota(prodotto.getTipo());
		Validazione.checkTipo(prodotto.getTipo());
		if(prodotto.getPrezzo() <= 0)
			throw new CheckException("Invalid price");
		Validazione.checkStringaVuota(prodotto.getColore());
		Validazione.checkStringaVuota(prodotto.getDescrizione());
		if(prodotto.getTipo().equals("felpa") || prodotto.getTipo().equals("t-shirt")) {
			Validazione.checkStringaVuota(prodotto.getMarca());
			Validazione.checkStringaVuota(prodotto.getModello());
		}	
		//fine
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE prodotto SET " + " tipo = ?, descrizione = ?, prezzo = ?, colore = ?, marca = ?, modello = ? WHERE codice = ?";
		int err;
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
			
			preparedStatement.setInt(7, prodotto.getCodice());

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
			throw new DBException("Update failed");;
	}

	
	public void doDelete(Prodotto prodotto) throws SQLException, DBException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String deleteSQL = "DELETE FROM prodotto WHERE codice = ?";
		int err;
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, prodotto.getCodice());

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
