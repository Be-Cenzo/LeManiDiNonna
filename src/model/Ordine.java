package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

public class Ordine {
	
	private int ID;
	private Date data;
	private float prezzo;
	private float costoSped;
	private String note;
	private String stato;
	private String email;
	private int indirizzo;
	private String corriere;
	private ArrayList<Prodotto> prodotti;
	
	public Ordine() {
		
	}
	
	public Ordine(int ID, Date data, float prezzo, float costoSped, String note, String stato, String email, int indirizzo, String corriere) {
		this.ID = ID;
		this.data = data;
		this.prezzo = prezzo;
		this.costoSped = costoSped;
		this.note = note;
		this.stato = stato;
		this.email = email;
		this.indirizzo = indirizzo;
		this.corriere = corriere;
		prodotti = new ArrayList<Prodotto>();
	}
	
	/**
	 * @return l'ID
	 */
	public int getID() {
		return ID;
	}
	
	/**
	 * @param ID l'ID da settare
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * @return la data dell'ordine
	 */
	public Date getData() {
		return data;
	}

	/**
	 * @param data la data da settare
	 */
	public void setData(Date data) {
		this.data = data;
	}

	/**
	 * @return il prezzo dei prodotti
	 */
	public float getPrezzo() {
		return prezzo;
	}

	/**
	 * @param prezzo il prezzo da settare
	 */
	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}

	/**
	 * @return il costo di spedizione
	 */
	public float getCostoSped() {
		return costoSped;
	}

	/**
	 * @param costoSped il costo di spedizione da settare
	 */
	public void setCostoSped(float costoSped) {
		this.costoSped = costoSped;
	}

	/**
	 * @return le note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note le note da settare
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return lo stato dell'ordine
	 */
	public String getStato() {
		return stato;
	}

	/**
	 * @param stato lo stato da settare
	 */
	public void setStato(String stato) {
		this.stato = stato;
	}

	/**
	 * @return l'email
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
	 * @return l'indirizzo
	 */
	public int getIndirizzo() {
		return indirizzo;
	}

	/**
	 * @param indirizzo l'indirizzo da settare
	 */
	public void setIndirizzo(int indirizzo) {
		this.indirizzo = indirizzo;
	}

	/**
	 * @return il corriere
	 */
	public String getCorriere() {
		return corriere;
	}

	/**
	 * @param corriere il corriere da settare
	 */
	public void setCorriere(String corriere) {
		this.corriere = corriere;
	}
	
	/**
	 * @return la quantità di prodotti nell'ordine
	 */
	public ArrayList<Prodotto> getProdotti() {
		return prodotti;
	}

	/**
	 * @param quantità la quantità di prodotti da settare
	 */
	public void setProdotti(ArrayList<Prodotto> prodotti) {
		this.prodotti = prodotti;
	}

}
