package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.*;
import utility.Validazione;

import javax.servlet.RequestDispatcher;
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
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	doPost(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
		//controllo accesso
		//se role è null allora non è ne user ne admin 
		//(controllo aggiunto perchè se era null non poteva chiamare la equals)
		if((request.getSession() != null) && (request.getSession().getAttribute("role") != null) && ((request.getSession().getAttribute("role")).equals("user") || (request.getSession().getAttribute("role")).equals("admin"))) {
			response.sendRedirect(response.encodeURL("./accessdenied.jsp"));
			return;
		}
		//fine
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/signup.jsp");
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		
		Integer error = 0;
		request.setAttribute("errore-registrazione", error);
		String email = request.getParameter("email");
		Account acc = null;
		try {
			acc = Validazione.checkEmail(email, ds);
			acc.setEmail(email);
		}
		catch(Exception e) {
			error = 1;
			request.setAttribute("errore-registrazione", error);
			dispatcher.forward(request, response);
			return;
		}
		String numero = null;
		try {
			numero = Validazione.checkNumero(request.getParameter("phone"));
			acc.addNumeroTel(numero);
		}
		catch(Exception e) {
			error = 2;
			request.setAttribute("errore-registrazione", error);
			dispatcher.forward(request, response);
			return;
		}
		String psw = request.getParameter("psw");
		String pswr = request.getParameter("psw-repeat");
		try {
			psw = Validazione.checkPassword(psw, pswr);
			acc.setPassword(psw);
		}catch(Exception e) {
			error = 3;
			request.setAttribute("errore-registrazione", error);
			dispatcher.forward(request, response);
			return;
		}
		
		String name = request.getParameter("name");
		try {
			name = Validazione.checkStringaVuota(name);
			acc.setNome(name);
		}catch(Exception e) {
			error = 4;
			request.setAttribute("errore-registrazione", error);
			dispatcher.forward(request, response);
			return;
		}
		String surname = request.getParameter("surname");
		try {
			surname = Validazione.checkStringaVuota(surname);
			acc.setCognome(surname);
		}catch(Exception e) {
			error = 5;
			request.setAttribute("errore-registrazione", error);
			dispatcher.forward(request, response);
			return;
		}
		String birth = request.getParameter("birth");
		try {
			birth = Validazione.checkStringaVuota(birth);
			acc.setDataNascita(birth);
		}catch(Exception e) {
			error = 6;
			request.setAttribute("errore-registrazione", error);
			dispatcher.forward(request, response);
			return;
		}
		String ig = request.getParameter("ig");
		acc.setNomeIG(ig);
		String provincia = request.getParameter("provincia");
		try {
			provincia = Validazione.checkStringaVuota(provincia);
		}catch(Exception e) {
			error = 7;
			request.setAttribute("errore-registrazione", error);
			dispatcher.forward(request, response);
			return;
		}
		String comune = request.getParameter("comune");
		try {
			comune = Validazione.checkStringaVuota(comune);
		}catch(Exception e) {
			error = 8;
			request.setAttribute("errore-registrazione", error);
			dispatcher.forward(request, response);
			return;
		}
		String via = request.getParameter("via");
		try {
			via = Validazione.checkStringaVuota(via);
		}catch(Exception e) {
			error = 9;
			request.setAttribute("errore-registrazione", error);
			dispatcher.forward(request, response);
			return;
		}
		int civico = 0;
		try {
			civico = Integer.parseInt(request.getParameter("civico"));
		}catch(Exception e) {
			error = 10;
			request.setAttribute("errore-registrazione", error);
			dispatcher.forward(request, response);
			return;
		}
		String cap = request.getParameter("cap");
		try {
			cap = Validazione.checkStringaVuota(cap);
		}catch(Exception e) {
			error = 11;
			request.setAttribute("errore-registrazione", error);
			dispatcher.forward(request, response);
			return;
		}
		int id = findID(email);
		Indirizzo address = new Indirizzo(id, email, provincia, comune, via, civico, cap);
		acc.addIndirizzo(address);
		if(!numero.contains("+39"))
			numero = "+39" + numero;
		numero = numero.replace(" ", "");
		this.save(acc, address, numero);
		request.getSession().setAttribute("role", "user");
		request.getSession().setAttribute("user", acc);
		request.getSession().setMaxInactiveInterval(-1);
		response.sendRedirect(response.encodeURL("./index.jsp"));
		
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
	
	private void save(Account acc, Indirizzo address, String numero) {
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		IndirizzoModelDS modelI = new IndirizzoModelDS(ds, acc.getEmail());
		AccountModelDS modelA = new AccountModelDS(ds);
		NumeroModelDS modelN = new NumeroModelDS(ds, acc.getEmail());
		try {
		modelA.doSave(acc);
		modelI.doSave(address);
		modelN.doSave(numero);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
