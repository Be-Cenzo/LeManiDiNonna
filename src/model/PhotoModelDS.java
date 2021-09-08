package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PhotoModelDS {
	public synchronized static byte[] load(int codice) {

		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		byte[] bt = null;

		try {
			connection = DBConnectionPool.getConnection();
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
					DBConnectionPool.releaseConnection(connection);
			}
		}
		return bt;
	}

	public synchronized static void updatePhoto(int codice, String photo) throws SQLException {
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = DBConnectionPool.getConnection();

			stmt = con.prepareStatement("UPDATE prodotto SET imgurl = ? WHERE codice = ?");
			
			File file = new File(photo);
			try {
				FileInputStream fis = new FileInputStream(file);
				stmt.setBinaryStream(1, fis, fis.available());
				stmt.setInt(2, codice);
				
				stmt.executeUpdate();
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
					DBConnectionPool.releaseConnection(con);
			}
		}
	}	
}
