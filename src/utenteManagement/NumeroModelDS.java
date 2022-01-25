package utenteManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import view.site.Validazione;

public class NumeroModelDS{
	
	private String email;
	private DataSource ds;
	
	public NumeroModelDS(DataSource ds, String email) {
		this.ds = ds;
		this.email = email;
	}

	public String doRetrieveByKey(String numero) throws Exception {
		Validazione.checkNumero(numero);
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String tel = null;

		String selectSQL = "SELECT * FROM numtel WHERE num = ? AND email = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, numero);
			preparedStatement.setString(2, email);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				tel = (rs.getString("num"));
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

		return tel;
	}

	public ArrayList<String> doRetrieveAll(String order) throws Exception {
		//pre-condition
		if(order != null && order != "" && order != "ASC" && order != "DESC")
			throw new Exception("Invalid order");
		//fine
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<String> numeri = new ArrayList<String>();

		String selectSQL = "SELECT * FROM numtel WHERE email = ?";

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, email);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				String num = rs.getString("num");

				numeri.add(num);
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

		return numeri;
	}

	public void doSave(String numero) throws Exception {
		//pre-condition
		if(this.doRetrieveByKey(numero) != null)
			throw new Exception("DB already contains num");
		//fine
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO numtel (num, email) VALUES (?, ?)";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);

			preparedStatement.setString(1, numero);
			preparedStatement.setString(2, email);
			
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

	public void doUpdate(String oldNumero, String newNumero) throws Exception {
		//pre-condition
		if(this.doRetrieveByKey(newNumero) != null)
			throw new Exception("DB already contains num");
		if(this.doRetrieveByKey(oldNumero) == null)
			throw new Exception("DB doesn't contain num");
		//fine
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE numtel SET num = ? WHERE num = ? AND email = ?";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);

			preparedStatement.setString(1, newNumero);
			preparedStatement.setString(2,oldNumero);
			preparedStatement.setString(3, email);

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

	public void doDelete(String numero) throws Exception {
		//pre-condition
		if(this.doRetrieveByKey(numero) == null)
			throw new Exception("Db doesn't contain num");
		//fine
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String deleteSQL = "DELETE FROM numtel WHERE num = ? AND email = ?";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, numero);
			preparedStatement.setString(2, email);

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
