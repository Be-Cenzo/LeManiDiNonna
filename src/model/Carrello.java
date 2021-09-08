package model;

import java.io.Serializable;
import java.util.HashMap;

public class Carrello implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private HashMap<Integer, Prodotto> prodotti;
	
	public Carrello() {
		prodotti = new HashMap<Integer, Prodotto>();
	}
	
	public void addProdotto(int prodID, Prodotto prodotto){
		if(prodotti.containsKey(prodID)) {
			prodotti.get(prodID).addQuantità(prodotto.getQuantità());
		}
		else {
			prodotti.put(prodID, prodotto);
		}
	}
	
	public Prodotto removeProdotto(Integer prod) {
		return prodotti.remove(prod);
	}
	
	public HashMap<Integer, Prodotto> getProdotti(){
		return prodotti;
	}
	
	public void deleteList() {
		prodotti = new HashMap<Integer, Prodotto>();
	}
	
	public boolean isEmpty() {
		return prodotti == null ? true : (prodotti.size()>0 ? false : true);
	}
	
}
