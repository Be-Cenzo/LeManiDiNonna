package catalogoManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import javax.sql.DataSource;
import checking.*;

public class DepositoModelDS{
	private DataSource ds = null;

	public DepositoModelDS(DataSource ds) {
		this.ds = ds;
	}

	
	public Deposito doRetrieveByKey(int ID) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Deposito deposito = new Deposito();

		String selectSQL = "SELECT * FROM deposito WHERE ID = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, ID);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				deposito.setID(rs.getInt("ID"));
				deposito.setLuogo(rs.getString("luogo"));
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
		return deposito;
	}

	
	public ArrayList<Deposito> doRetrieveAll(String order) throws CheckException, SQLException {
		//pre-condition
		if(order != null && order != "" && order != "ASC" && order != "DESC")
			throw new CheckException("Invalid order");
		//fine
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<Deposito> depositi = new ArrayList<Deposito>();

		String selectSQL = "SELECT * FROM deposito";

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Deposito deposito = new Deposito();

				deposito.setID(rs.getInt("ID"));
				deposito.setLuogo(rs.getString("luogo"));

				depositi.add(deposito);
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
		return depositi;
	}

	
	public void doSave(Deposito deposito) throws CheckException, SQLException {
		//pre-condition
		Validazione.checkStringaVuota(deposito.getLuogo());
		//fine
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO deposito (luogo) VALUES (?)";

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);

			preparedStatement.setString(1, deposito.getLuogo());
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

	
	public void doUpdate(Deposito deposito) throws CheckException, SQLException, DBException {
		//pre-condition
		Validazione.checkStringaVuota(deposito.getLuogo());
		//fine
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE deposito SET " + "luogo = ? WHERE ID = ?";
		int err;
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);

			preparedStatement.setString(1, deposito.getLuogo());
			
			preparedStatement.setInt(2, deposito.getID());

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

	
	public void doDelete(Deposito deposito) throws SQLException, DBException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String deleteSQL = "DELETE FROM deposito WHERE ID = ?";
		int err;
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, deposito.getID());

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
