package view.utente;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.*;

import catalogoManagement.*;
import utenteManagement.Account;
import utenteManagement.AccountModelDS;
import utenteManagement.IndirizzoModelDS;
import utenteManagement.NumeroModelDS;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/login")
public class Login extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		{
			String username = request.getParameter("uname");
			String password = request.getParameter("psw");
			String role = new String();
			String redirectedPage;
			try {
				role = checkLogin(username, password, request);
				request.getSession().setAttribute("role", role);
				request.getSession().setAttribute("loginError", 0);
				redirectedPage = "./chisiamo.jsp";
			} catch (Exception e) {
				request.getSession().setAttribute("role", "guest");
				request.getSession().setAttribute("loginError", 1);
				redirectedPage = "./login.jsp";
			}
			request.getSession().setMaxInactiveInterval(-1);
			response.sendRedirect(response.encodeURL(redirectedPage));
		}
	}

	private String checkLogin(String username, String password, HttpServletRequest request) throws Exception{
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		AccountModelDS model = new AccountModelDS(ds);
		Account acc = null;
		try {
		acc = model.doRetrieveByKey(username);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
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
		Object account = acc;

		IndirizzoModelDS modelI = new IndirizzoModelDS(ds, acc.getEmail());
		NumeroModelDS modelN = new NumeroModelDS(ds, acc.getEmail());
		
		try {
			acc.setIndirizzi(modelI.doRetrieveAll(""));
			acc.setNumeriTel(modelN.doRetrieveAll(""));
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	
		if(acc.getEmail() == null || !(psw.equals(acc.getPassword())))
			throw new Exception("Invalid login and password");
		else if("vincenzo.offertucci@gmail.com".equals(acc.getEmail()) || "christian.gambardella@gmail.com".equals(acc.getEmail())) {
			request.getSession().setAttribute("user", account);
			return "admin";
		}
		else {
			request.getSession().setAttribute("user", account);
			return "user";
		}
	}
	
	private static final long serialVersionUID = 1L;

	public Login() {
		super();
	}	

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	

}
