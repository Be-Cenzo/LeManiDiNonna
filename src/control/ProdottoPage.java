package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import model.Prodotto;
import model.ProdottoModelDS;

/**
 * Servlet implementation class ProdottoPage
 */
@WebServlet("/ProdottoPage")
public class ProdottoPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProdottoPage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		ProdottoModelDS model = new ProdottoModelDS(ds);
		
		String id = (String) request.getParameter("id");
		
		Prodotto prod = null;
		System.out.println("id: " + id);
		
		if(id != null) {
			try {
				prod = model.doRetrieveByKey(id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		if(prod != null) {
			request.setAttribute("prodotto-page", prod);
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/prodotto.jsp");
			dispatcher.forward(request, response);
		}
		//prodotto non trovato
		System.out.println("nient");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
