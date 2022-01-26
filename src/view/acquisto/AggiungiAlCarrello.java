package view.acquisto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import acquistoManagement.Carrello;
import catalogoManagement.Prodotto;
import catalogoManagement.ProdottoModelDS;

/**
 * Servlet implementation class AggiungiAlCarrello
 */
@WebServlet("/AggiungiAlCarrello")
public class AggiungiAlCarrello extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AggiungiAlCarrello() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		int quantita = Integer.parseInt(request.getParameter("quantità"));
		String taglia = (String) request.getParameter("taglia");
		if(quantita > 0) {
			
			if(taglia == null)
				taglia = "N";
		
			Carrello cart = (Carrello) request.getSession(true).getAttribute("carrello");
			if (cart == null) {
				cart = new Carrello();
				request.getSession().setAttribute("carrello", cart);
			}
			
			DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
			
			ProdottoModelDS model = new ProdottoModelDS(ds);
			
			Prodotto prod = null;
			try {
				prod = model.doRetrieveByKey(Integer.parseInt(id));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(prod != null && (taglia.equals("S") || taglia.equals("M") || taglia.equals("L") || taglia.equals("N"))) {
				prod.setTaglia(taglia);
				prod.addQuantita(quantita);
				cart.addProdotto(prod);
				System.out.println("aggiunto");
				request.getSession().setAttribute("carrello", cart);
			}
		}
		
		response.sendRedirect(response.encodeURL("./prodotti.jsp"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
