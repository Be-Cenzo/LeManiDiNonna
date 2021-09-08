package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

public class AccountModelDS implements Model<Account>{

	private DataSource ds = null;

	public AccountModelDS(DataSource ds) {
		this.ds = ds;
	}
	
	@Override
	public Account doRetrieveByKey(String email) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Account account = new Account();

		String selectSQL = "SELECT * FROM account WHERE email = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, email);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				account.setEmail(rs.getString("email"));
				account.setNome(rs.getString("nome"));
				account.setCognome(rs.getString("cognome"));
				account.setDataNascita(rs.getString("dataNascita"));
				account.setPassword(rs.getString("password"));
				account.setNomeIG(rs.getString("nomeIG"));
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
		return account;
	}

	@Override
	public ArrayList<Account> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<Account> accounts = new ArrayList<Account>();

		String selectSQL = "SELECT * FROM account";

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Account account = new Account();

				account.setEmail(rs.getString("email"));
				account.setNome(rs.getString("nome"));
				account.setCognome(rs.getString("cognome"));
				account.setDataNascita(rs.getString("dataNascita"));
				account.setPassword(rs.getString("password"));
				account.setNomeIG(rs.getString("nomeIG"));

				accounts.add(account);
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
		return accounts;
	}

	@Override
	public void doSave(Account account) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO account" + " (email, nome, cognome, dataNascita, password, nomeIG) VALUES (?, ?, ?, ?, MD5(?), ?)";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);

			preparedStatement.setString(1, account.getEmail());
			preparedStatement.setString(2, account.getNome());
			preparedStatement.setString(3, account.getCognome());
			preparedStatement.setString(4, account.getDataNascita());
			preparedStatement.setString(5, account.getPassword());
			preparedStatement.setString(6, account.getNomeIG());
			
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
	public void doUpdate(Account account) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE account SET " + " nome = ?, cognome = ?, dataNascita = ?, password = ?, NomeIG = ? WHERE email = ?";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);

			preparedStatement.setString(1, account.getNome());
			preparedStatement.setString(2, account.getCognome());
			preparedStatement.setString(3, account.getDataNascita());
			preparedStatement.setString(4, account.getPassword());
			preparedStatement.setString(5, account.getNomeIG());
			
			preparedStatement.setString(6, account.getEmail());
			

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
	
	public void doUpdateInfo(Account account) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE account SET " + " nome = ?, cognome = ?, dataNascita = ?, NomeIG = ? WHERE email = ?";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);

			preparedStatement.setString(1, account.getNome());
			preparedStatement.setString(2, account.getCognome());
			preparedStatement.setString(3, account.getDataNascita());
			preparedStatement.setString(4, account.getNomeIG());
			
			preparedStatement.setString(5, account.getEmail());
			

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
	public void doDelete(Account account) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String deleteSQL = "DELETE FROM account WHERE email = ?";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, account.getEmail());

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
