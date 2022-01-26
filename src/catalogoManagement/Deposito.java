package catalogoManagement;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public class Deposito implements Serializable{

	private static final long serialVersionUID = 1L;
	private int ID;
	private String luogo;
	
	public Deposito() {
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

	
	public boolean equals(Object obj) {
		if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Deposito o = (Deposito) obj;
        return ID == o.ID && Objects.equals(luogo, o.luogo);
	}
	
}
