package view.acquisto;

import java.io.IOException;
import java.sql.SQLException;

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
 * Servlet implementation class RimuoviDalCarrello
 */
@WebServlet("/RimuoviDalCarrello")
public class RimuoviDalCarrello extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RimuoviDalCarrello() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String taglia = request.getParameter("taglia");
		String action = request.getParameter("action");
		
		Carrello cart = (Carrello) request.getSession(true).getAttribute("carrello");
		if (cart == null) {
			cart = new Carrello();
			request.getSession().setAttribute("carrello", cart);
		}
		
		if(action!=null) {
			if(action.equals("all")) {
				cart.deleteList();
				request.getSession().setAttribute("carrello", cart);
			}
			else if(action.equals("remove")){
				DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
				
				ProdottoModelDS model = new ProdottoModelDS(ds);
				Prodotto prod = null;
				try {
					prod = model.doRetrieveByKey(id);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(taglia != null && !taglia.equals("N"))
					prod.setTaglia(taglia);
				else
					prod.setTaglia("N");
				
				if(prod != null) {
					cart.removeProdotto(prod);
					System.out.println("rimosso " + cart.getProdotti().size());
					request.getSession().setAttribute("carrello", cart);
				}
			}
		}
		
		
		
		response.sendRedirect(response.encodeURL("./carrello.jsp"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
