package control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
import model.Ordine;
import model.OrdineModelDS;
import model.Prodotto;
import model.ProdottoModelDS;
import model.RelatoDS;

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
		
		Ordine ord = new Ordine(7, new Date(15000000000l), 65, 10, "no scusa o sbagliato", "contabilizzato", "vincenzo.offertucci@gmail.com", 1, "Bartolini");
		ProdottoModelDS modprod = new ProdottoModelDS(ds);
		OrdineModelDS mod = new OrdineModelDS(ds);
		Prodotto prod = null;
		Prodotto due = null;
		try {
			prod = modprod.doRetrieveByKey("1");
			due = modprod.doRetrieveByKey("2");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HashMap<Prodotto, Integer> quant = new HashMap<Prodotto, Integer>();
		quant.put(prod, 10);
		quant.put(due, 5);
		ord.setQuantità(quant);
		RelatoDS rel = new RelatoDS(ds);
		int quantità = 0;
		/*try {
			mod.creaOrdine(ord);
			rel.doDelete(prod, ord);
			rel.doDelete(due, ord);
			HashMap<Prodotto, Integer> quantitàOrd = rel.doRetrieveAll("", ord);
			quantitàOrd.forEach((p, q) -> {
				System.out.println("Prodotto: " + p.getDescrizione() + "quantità: " + q);
			});
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		response.getWriter().append("<html><head></head><body>");
		if(ord!=null)
			response.getWriter().append("ha funzionato:<br><p>prodotto: " + prod.getDescrizione() + "</p><p>ordine: " + ord.getID() + "</p><p>quantità: " + quantità + "</p>").append("Served at: ").append(request.getContextPath());
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
