package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
		//controllo accesso
		if((request.getSession().getAttribute("role")).equals("user") || (request.getSession().getAttribute("role")).equals("admin")) {
			response.sendRedirect(response.encodeURL("./accessdenied.jsp"));
			return;
		}
		//fine
		
		String email = request.getParameter("email");
		Account acc = null;
		try {
			acc = this.checkEmail(email);
		}
		catch(Exception e) {
			//codice per ritornare email non valida
		}
		//aggiungere controllo num tel e creare bean
		String psw = request.getParameter("psw");
		String name = request.getParameter("name");
		String surnname = request.getParameter("surname");
		String birth = request.getParameter("birth");
		String ig = request.getParameter("ig");
		acc.setEmail(email);
		acc.setPassword(psw);
		acc.setCognome(surnname);
		acc.setNome(name);
		//acc.setDataNascita(birth);
		acc.setNomeIG(ig);
		String provincia = request.getParameter("provincia");
		String comune = request.getParameter("comune");
		String via = request.getParameter("via");
		int civico = Integer.parseInt(request.getParameter("civico"));
		String cap = request.getParameter("cap");
		int id = findID(email);
		Indirizzo address = new Indirizzo(id, email, provincia, comune, via, civico, cap);
		this.save(acc, address);
		request.getSession().setAttribute("role", "user");
		request.getSession().setAttribute("user", acc);
		response.sendRedirect(response.encodeURL("./index.jsp"));
		
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
	
	private int findID(String email) {
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		IndirizzoModelDS model = new IndirizzoModelDS(ds, email);
		try {
		ArrayList<Indirizzo> all = model.doRetrieveAll(null);
		int id = all.size() + 1;
		return id;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	private void save(Account acc, Indirizzo address) {
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		IndirizzoModelDS modelI = new IndirizzoModelDS(ds, acc.getEmail());
		AccountModelDS modelA = new AccountModelDS(ds);
		//aggiungi num tel
		try {
		modelA.doSave(acc);
		modelI.doSave(address);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
