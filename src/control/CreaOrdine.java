package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import model.Account;
import model.Carrello;
import model.CreaOrdineDS;
import model.IndirizzoModelDS;
import model.Prodotto;

/**
 * Servlet implementation class CreaOrdine
 */
@WebServlet("/CreaOrdine")
public class CreaOrdine extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreaOrdine() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Carrello cart = (Carrello) request.getSession().getAttribute("carrello");
		Account account = (Account) request.getSession().getAttribute("user");
		
		DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
		CreaOrdineDS crea = new CreaOrdineDS(ds);
		
		int idIndirizzo = Integer.parseInt(request.getParameter("idIndirizzo"));
		String corriere = request.getParameter("corriere");
		
		float totale = 0;
		
		for(Prodotto prod : cart.getProdotti()){
			totale += prod.getQuantità()*prod.getPrezzo();
		}
		
		ArrayList<Prodotto> prodotti = crea.checkDisponibilità(cart.getProdotti());
		
		if(!prodotti.isEmpty()) {
		request.setAttribute("nonDisponibili", prodotti);
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/carrello.jsp");
		dispatcher.forward(request, response);
		}
		
		int ris = 0;
		try {
			ris = crea.newOrdine(account.getEmail(), "", idIndirizzo, corriere, cart.getProdotti(), totale);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(ris == 1)
			System.out.println("WOW");
		
		for(Prodotto prod : cart.getProdotti()){
			System.out.println("Prodotto: " + prod.getDescrizione());
		}
		System.out.println("Email: " + account.getEmail());
		System.out.println("Indirizzo id: " + idIndirizzo);
		System.out.println("Corriere: " + corriere);
		
		cart.deleteList();
		request.getSession().setAttribute("carrello", cart);
		
		response.sendRedirect(response.encodeURL("./chisiamo.jsp"));
	}

}
