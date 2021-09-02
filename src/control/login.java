package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class login extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		{
			String username = request.getParameter("uname");
			String password = request.getParameter("psw");
			
			String redirectedPage;
			try {
				checkLogin(username, password);
				request.getSession().setAttribute("adminRoles", new Boolean(true));
				redirectedPage = "/prova.html";
			} catch (Exception e) {
				request.getSession().setAttribute("adminRoles", new Boolean(false));
				redirectedPage = "/carrello.jsp";
			}
			response.sendRedirect(request.getContextPath() + redirectedPage);
		}
	}

	private void checkLogin(String username, String password) throws Exception {
		if ("root".equals(username) && "admin".equals(password)) {
			//
		} else
			throw new Exception("Invalid login and password");
	}
	
	private static final long serialVersionUID = 1L;

	public login() {
		super();
	}	

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	

}
