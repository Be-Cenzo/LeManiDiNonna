package view.catalogo;

import java.io.File;
import view.site.Validazione;
import view.site.Validazione;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import catalogoManagement.*;





@WebServlet("/UploadProdotto")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UploadProdotto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UploadProdotto() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");

		out.write("Error: GET method is used but POST method is required");
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//controllo
		if(request.getSession().getAttribute("role") == null || request.getSession().getAttribute("role") != "admin") {
			response.sendRedirect(response.encodeURL("./accessdenied.jsp"));
			return;
		}
		//fine
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/aggiungiprodotti.jsp");
		Integer error = 0;
		request.setAttribute("errore-operazione",  error);
		String tipo = null;
		try {
			tipo = Validazione.checkStringaVuota(request.getParameter("tipo"));
			tipo = Validazione.checkTipo(request.getParameter("tipo"));
			
		}
		catch(Exception e) {
			error = 1;
			request.setAttribute("errore-operazione", error);
			dispatcher.forward(request, response);
			return;
		}
		float prezzo = 0;
		try {
			String temp = Validazione.checkStringaVuota(request.getParameter("prezzo"));
			prezzo = Float.parseFloat(request.getParameter("prezzo"));
			
		}
		catch(Exception e) {
			error = 2;
			request.setAttribute("errore-operazione", error);
			dispatcher.forward(request, response);
			return;
		}
		String colore = null;
		try {
			colore = Validazione.checkStringaVuota(request.getParameter("colore"));
		}
		catch(Exception e) {
			error = 3;
			request.setAttribute("errore-operazione", error);
			dispatcher.forward(request, response);
			return;
		}
		String descrizione = null;
		try {
			descrizione = Validazione.checkStringaVuota(request.getParameter("desc"));
		}
		catch(Exception e) {
			error = 4;
			request.setAttribute("errore-operazione", error);
			dispatcher.forward(request, response);
			return;
		}
		String marca = null;
		if(tipo.equals("felpa") || tipo.equals("t-shirt")) {
			try {
				marca = Validazione.checkStringaVuota(request.getParameter("marca"));
			}
			catch(Exception e) {
				error = 5;
				request.setAttribute("errore-operazione", error);
				dispatcher.forward(request, response);
				return;
			}
		}
		
		String modello = null;
		if(tipo.equals("felpa") || tipo.equals("t-shirt")) {
			try {
				modello = Validazione.checkStringaVuota(request.getParameter("modello"));
			}
			catch(Exception e) {
				error = 6;
				request.setAttribute("errore-operazione", error);
				dispatcher.forward(request, response);
				return;
			}
		}
		
		String taglia = null;
		if(tipo.equals("felpa") || tipo.equals("t-shirt"))
			try {
				taglia = Validazione.checkStringaVuota(request.getParameter("taglia"));
				taglia = Validazione.checkTaglia(request.getParameter("taglia"));
			}
			catch(Exception e) {
				error = 7;
				request.setAttribute("errore-operazione", error);
				dispatcher.forward(request, response);
				return;
			}
		else
			taglia = "N";
		
		int quantita = 0;
		try {
			quantita = Integer.parseInt(request.getParameter("quant"));
			if(quantita < 0)
				throw new Exception("Error negative quantity");
		}
		catch(Exception e) {
			e.printStackTrace();
			error = 8;
			request.setAttribute("errore-operazione", error);
			dispatcher.forward(request, response);
			return;
		}
		
		
		Prodotto prodotto = new Prodotto();
		prodotto.setTipo(tipo);
		prodotto.setPrezzo(prezzo);
		prodotto.setColore(colore);
		prodotto.setMarca(marca);
		prodotto.setModello(modello);
		prodotto.setTaglia(taglia);
		prodotto.setDescrizione(descrizione);
		
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		ProdottoModelDS modelP = new ProdottoModelDS(ds);
		try {
		modelP.doSave(prodotto);
		}
		catch(SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int codice = this.findCodice(modelP);
		if(codice > 0)
			prodotto.setCodice(this.findCodice(modelP));
		else
			throw new ServletException("Errore");
		
		
		//taglia
		TaglieModelDS modelT = new TaglieModelDS(ds);
		try {
			if(tipo.equals("t-shirt") || tipo.equals("felpa")) {
				modelT.doSave(prodotto.getCodice(), "S");
				modelT.doSave(prodotto.getCodice(), "M");
				modelT.doSave(prodotto.getCodice(), "L");
			}
			else
				modelT.doSave(prodotto.getCodice(), "N");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		//fine
		
		
		//deposito
		DepositoModelDS modelD = new DepositoModelDS(ds);
		String deposito = null;
		Deposito dep = null;
		try {
		int temp = Integer.parseInt(request.getParameter("deposito"));
		deposito = request.getParameter("deposito");
		dep = modelD.doRetrieveByKey(Integer.parseInt(deposito));
		if(dep.getLuogo() == null)
			throw new Exception("Invalid dep id");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			error = 9;
			request.setAttribute("errore-operazione", error);
			dispatcher.forward(request, response);
			return;
		}
		//fine
		
		//conservato
		ConservatoDS modelC = new ConservatoDS(ds);
		try {
			modelC.doSave(prodotto, dep, quantita);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		//fine
		
		
		//upload immagine
		String SAVE_DIR = "/uploadTemp";
		String appPath = request.getServletContext().getRealPath("");
		String savePath = appPath + File.separator + SAVE_DIR;

		File fileSaveDir = new File(savePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir();
		}

		for (Part part : request.getParts()) {
			String fileName = extractFileName(part);
			if (fileName != null && !fileName.equals("")) {
				part.write(savePath + File.separator + fileName);
				try {
					PhotoModelDS.updatePhoto(prodotto.getCodice(), savePath + File.separator + fileName);
				} catch (SQLException sqlException) {
					System.out.println(sqlException);
				}
			}
		}
		//fine

		RequestDispatcher next = request.getRequestDispatcher("/chisiamo.jsp");
		next.forward(request, response);
	}
	
	private int findCodice(ProdottoModelDS modelP) {
		try {
		ArrayList<Prodotto> all = modelP.doRetrieveAll(null);
		return all.size();
		}
		catch(SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		return "";
	}

}
