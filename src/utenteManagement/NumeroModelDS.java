package utenteManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import javax.sql.DataSource;
import checking.*;

public class NumeroModelDS{
	
	private String email;
	private DataSource ds;
	
	public NumeroModelDS(DataSource ds, String email) {
		this.ds = ds;
		this.email = email;
	}

	public String doRetrieveByKey(String numero) throws CheckException, SQLException {
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

	public ArrayList<String> doRetrieveAll(String order) throws CheckException, SQLException {
		//pre-condition
		if(order != null && order != "" && order != "ASC" && order != "DESC")
			throw new CheckException("Invalid order");
		//fine
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<String> numeri = new ArrayList<String>();

		String selectSQL = "SELECT * FROM numtel WHERE email = ?";

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY num " + order;
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

	public void doSave(String numero) throws CheckException, SQLException {
		//pre-condition
		Validazione.checkNumero(numero);
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

	public void doUpdate(String oldNumero, String newNumero) throws CheckException, SQLException, DBException {
		//pre-condition
		Validazione.checkNumero(newNumero);
		//fine
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE numtel SET num = ? WHERE num = ? AND email = ?";
		int err;
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);

			preparedStatement.setString(1, newNumero);
			preparedStatement.setString(2,oldNumero);
			preparedStatement.setString(3, email);

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

	public void doDelete(String numero) throws CheckException, SQLException, DBException {
		//pre-condition
		Validazione.checkStringaVuota(numero);
		//fine
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String deleteSQL = "DELETE FROM numtel WHERE num = ? AND email = ?";
		int err;
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, numero);
			preparedStatement.setString(2, email);

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
