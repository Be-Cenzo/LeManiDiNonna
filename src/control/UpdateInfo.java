package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import model.Account;
import model.AccountModelDS;
import utility.Validazione;

/**
 * Servlet implementation class UpdateInfo
 */
@WebServlet("/UpdateInfo")
public class UpdateInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				//controllo accesso
				if(request.getSession().getAttribute("role") == null || (request.getSession().getAttribute("role")).equals("guest")) {
					response.sendRedirect(response.encodeURL("./accessdenied.jsp"));
					return;
				}
				//fine
				RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/informazioniutente.jsp");
				DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
				
				Account user = (Account) request.getSession().getAttribute("user");
				int error = 0;
				request.setAttribute("errore-modifica", error);
				String name = request.getParameter("name");
				try {
					name = Validazione.checkStringaVuota(name);
					user.setNome(name);
				}catch(Exception e) {
					error = 1;
					System.out.println("errore");
					request.setAttribute("errore-modifica", error);
					dispatcher.forward(request, response);
					return;
				}
				String surname = request.getParameter("surname");
				try {
					surname = Validazione.checkStringaVuota(surname);
					user.setCognome(surname);
				}catch(Exception e) {
					error = 2;
					request.setAttribute("errore-modifica", error);
					dispatcher.forward(request, response);
					return;
				}
				String birth = request.getParameter("birth");
				try {
					birth = Validazione.checkStringaVuota(birth);
					user.setDataNascita(birth);
				}catch(Exception e) {
					error = 3;
					request.setAttribute("errore-modifica", error);
					dispatcher.forward(request, response);
					return;
				}
				String ig = request.getParameter("ig");
				user.setNomeIG(ig);
				AccountModelDS model = new AccountModelDS(ds);
				try {
					model.doUpdateInfo(user);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				request.getSession().setAttribute("user", user);
				
				response.sendRedirect(response.encodeURL("./informazioniutente.jsp"));
	}

}
