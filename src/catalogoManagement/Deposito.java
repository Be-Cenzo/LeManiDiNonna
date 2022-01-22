package catalogoManagement;

import java.io.Serializable;
import java.util.HashMap;

public class Deposito implements Serializable{

	private static final long serialVersionUID = 1L;
	private int ID;
	private String luogo;
	private HashMap<Prodotto, Integer> disponibilit�;
	
	public Deposito() {
		disponibilit� = new HashMap<Prodotto, Integer>();
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
	 * @return la disponibilit� di un prodotto
	 */
	public HashMap<Prodotto, Integer> getDisponibilit�() {
		return (HashMap<Prodotto, Integer>) disponibilit�.clone();
	}

	/**
	 * @param disponibilit� la disponibilit� da settare
	 */
	public void setDisponibilit�(HashMap<Prodotto, Integer> disponibilit�) {
		this.disponibilit� = (HashMap<Prodotto, Integer>) disponibilit�.clone();
	}
	
	/**
	 * Aggiorna l'hashmap aggiungendo la coppia chiave-valore se non era gi� presente la chiave al suo interno,
	 *  aggiornando il valore della chiave se era gi� presente
	 * @param prod la chiave da aggiornare
	 * @param quantit� il valore della chiave da aggiornare
	 * @return l'hashmap aggiornata
	 */
	public HashMap<Prodotto, Integer> updateDisponibilit�(Prodotto prod, int quantit�){
		if(disponibilit�.containsKey(prod))
			disponibilit�.replace(prod, quantit�);
		else
			disponibilit�.put(prod, quantit�);
		
		return (HashMap<Prodotto, Integer>) disponibilit�.clone();
	}
	
}
