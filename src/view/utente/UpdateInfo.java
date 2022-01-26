package view.utente;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import utenteManagement.Account;
import utenteManagement.AccountModelDS;
import utenteManagement.Indirizzo;
import utenteManagement.IndirizzoModelDS;
import utenteManagement.NumeroModelDS;
import view.site.Validazione;

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
		String action = (String) request.getParameter("action");
		Account user = (Account) request.getSession().getAttribute("user");
		
		if(action != null && action.equals("edit")) {
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
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.getSession().setAttribute("user", user);
		}
		else if(action != null && action.equals("update-addr")) {
			int id = Integer.parseInt(request.getParameter("id"));
			ArrayList<Indirizzo> indirizzi = user.getIndirizzi();
			int error = 0;
			request.setAttribute("errore-update-address", error);
			String provincia = request.getParameter("provincia");
			try {
				provincia = Validazione.checkStringaVuota(provincia);
			}catch(Exception e) {
				error = 1;
				request.setAttribute("errore-update-address", error);
				dispatcher.forward(request, response);
				return;
			}
			String comune = request.getParameter("comune");
			try {
				comune = Validazione.checkStringaVuota(comune);
			}catch(Exception e) {
				error = 2;
				request.setAttribute("errore-update-address", error);
				dispatcher.forward(request, response);
				return;
			}
			String via = request.getParameter("via");
			try {
				via = Validazione.checkStringaVuota(via);
			}catch(Exception e) {
				error = 3;
				request.setAttribute("errore-update-address", error);
				dispatcher.forward(request, response);
				return;
			}
			int civico = 0;
			try {
				civico = Integer.parseInt(request.getParameter("civico"));
			}catch(Exception e) {
				error = 4;
				request.setAttribute("errore-update-address", error);
				dispatcher.forward(request, response);
				return;
			}
			String cap = request.getParameter("cap");
			try {
				cap = Validazione.checkStringaVuota(cap);
			}catch(Exception e) {
				error = 5;
				request.setAttribute("errore-update-address", error);
				dispatcher.forward(request, response);
				return;
			}
			Indirizzo updateAddr = new Indirizzo(id, user.getEmail(), provincia, comune, via, civico, cap);
			for(Indirizzo ind : indirizzi) {
				if(ind.getID() == id) {
					indirizzi.remove(ind);
					indirizzi.add(updateAddr);
					break;
				}
			}
			IndirizzoModelDS modelI = new IndirizzoModelDS(ds, user.getEmail());
			try {
				modelI.doUpdate(updateAddr);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			user.setIndirizzi(indirizzi);
			request.getSession().setAttribute("user", user);
		}
		else if(action != null && action.equals("add-addr")) {
			int error = 0;
			request.setAttribute("errore-add-address", error);
			String provincia = request.getParameter("provincia");
			try {
				provincia = Validazione.checkStringaVuota(provincia);
			}catch(Exception e) {
				error = 1;
				request.setAttribute("errore-add-address", error);
				dispatcher.forward(request, response);
				return;
			}
			String comune = request.getParameter("comune");
			try {
				comune = Validazione.checkStringaVuota(comune);
			}catch(Exception e) {
				error = 2;
				request.setAttribute("errore-add-address", error);
				dispatcher.forward(request, response);
				return;
			}
			String via = request.getParameter("via");
			try {
				via = Validazione.checkStringaVuota(via);
			}catch(Exception e) {
				error = 3;
				request.setAttribute("errore-add-address", error);
				dispatcher.forward(request, response);
				return;
			}
			int civico = 0;
			try {
				civico = Integer.parseInt(request.getParameter("civico"));
			}catch(Exception e) {
				error = 4;
				request.setAttribute("errore-add-address", error);
				dispatcher.forward(request, response);
				return;
			}
			String cap = request.getParameter("cap");
			try {
				cap = Validazione.checkStringaVuota(cap);
			}catch(Exception e) {
				error = 5;
				request.setAttribute("errore-add-address", error);
				dispatcher.forward(request, response);
				return;
			}
			int id = findID(user.getEmail());
			Indirizzo address = new Indirizzo(id, user.getEmail(), provincia, comune, via, civico, cap);
			IndirizzoModelDS modelI = new IndirizzoModelDS(ds, user.getEmail());
			
			try {
				modelI.doSave(address);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			user.addIndirizzo(address);
			request.getSession().setAttribute("user", user);
		}
		else if(action != null && action.equals("add-nmbr")) {
			String numero = request.getParameter("phone");
			if(!numero.contains("+39"))
				numero = "+39" + numero;
			numero = numero.replace(" ", "");
			int error = 0;
			NumeroModelDS modelN = new NumeroModelDS(ds, user.getEmail());
			request.setAttribute("errore-add-number", error);
			if(user.getNumeriTel().contains(numero)) {
				error = 2;
				request.setAttribute("errore-add-number", error);
				dispatcher.forward(request, response);
				return;
			}
			try {
				Validazione.checkNumero(numero);
			}catch(Exception e) {
				error = 1;
				request.setAttribute("errore-add-number", error);
				dispatcher.forward(request, response);
				return;
			}
			try {
				modelN.doSave(numero);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			user.addNumeroTel(numero);
			request.getSession().setAttribute("user", user);
		}
		else if(action != null && action.equals("update-nmbr")) {
			String numero = request.getParameter("phone");
			if(!numero.contains("+39"))
				numero = "+39" + numero;
			numero = numero.replace(" ", "");
			String oldNumero = request.getParameter("numero");
			int error = 0;
			ArrayList<String> numeri = user.getNumeriTel();
			if(numeri.contains(numero)) {
				error = 2;
				request.setAttribute("errore-update-number", error);
				dispatcher.forward(request, response);
				return;
			}
			NumeroModelDS modelN = new NumeroModelDS(ds, user.getEmail());
			request.setAttribute("errore-update-number", error);
			try {
				Validazione.checkNumero(numero);
			}catch(Exception e) {
				error = 1;
				request.setAttribute("errore-update-number", error);
				dispatcher.forward(request, response);
				return;
			}
			for(String number : numeri) {
				if(number.equals(oldNumero)) {
					numeri.remove(number);
					numeri.add(numero);
					break;
				}
			}
			try {
				modelN.doUpdate(oldNumero, numero);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			user.setNumeriTel(numeri);
			request.getSession().setAttribute("user", user);
		}
		
		response.sendRedirect(response.encodeURL("./informazioniutente.jsp"));
	}
	
	private int findID(String email) {
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		IndirizzoModelDS model = new IndirizzoModelDS(ds, email);
		try {
		ArrayList<Indirizzo> all = model.doRetrieveAll(null);
		int id = all.size() + 1;
		return id;
		}
		catch(SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

}
