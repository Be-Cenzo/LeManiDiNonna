package checking;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import utenteManagement.Account;
import utenteManagement.AccountModelDS;

public class Validazione {
	
	public static Account checkEmail(String email, DataSource ds) throws CheckException {
		
		String format = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+";
		Pattern reg = Pattern.compile(format);
		Matcher matcher = reg.matcher(email);
		if(!matcher.matches()) {
			throw new CheckException("Invalid Email");
		}
		
		AccountModelDS model = new AccountModelDS(ds);
		Account acc = null;
		try {
		acc = model.doRetrieveByKey(email);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		if(acc.getEmail() == null)
			return acc;
		else
			throw new CheckException("Invalid Email");

	}
	
public static void checkEmailForUpdate(String email, DataSource ds) throws CheckException {
		
		String format = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+";
		Pattern reg = Pattern.compile(format);
		Matcher matcher = reg.matcher(email);
		if(!matcher.matches()) {
			throw new CheckException("Invalid Email");
		}
	}
	
	public static String checkNumero(String phone) throws CheckException {

		String format = "((00|\\+)39[\\. ]??)??3\\d{2}[\\. ]??\\d{6,7}";
		Pattern reg = Pattern.compile(format);
		Matcher matcher = reg.matcher(phone);
		if(!matcher.matches()) {
			throw new CheckException("Invalid phone number");
		}
		return phone;
		
	}
	
	public static String checkPassword(String psw, String pswr) throws CheckException {

		String format = "[a-zA-Z0-9]{6,}";
		Pattern reg = Pattern.compile(format);
		Matcher matcher = reg.matcher(psw);
		if(!matcher.matches()) {
			throw new CheckException("Invalid password");
		}
		else if(!psw.equals(pswr))
			throw new CheckException("Passwords don't match");
		else
			return psw;
	}
	
	public static String checkStringaVuota(String str) throws CheckException{
		String format = "[ ]*";
		Pattern reg = Pattern.compile(format);
		Matcher matcher = reg.matcher(str);
		if(str == null || str.equals("") || matcher.matches()) 
			throw new CheckException("Empty string");
		else
			return str;
	}
	
	public static String checkTipo(String str) throws CheckException {
		if(str.equals("t-shirt") || str.equals("cappello") || str.equals("borsello") || str.equals("grembiule") || str.equals("shopper") || str.equals("felpa"))
			return str;
		else
			throw new CheckException("Invalid tipo");
	}
	
	public static String checkTaglia(String str) throws CheckException{
		if(str.equals("S") || str.equals("M") || str.equals("L"))
			return str;
		else
			throw new CheckException("Invalid taglia");
	}
	
	public static String checkData(String data) throws CheckException {

		String format = "^\\d{4}[\\-\\/\\s]?((((0[13578])|(1[02]))[\\-\\/\\s]?(([0-2][0-9])|(3[01])))|(((0[469])|(11))[\\-\\/\\s]?(([0-2][0-9])|(30)))|(02[\\-\\/\\s]?[0-2][0-9]))$";
		Pattern reg = Pattern.compile(format);
		Matcher matcher = reg.matcher(data);
		if(!matcher.matches()) {
			throw new CheckException("Invalid date format");
		}
		return data;
		
	}
	
}
