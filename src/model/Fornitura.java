package model;

import java.sql.Date;

public class Fornitura {

	private int ID;
	private String fornitore;
	private Date data;
	private int quantità;
	private int prodotto;
	private float prezzo;
	
	public Fornitura() {
		
	}
	
	public Fornitura(int ID, String fornitore, Date data, int quantità, int prodotto, float prezzo) {
		this.ID = ID;
		this.fornitore = fornitore;
		this.data = (Date) data.clone();
		this.quantità = quantità;
		this.prodotto = prodotto;
		this.prezzo = prezzo;
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
	 * @return il nome del fornitore
	 */
	public String getFornitore() {
		return fornitore;
	}
	
	/**
	 * @param fornitore il nome del fornitore da settare
	 */
	public void setFornitore(String fornitore) {
		this.fornitore = fornitore;
	}

	/**
	 * @return la data
	 */
	public Date getData() {
		return (Date) data.clone();
	}

	/**
	 * @param data la data da settare
	 */
	public void setData(Date data) {
		this.data = (Date) data.clone();
	}

	/**
	 * @return la quantità della fornitura
	 */
	public int getQuantità() {
		return quantità;
	}

	/**
	 * @param quantità la quantità della fornitura da settare
	 */
	public void setQuantità(int quantità) {
		this.quantità = quantità;
	}

	/**
	 * @return il codice del prodotto
	 */
	public int getProdotto() {
		return prodotto;
	}

	/**
	 * @param prodotto il codice del prodotto da settare
	 */
	public void setProdotto(int prodotto) {
		this.prodotto = prodotto;
	}

	/**
	 * @return il prezzo
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
}
