package control;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import model.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class Signup
 */
@WebServlet("/Signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Signup() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
		String email = request.getParameter("email");
		Account acc = null;
		try {
			acc = this.checkEmail(email);
		}
		catch(Exception e) {
			//codice per ritornare email non valida
		}
		String psw = request.getParameter("psw");
		String name = request.getParameter("name");
		String surnname = request.getParameter("surname");
		String birth = request.getParameter("birth");
		String ig = request.getParameter("ig");
		
		
		
	}
	
	private Account checkEmail(String email) throws Exception {
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		AccountModelDS model = new AccountModelDS(ds);
		Account acc = null;
		try {
		acc = model.doRetrieveByKey(email);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		if(acc.getEmail() == null)
			return acc;
		else
			throw new Exception("Invalid Email");

	}

}
