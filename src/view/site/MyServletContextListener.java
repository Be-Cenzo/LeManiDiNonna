package view.site;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class MyServletContextListener implements ServletContextListener {
	
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();

		DataSource ds = null;
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			ds = (DataSource) envCtx.lookup("jdbc/ecommerce");

			try {
				Connection con = ds.getConnection();

				DatabaseMetaData metaData = con.getMetaData();
				System.out.println("JDBC version: " + metaData.getJDBCMajorVersion() + "." + metaData.getJDBCMinorVersion());
				System.out.println("Product name: " + metaData.getDatabaseProductName());
				System.out.println("Product version: " + metaData.getDatabaseProductVersion());

			} catch (SQLException e) {
				System.out.println(e);
			}

		} catch (NamingException e) {
			System.out.println(e);
		}

		context.setAttribute("DataSource", ds);
		System.out.println("DataSource creation: " + ds.toString());
	}
	
	public void contextDestroyed(ServletContextEvent event) {
		ServletContext context = event.getServletContext();

		DataSource ds = (DataSource) context.getAttribute("DataSource");
		context.removeAttribute("DataSource");

		System.out.println("DataSource deletion: " + ds.toString());
	}

}
