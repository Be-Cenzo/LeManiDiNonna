package catalogoManagement;

import java.io.Serializable;
import java.util.HashMap;

public class Deposito implements Serializable{

	private static final long serialVersionUID = 1L;
	private int ID;
	private String luogo;
	private HashMap<Prodotto, Integer> disponibilità;
	
	public Deposito() {
		disponibilità = new HashMap<Prodotto, Integer>();
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
	 * @return la disponibilità di un prodotto
	 */
	public HashMap<Prodotto, Integer> getDisponibilità() {
		return (HashMap<Prodotto, Integer>) disponibilità.clone();
	}

	/**
	 * @param disponibilità la disponibilità da settare
	 */
	public void setDisponibilità(HashMap<Prodotto, Integer> disponibilità) {
		this.disponibilità = (HashMap<Prodotto, Integer>) disponibilità.clone();
	}
	
	/**
	 * Aggiorna l'hashmap aggiungendo la coppia chiave-valore se non era già presente la chiave al suo interno,
	 *  aggiornando il valore della chiave se era già presente
	 * @param prod la chiave da aggiornare
	 * @param quantità il valore della chiave da aggiornare
	 * @return l'hashmap aggiornata
	 */
	public HashMap<Prodotto, Integer> updateDisponibilità(Prodotto prod, int quantità){
		if(disponibilità.containsKey(prod))
			disponibilità.replace(prod, quantità);
		else
			disponibilità.put(prod, quantità);
		
		return (HashMap<Prodotto, Integer>) disponibilità.clone();
	}
	
}
