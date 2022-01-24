package catalogoManagement;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public class Deposito implements Serializable{

	private static final long serialVersionUID = 1L;
	private int ID;
	private String luogo;
	private HashMap<Prodotto, Integer> disponibilita;
	
	public Deposito() {
		disponibilita = new HashMap<Prodotto, Integer>();
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
	public HashMap<Prodotto, Integer> getDisponibilita() {
		return (HashMap<Prodotto, Integer>) disponibilita.clone();
	}

	/**
	 * @param disponibilita la disponibilità da settare
	 */
	public void setDisponibilita(HashMap<Prodotto, Integer> disponibilita) {
		this.disponibilita = (HashMap<Prodotto, Integer>) disponibilita.clone();
	}
	
	/**
	 * Aggiorna l'hashmap aggiungendo la coppia chiave-valore se non era già presente la chiave al suo interno,
	 *  aggiornando il valore della chiave se era già presente
	 * @param prod la chiave da aggiornare
	 * @param quantita il valore della chiave da aggiornare
	 * @return l'hashmap aggiornata
	 */
	public HashMap<Prodotto, Integer> updateDisponibilita(Prodotto prod, int quantita){
		if(disponibilita.containsKey(prod))
			disponibilita.replace(prod, quantita);
		else
			disponibilita.put(prod, quantita);
		
		return (HashMap<Prodotto, Integer>) disponibilita.clone();
	}
	
	public boolean equals(Object obj) {
		if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Deposito o = (Deposito) obj;
        return ID == o.ID && Objects.equals(luogo, o.luogo) && Objects.equals(disponibilita, o.disponibilita);
	}
	
}
