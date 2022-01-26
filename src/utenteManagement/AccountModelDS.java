package utenteManagement;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import view.site.Validazione;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

public class AccountModelDS {

	private DataSource ds = null;

	public AccountModelDS(DataSource ds) {
		this.ds = ds;
	}
	
	
	public Account doRetrieveByKey(String email) throws Exception {
		if(email == null || email == "")
			throw new Exception("Email not valid");
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

	
	public ArrayList<Account> doRetrieveAll(String order) throws Exception {
		//pre-condition
		if(order != null && order != "" && order != "ASC" && order != "DESC")
			throw new Exception("Invalid order");
		//fine
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<Account> accounts = new ArrayList<Account>();

		String selectSQL = "SELECT * FROM account";

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY email " + order;
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

	
	public void doSave(Account account) throws Exception {
		//pre-condition
		Validazione.checkEmailForUpdate(account.getEmail(), ds);
		Validazione.checkPassword(account.getPassword(), account.getPassword());
		Validazione.checkStringaVuota(account.getNome());
		Validazione.checkData(account.getDataNascita());
		//fine
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String password = account.getPassword();
		String psw = new String();
		/*md5 pass*/
		try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(password.getBytes());
            //Get the hash's bytes 
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            psw = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
		/*end*/
		account.setPassword(psw);

		String insertSQL = "INSERT INTO account" + " (email, nome, cognome, dataNascita, password, nomeIG) VALUES (?, ?, ?, ?, ?, ?)";

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

	
	public void doUpdate(Account account) throws Exception {
		//pre-condition
		Validazione.checkEmailForUpdate(account.getEmail(), ds);
		Validazione.checkPassword(account.getPassword(), account.getPassword());
		Validazione.checkStringaVuota(account.getNome());
		Validazione.checkData(account.getDataNascita());
		//fine
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String password = account.getPassword();
		String psw = new String();
		/*md5 pass*/
		try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(password.getBytes());
            //Get the hash's bytes 
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            psw = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
		/*end*/
		account.setPassword(psw);

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
	
	public void doUpdateInfo(Account account) throws Exception {
		//pre-condition
		Validazione.checkEmailForUpdate(account.getEmail(), ds);
		Validazione.checkPassword(account.getPassword(), account.getPassword());
		Validazione.checkStringaVuota(account.getNome());
		Validazione.checkData(account.getDataNascita());
		//fine
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

	
	public void doDelete(Account account) throws Exception {
		Validazione.checkStringaVuota(account.getEmail());
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
