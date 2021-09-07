package model;

import java.io.Serializable;
import java.util.HashMap;

public class Deposito implements Serializable{

	private static final long serialVersionUID = 1L;
	private int ID;
	private String luogo;
	private HashMap<Prodotto, Integer> disponibilitą;
	
	public Deposito() {
		disponibilitą = new HashMap<Prodotto, Integer>();
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
	 * @return il luogo
	 */
	public String getLuogo() {
		return luogo;
	}

	/**
	 * @param luogo il luogo da settare
	 */
	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}

	/**
	 * @return la disponibilitą di un prodotto
	 */
	public HashMap<Prodotto, Integer> getDisponibilitą() {
		return (HashMap<Prodotto, Integer>) disponibilitą.clone();
	}

	/**
	 * @param disponibilitą la disponibilitą da settare
	 */
	public void setDisponibilitą(HashMap<Prodotto, Integer> disponibilitą) {
		this.disponibilitą = (HashMap<Prodotto, Integer>) disponibilitą.clone();
	}
	
	/**
	 * Aggiorna l'hashmap aggiungendo la coppia chiave-valore se non era gią presente la chiave al suo interno,
	 *  aggiornando il valore della chiave se era gią presente
	 * @param prod la chiave da aggiornare
	 * @param quantitą il valore della chiave da aggiornare
	 * @return l'hashmap aggiornata
	 */
	public HashMap<Prodotto, Integer> updateDisponibilitą(Prodotto prod, int quantitą){
		if(disponibilitą.containsKey(prod))
			disponibilitą.replace(prod, quantitą);
		else
			disponibilitą.put(prod, quantitą);
		
		return (HashMap<Prodotto, Integer>) disponibilitą.clone();
	}
	
}
