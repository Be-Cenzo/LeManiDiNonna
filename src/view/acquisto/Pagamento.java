package view.acquisto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import acquistoManagement.Carrello;
import acquistoManagement.Corriere;
import acquistoManagement.CorriereModelDS;
import acquistoManagement.CreaOrdineDS;
import catalogoManagement.Prodotto;
import utenteManagement.Account;
import utenteManagement.IndirizzoModelDS;

/**
 * Servlet implementation class Pagamento
 */
@WebServlet("/Pagamento")
public class Pagamento extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Pagamento() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//controllo accesso
		if(request.getSession().getAttribute("role") == null || (request.getSession().getAttribute("role")).equals("guest")) {
			response.sendRedirect(response.encodeURL("./accessdenied.jsp"));
			return;
		}
		//fine
		Account user = (Account) request.getSession().getAttribute("user");
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		IndirizzoModelDS model = new IndirizzoModelDS(ds, user.getEmail());
		
		try {
			user.setIndirizzi(model.doRetrieveAll(""));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.getSession().setAttribute("user", user);
		//System.out.println("sto in pagamento.java l'user è : " + user.getEmail());
		
		ArrayList<Corriere> corrieri = null;
		CorriereModelDS corrmod = new CorriereModelDS(ds);
		
		try {
			corrieri = corrmod.doRetrieveAll("");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.getSession().setAttribute("corrieri", corrieri);
		
		CreaOrdineDS crea = new CreaOrdineDS(ds);
		Carrello cart = (Carrello) request.getSession().getAttribute("carrello");
		
		ArrayList<Prodotto> prodotti = crea.checkDisponibilità(cart.getProdotti());
		
		if(!prodotti.isEmpty()) {
			request.setAttribute("nonDisponibili", prodotti);
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/carrello.jsp");
			dispatcher.forward(request, response);
			return;
		}

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/pagamento.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
