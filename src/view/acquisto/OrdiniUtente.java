package view.acquisto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import acquistoManagement.Ordine;
import acquistoManagement.OrdineModelDS;
import acquistoManagement.RelatoModelDS;
import catalogoManagement.Prodotto;
import utenteManagement.Account;

/**
 * Servlet implementation class OrdiniUtente
 */
@WebServlet("/OrdiniUtente")
public class OrdiniUtente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrdiniUtente() {
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
		//fin
		
		Account user = (Account) request.getSession().getAttribute("user");
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		OrdineModelDS model = new OrdineModelDS(ds);
		RelatoModelDS relmodel = new RelatoModelDS(ds);
		
		ArrayList<Ordine> ordini = null;
		
		try {
			ordini = model.doRetrieveAll("", user.getEmail());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Ordine ord : ordini) {
			ArrayList<Prodotto> prodotti = null;
			try {
				prodotti = relmodel.doRetrieveAll("", ord);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(prodotti != null)
				ord.setProdotti(prodotti);
		}
		
		request.getSession().setAttribute("ordini-utente", ordini);
		
		response.sendRedirect(response.encodeURL("./ordini.jsp"));
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
