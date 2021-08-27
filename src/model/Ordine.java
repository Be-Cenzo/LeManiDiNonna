package model;

import java.sql.Date;
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
	private HashMap<Prodotto, Integer> quantit�;
	
	public Ordine() {
		
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
	 * @return la quantit� di prodotti nell'ordine
	 */
	public HashMap<Prodotto, Integer> getDisponibilit�() {
		return (HashMap<Prodotto, Integer>) quantit�.clone();
	}

	/**
	 * @param quantit� la quantit� di prodotti da settare
	 */
	public void setDisponibilit�(HashMap<Prodotto, Integer> quantit�) {
		this.quantit� = (HashMap<Prodotto, Integer>) quantit�.clone();
	}
	
	/**
	 * Aggiorna l'hashmap aggiungendo la coppia chiave-valore se non era gi� presente la chiave al suo interno,
	 *  aggiornando il valore della chiave se era gi� presente
	 * @param prod la chiave da aggiornare
	 * @param quantit� il valore della chiave da aggiornare
	 * @return l'hashmap aggiornata
	 */
	public HashMap<Prodotto, Integer> updateDisponibilit�(Prodotto prod, int quantit�){
		if(this.quantit�.containsKey(prod))
			this.quantit�.replace(prod, quantit�);
		else
			this.quantit�.put(prod, quantit�);
		
		return (HashMap<Prodotto, Integer>) this.quantit�.clone();
	}

}
