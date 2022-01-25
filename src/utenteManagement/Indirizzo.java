package utenteManagement;

import java.io.Serializable;
import java.util.Objects;

public class Indirizzo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int ID;
	private String email;
	private String provincia;
	private String comune;
	private String via;
	private int civico;
	private String CAP;
	
	public Indirizzo(int ID, String email, String provincia, String comune, String via, int civico, String CAP) {
		this.ID = ID;
		this.email = email;
		this.provincia = provincia;
		this.comune = comune;
		this.via = via;
		this.civico = civico;
		this.CAP = CAP;
	}
	
	/**
	 * @return l'ID
	 */
	public int getID() {
		return ID;
	}
	
	/**
	 * @return l'email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return la provincia
	 */
	public String getProvincia() {
		return provincia;
	}
	
	/**
	 * @return il comune
	 */
	public String getComune() {
		return comune;
	}
	
	/**
	 * @return la via
	 */
	public String getVia() {
		return via;
	}

	/**
	 * @return il civico
	 */
	public int getCivico() {
		return civico;
	}
	
	/**
	 * @return il CAP
	 */
	public String getCAP() {
		return CAP;
	}
	
	public boolean equals(Object obj) {
		if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Indirizzo o = (Indirizzo) obj;
        return ID == o.ID && civico == o.civico && Objects.equals(CAP, o.CAP) && Objects.equals(email, o.email) && Objects.equals(provincia, o.provincia) && Objects.equals(via, o.via) && Objects.equals(comune, o.comune);
	}

}
