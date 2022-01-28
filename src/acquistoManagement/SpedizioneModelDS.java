package acquistoManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import view.site.Validazione;

public class SpedizioneModelDS {

	private DataSource ds = null;

	public SpedizioneModelDS(DataSource ds) {
		this.ds = ds;
	}
	
	
	public Spedizione doRetrieveByKey(String nome) throws Exception {
		//pre-condition
		Validazione.checkStringaVuota(nome);
		//fine
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Spedizione spedizione = new Spedizione();

		String selectSQL = "SELECT * FROM corriere WHERE nome = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, nome);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				spedizione.setNome(rs.getString("nome"));
				spedizione.setTempo(rs.getString("tempo"));
				spedizione.setPrezzo(rs.getFloat("costoSped"));
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
		return spedizione;
	}

	
	public ArrayList<Spedizione> doRetrieveAll(String order) throws Exception {
		//pre-condition
		if(order != null && order != "" && order != "ASC" && order != "DESC")
			throw new Exception("Invalid order");
		//fine
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<Spedizione> corrieri = new ArrayList<Spedizione>();

		String selectSQL = "SELECT * FROM corriere";

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY nome " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Spedizione corriere = new Spedizione();

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

	
	public void doSave(Spedizione spedizione) throws Exception {
		//pre-condition
		Validazione.checkStringaVuota(spedizione.getNome());
		Validazione.checkStringaVuota(spedizione.getTempo());
		if(spedizione.getPrezzo() <= 0)
			throw new Exception("Invalid price");
		//fine
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO corriere VALUES (?, ?, ?)";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);

			preparedStatement.setString(1, spedizione.getNome());
			preparedStatement.setString(2, spedizione.getTempo());
			preparedStatement.setFloat(3, spedizione.getPrezzo());
			
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

	
	public void doUpdate(Spedizione spedizione) throws Exception {
		//pre-condition
		Validazione.checkStringaVuota(spedizione.getNome());
		Validazione.checkStringaVuota(spedizione.getTempo());
		if(spedizione.getPrezzo() <= 0)
			throw new Exception("Invalid price");
		//fine
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE corriere SET " + " tempo = ?, costoSped = ? WHERE nome = ?";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);

			preparedStatement.setString(1, spedizione.getTempo());
			preparedStatement.setFloat(2, spedizione.getPrezzo());
			preparedStatement.setString(3, spedizione.getNome());

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

	
	public void doDelete(Spedizione spedizione) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String deleteSQL = "DELETE FROM corriere WHERE nome = ?";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, spedizione.getNome());

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
