package catalogoManagement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import checking.DBException;

public class PhotoModelDS {
	public synchronized static byte[] load(int codice, DataSource datasource) throws SQLException {

		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		DataSource ds = datasource;

		byte[] bt = null;

		try {
			connection = ds.getConnection();
			String sql = "SELECT imgurl FROM prodotto WHERE codice = ?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setInt(1, codice);
			rs = stmt.executeQuery();

			if (rs.next()) {
				bt = rs.getBytes("imgurl");
			}

		} catch (SQLException sqlException) {
			System.out.println(sqlException);
		} 
			finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException sqlException) {
				System.out.println(sqlException);
			} finally {
				if (connection != null) 
					connection.close();
			}
		}
		return bt;
	}

	public synchronized static void updatePhoto(int codice, String photo, DataSource datasource) throws SQLException, DBException {
		Connection con = null;
		PreparedStatement stmt = null;
		DataSource ds = datasource;
		int err = 0;
		try {
			con = ds.getConnection();

			stmt = con.prepareStatement("UPDATE prodotto SET imgurl = ? WHERE codice = ?");
			
			File file = new File(photo);
			try {
				FileInputStream fis = new FileInputStream(file);
				stmt.setBinaryStream(1, fis, fis.available());
				
				stmt.setInt(2, codice);
				
				err = stmt.executeUpdate();
				con.commit();
			} catch (FileNotFoundException e) {
				System.out.println(e);
			} catch (IOException e) {
				System.out.println(e);
			}
		} finally { 
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException sqlException) {
				System.out.println(sqlException);
			} finally {
				if (con != null)
					con.close();
			}
		}
		if(err == 0)
			throw new DBException("Update failed");
	}	
}
