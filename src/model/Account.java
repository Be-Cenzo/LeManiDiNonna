package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable{

	private static final long serialVersionUID = 1L;
	private String email;
	private String nome;
	private String cognome;
	private String dataNascita;
	private String password;
	private String nomeIG;
	private ArrayList<String> numeriTel = new ArrayList<String>();
	private ArrayList<Indirizzo> indirizzi = new ArrayList<Indirizzo>();
	
	public Account() {
		
	}
	
	public Account(String email, String nome, String cognome, String dataNascita, String password, String nomeIG, String numeroTel, Indirizzo indirizzo) {
		this.email = email;
		this.nome = nome;		
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.password = password;
		this.nomeIG = nomeIG;
		this.numeriTel = new ArrayList<String>();
		this.numeriTel.add(numeroTel);
		this.indirizzi = new ArrayList<Indirizzo>();
		this.indirizzi.add(indirizzo);
	}
	
	public Account(String email, String nome, String cognome, String dataNascita, String password, String nomeIG, ArrayList<String> numeriTel, ArrayList<Indirizzo> indirizzi) {
		this.email = email;
		this.nome = nome;		
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.password = password;
		this.nomeIG = nomeIG;
		this.numeriTel = (ArrayList<String>)numeriTel.clone();
		this.indirizzi = (ArrayList<Indirizzo>)indirizzi.clone();
	}
	
	/**
	 * @return l' email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @param email l'email da settare
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return il nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome il nome da settare
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return il cognome
	 */
	public String getCognome() {
		return cognome;
	}

	/**
	 * @param cognome il cognome da settare
	 */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	/**
	 * @return la data di nascita
	 */
	public String getDataNascita() {
		return dataNascita;
	}

	/**
	 * @param dataNascita la data di nascita da settare
	 */
	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}

	/**
	 * @return la password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password la password da settare
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * @return il nome Instagram
	 */
	public String getNomeIG() {
		return nomeIG;
	}

	/**
	 * @param nomeIG il nome Instagram da settare
	 */
	public void setNomeIG(String nomeIG) {
		this.nomeIG = nomeIG;
	}

	/**
	 * @return i numeri di telefono
	 */
	public ArrayList<String> getNumeriTel() {
		return (ArrayList<String>)numeriTel.clone();
	}

	/**
	 * @param numeroTel il numero di telefono da aggiungere
	 */
	public void addNumeroTel(String numeroTel) {
		this.numeriTel.add(numeroTel);
	}
	
	/**
	 * @return gli indirizzi
	 */
	public ArrayList<Indirizzo> getIndirizzi(){
		return indirizzi;
	}
	
	public void setIndirizzi(ArrayList<Indirizzo> indirizzi) {
		this.indirizzi = indirizzi;
	}
	
	/**
	 * @param indirizzo l'indirizzo da aggiungere
	 */
	public void addIndirizzo(Indirizzo indirizzo) {
		indirizzi.add(indirizzo);
	}
}
