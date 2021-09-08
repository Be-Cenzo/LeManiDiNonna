package utility;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import model.Account;
import model.AccountModelDS;

public class Validazione {
	
	public static Account checkEmail(String email, DataSource ds) throws Exception {
		
		String format = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+";
		Pattern reg = Pattern.compile(format);
		Matcher matcher = reg.matcher(email);
		if(!matcher.matches()) {
			throw new Exception("Invalid Email");
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
			throw new Exception("Invalid Email");

	}
	
	public static String checkNumero(String phone) throws Exception {

		String format = "((00|\\+)39[\\. ]??)??3\\d{2}[\\. ]??\\d{6,7}";
		Pattern reg = Pattern.compile(format);
		Matcher matcher = reg.matcher(phone);
		if(!matcher.matches()) {
			throw new Exception("Invalid phone number");
		}
		return phone;
		
	}
	
	public static String checkPassword(String psw, String pswr) throws Exception {

		String format = "[a-zA-Z0-9]{6,}";
		Pattern reg = Pattern.compile(format);
		Matcher matcher = reg.matcher(psw);
		if(!matcher.matches()) {
			System.out.println("mannagg a chella puttan e merd " + psw);
			throw new Exception("Invalid password");
		}
		else if(!psw.equals(pswr))
			throw new Exception("Passwords don't match");
		else
			return psw;
	}
	
	public static String checkStringaVuota(String str) throws Exception{
		String format = "[ ]*";
		Pattern reg = Pattern.compile(format);
		Matcher matcher = reg.matcher(str);
		if(str == null || str.equals("") || matcher.matches()) 
			throw new Exception("Empty string");
		else
			return str;
	}
	
}
