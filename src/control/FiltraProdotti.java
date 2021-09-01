package control;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FiltraProdotti
 */
@WebServlet("/FiltraProdotti")
public class FiltraProdotti extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FiltraProdotti() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("richiesta arrivatas");
		
		ArrayList<String> filter = (ArrayList<String>) request.getSession(true).getAttribute("filter");
		if(filter == null) {
			filter = new ArrayList<String>();
			request.getSession().setAttribute("filter", filter);
		}
		
		String filtro = request.getParameter("filtro");
		
		if(filtro != null) {
			if(filtro.equals("t-shirt") || filtro.equals("felpa") || filtro.equals("borsello") || filtro.equals("shopper") || filtro.equals("grembiule"))
				if(filter.contains(filtro))
					filter.remove(filtro);
				else
					filter.add(filtro);
			else if(filtro.equals("remove"))
				filter = new ArrayList<String>();
		}
		
		request.getSession().setAttribute("filter", filter);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
