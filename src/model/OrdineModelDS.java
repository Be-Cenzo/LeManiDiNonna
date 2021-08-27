package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

public class OrdineModelDS implements Model<Ordine>{

	private DataSource ds = null;

	public OrdineModelDS(DataSource ds) {
		this.ds = ds;
	}
	
	@Override
	public Ordine doRetrieveByKey(String ID) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

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

	@Override
	public ArrayList<Ordine> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

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

	@Override
	public void doSave(Ordine product) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doUpdate(Ordine product) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doDelete(Ordine product) throws SQLException {
		// TODO Auto-generated method stub
		
	}

}
