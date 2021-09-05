package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.*;
import java.util.*;
import javax.sql.*;



import java.lang.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
				redirectedPage = "/index.jsp";
			} catch (Exception e) {
				request.getSession().setAttribute("role", "guest");
				redirectedPage = "/carrello.jsp";
			}
			request.getSession().setMaxInactiveInterval(-1);
			response.sendRedirect(request.getContextPath() + redirectedPage);
		}
	}

	private String checkLogin(String username, String password, HttpServletRequest request) throws Exception {
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		AccountModelDS model = new AccountModelDS(ds);
		Account acc = model.doRetrieveByKey(username);
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
		
	
		if(acc.getEmail() == null || !(psw.equals(acc.getPassword())))
			throw new Exception("Invalid login and password");
		else if("vincenzo.offertucci@gmail.com".equals(acc.getEmail()) || "christian.gambardella@gmail.com".equals(acc.getEmail()))
			return "admin";
		else {
			request.getSession().setAttribute("user", acc);
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
