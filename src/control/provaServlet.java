package control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import model.Account;
import model.AccountModelDS;
import model.ConservatoDS;
import model.Corriere;
import model.CorriereModelDS;
import model.Deposito;
import model.DepositoModelDS;
import model.Fattura;
import model.FatturaModelDS;
import model.Fornitura;
import model.FornituraModelDS;
import model.Indirizzo;
import model.IndirizzoModelDS;
import model.Prodotto;
import model.ProdottoModelDS;

/**
 * Servlet implementation class provaServlet
 */
@WebServlet("/provaServlet")
public class provaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public provaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource ds = (DataSource) getServletConfig().getServletContext().getAttribute("DataSource");
		
		Fornitura forn = new Fornitura(4, "Nonna Franca", new Date(1500000000l), 15, 1, 65.65f);
		FornituraModelDS mod = new FornituraModelDS(ds);
		try {
			mod.doDelete(forn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.getWriter().append("<html><head></head><body>");
		if(forn!=null)
			response.getWriter().append("ha funzionato:<br><p>Fornitore: " + forn.getFornitore() + "</p><p>Quantità: " + forn.getQuantità() + "</p><p>Prezzo: " + forn.getPrezzo() + "</p>").append("Served at: ").append(request.getContextPath());
		else
			response.getWriter().append("non a funzionato<br>").append("Served at: ").append(request.getContextPath());
		response.getWriter().append("</body>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
