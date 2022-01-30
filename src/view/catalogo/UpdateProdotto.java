package view.catalogo;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import catalogoManagement.ConservatoModelDS;
import catalogoManagement.Deposito;
import catalogoManagement.DepositoModelDS;
import catalogoManagement.PhotoModelDS;
import catalogoManagement.Prodotto;
import catalogoManagement.ProdottoModelDS;
import catalogoManagement.TaglieModelDS;
import checking.CheckException;
import checking.DBException;
import checking.Validazione;

/**
 * Servlet implementation class UpdateProdotto
 */
@WebServlet("/UpdateProdotto")
public class UpdateProdotto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateProdotto() {
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
		
		//controllo
		if(request.getSession().getAttribute("role") == null || request.getSession().getAttribute("role") != "admin") {
			response.sendRedirect(response.encodeURL("./accessdenied.jsp"));
			return;
		}
		//fine
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/aggiungiquantita.jsp");
		Integer error = 0;
		request.setAttribute("errore-operazione",  error);
		
		String prod = request.getParameter("prod");
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		ProdottoModelDS model = new ProdottoModelDS(ds);
		
		Prodotto prodotto = null;
		try {
			prodotto = model.doRetrieveByKey(Integer.parseInt(prod));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(prodotto == null)
			throw new IOException("Prodotto non valido");
		
		String tipo = prodotto.getTipo();
		String taglia = null;
		if(tipo.equals("felpa") || tipo.equals("t-shirt"))
			try {
				taglia = Validazione.checkStringaVuota(request.getParameter("taglia"));
				taglia = Validazione.checkTaglia(request.getParameter("taglia"));
			}
			catch(CheckException e) {
				error = 1;
				request.setAttribute("errore-operazione", error);
				dispatcher.forward(request, response);
				return;
			}
		else
			taglia = "N";
		prodotto.setTaglia(taglia);
		
		int quantita = 0;
		try {
			quantita = Integer.parseInt(request.getParameter("quant"));
			if(quantita < 0)
				throw new CheckException("Error negative quantity");
		}
		catch(CheckException e) {
			e.printStackTrace();
			error = 2;
			request.setAttribute("errore-operazione", error);
			dispatcher.forward(request, response);
			return;
		}
		
		//deposito
		DepositoModelDS modelD = new DepositoModelDS(ds);
		String deposito = null;
		Deposito dep = null;
		try {
		int temp = Integer.parseInt(request.getParameter("deposito"));
		deposito = request.getParameter("deposito");
		dep = modelD.doRetrieveByKey(Integer.parseInt(deposito));
		if(dep.getLuogo() == null)
			throw new CheckException("Invalid dep id");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		catch(CheckException e) {
			error = 3;
			request.setAttribute("errore-operazione", error);
			dispatcher.forward(request, response);
			return;
		}
		//fine
		
		//conservato
		ConservatoModelDS modelC = new ConservatoModelDS(ds);
		try {
			int disponibilita = modelC.doRetrieveByKey(prodotto, dep);
			if(disponibilita == -1)
				modelC.doSave(prodotto, dep, quantita);
			else
				modelC.doUpdate(prodotto, dep, disponibilita + quantita);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		catch(CheckException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//fine
		RequestDispatcher next = request.getRequestDispatcher("/chisiamo.jsp");
		next.forward(request, response);
	}

}
