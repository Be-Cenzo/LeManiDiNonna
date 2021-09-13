package control;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;

import model.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;





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
		
		String tipo = request.getParameter("tipo");
		float prezzo = Float.parseFloat(request.getParameter("prezzo"));
		String colore = request.getParameter("colore");
		String marca = request.getParameter("marca");
		String modello = request.getParameter("modello");
		String taglia = request.getParameter("taglia");
		int quantita = Integer.parseInt(request.getParameter("quant"));
		String deposito = request.getParameter("deposito");
		String descrizione = request.getParameter("desc");
		
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
		}
		
		int codice = this.findCodice(modelP);
		if(codice > 0)
			prodotto.setCodice(this.findCodice(modelP));
		else
			throw new ServletException("Errore");
		
		
		//taglia
		TaglieModelDS modelT = new TaglieModelDS(ds);
		try {
		modelT.doSave(prodotto.getCodice(), taglia);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		//fine
		
		
		//deposito
		DepositoModelDS modelD = new DepositoModelDS(ds);
		Deposito dep = null;
		try {
		dep = modelD.doRetrieveByKey(deposito);
		}
		catch(SQLException e) {
			e.printStackTrace();
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
		System.out.println(request.getParameter("id"));
		//int id = Integer.parseInt(request.getParameter("id"));
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
