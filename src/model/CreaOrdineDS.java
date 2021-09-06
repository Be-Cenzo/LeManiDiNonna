package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.DataSource;

public class CreaOrdineDS {
	
	private DataSource ds;
	
	public CreaOrdineDS(DataSource ds) {
		this.ds = ds;
	}
	
	public int newOrdine(String email, String note, int indirizzo, String corriere, HashMap<Integer, Prodotto> prodotti, float totale) throws SQLException {
		Connection con = null;
        PreparedStatement st = null;
        ResultSet ris = null;
        int res, id = 0, dispo = 0;
        float costoSped = 0;
        System.out.println(email);

        try{
            con = ds.getConnection();
            con.setAutoCommit(false);

            st = con.prepareStatement("SELECT costoSped FROM Corriere WHERE nome = ?");
            st.setString(1, corriere);
            ris = st.executeQuery();

            while(ris.next()){
                costoSped = ris.getFloat("costoSped");
            }

            st = con.prepareStatement("INSERT INTO Ordine(data, prezzo, costoSped, stato, note, email, indirizzo, corriere)" +
                                        "VALUES (current_timestamp(), ?, ?, 'Confermato', ?, ?, ?, ?);");
            st.setFloat(1, totale);
            st.setFloat(2, costoSped);
            st.setString(3, note);
            st.setString(4, email);
            st.setInt(5, indirizzo);
            st.setString(6, corriere);

            res = st.executeUpdate();
            if (res > 0) {
                st = con.prepareStatement("SELECT MAX(ID) AS Max FROM Ordine WHERE email = ?;");
                st.setString(1, email);
                ris = st.executeQuery();
                while(ris.next()){
                    id = ris.getInt("Max");
                }
                
                for(int prodID : prodotti.keySet()){
        			Prodotto prodotto = prodotti.get(prodID);
                	int quantita = prodotto.getQuantità();
	                st = con.prepareStatement("SELECT SUM(disponibilità) AS quantità FROM Conservato WHERE prodotto = ? AND taglia = ?;");
	                st.setInt(1, prodotto.getCodice());
	                st.setString(2, prodotto.getTaglia());
	                ris = st.executeQuery();
	
	                while(ris.next()){
	                    dispo = ris.getInt("quantità");
	                }
	
	                if(dispo>=quantita) {
	
	                    st = con.prepareStatement("INSERT INTO Relato VALUES (?, ?, ? ,?);");
	                    st.setInt(1, prodotto.getCodice());
	                    st.setString(2, prodotto.getTaglia());
	                    st.setInt(3, id);
	                    st.setInt(4, quantita);
	                    res = st.executeUpdate();
	
	                    if (res > 0) {
	
	                        while(quantita > 0){
	                            int deposito = 1;
	                            int quantitaDep = 0;
	                            st = con.prepareStatement("SELECT MIN(deposito) as dep, disponibilità FROM Conservato WHERE prodotto = ? AND taglia = ? AND disponibilità > 0;");
	                            st.setInt(1, prodotto.getCodice());
                                st.setString(2, prodotto.getTaglia());
	                            ris = st.executeQuery();
	                            while(ris.next()) {
	                                deposito = ris.getInt("dep");
	                                quantitaDep = ris.getInt("disponibilità");
	                            }
	
	                            if(quantita > quantitaDep){
	                                st = con.prepareStatement("UPDATE Conservato SET disponibilità = 0 WHERE deposito = ? AND prodotto = ? AND taglia = ?;");
	                                st.setInt(1, deposito);
	                                st.setInt(2, prodotto.getCodice());
	                                st.setString(3, prodotto.getTaglia());
	                                res = st.executeUpdate();
	                                if(res <= 0) {
	                                    con.rollback();
	                                    System.out.println("Errore nell'update del deposito. 1");
	                                    break;
	                                }
	                                quantita-=quantitaDep;
	                            }
	                            else{
	                                st = con.prepareStatement("UPDATE Conservato SET disponibilità = ? WHERE deposito = ? AND prodotto = ? AND taglia = ?;");
	                                int rest = quantitaDep - prodotto.getQuantità();
	                                st.setInt(1, rest);
	                                st.setInt(2, deposito);
	                                st.setInt(3, prodotto.getCodice());
	                                st.setString(4, prodotto.getTaglia());
	                                res = st.executeUpdate();
	                                if(res <= 0) {
	                                    con.rollback();
	                                    System.out.println("Errore nell'update del deposito. 2");
	                                    break;
	                                }
	                                quantita = 0;
	                            }
	                            System.out.println("Update effettuato");
	                            con.commit();
	                        }
	                    } else {
	                        con.rollback();
	                        System.out.println("Impossibile effettuare l'update");
	                    }
	                }
	                else{
	                    con.rollback();
	                    System.out.println("Non c'è abbastanza disponibilità.");
	                }
                }

            } else {
                System.out.println("Impossibile effettuare l'update");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (ris != null)
                    ris.close();
                if (st != null)
                    st.close();
                if (con != null)
                	con.close();
            }catch(SQLException s){
                s.printStackTrace();
            }
        }
        return 1;
	}
	
	public ArrayList<Integer> checkDisponibilità(HashMap<Integer, Prodotto> prodotti) {
		Connection con = null;
        PreparedStatement st = null;
        ResultSet ris = null;
        int res, id = 0, dispo = 0;
        ArrayList<Integer> prodottiND = new ArrayList<Integer>();

        try{
            con = ds.getConnection();

				for(int prodID : prodotti.keySet()){
					Prodotto prodotto = prodotti.get(prodID);
		        	int quantita = prodotto.getQuantità();
		            st = con.prepareStatement("SELECT SUM(disponibilità) AS quantità FROM Conservato WHERE prodotto = ? AND taglia = ?;");
		            st.setInt(1, prodotto.getCodice());
		            st.setString(2, prodotto.getTaglia());
		            ris = st.executeQuery();
		
		            while(ris.next()){
		                dispo = ris.getInt("quantità");
		            }
		
		            if(dispo<quantita) {
		                prodottiND.add(prodotto.getCodice());
		            	System.out.println("Non c'è abbastanza disponibilità.");
		            }
		 }

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (ris != null)
                    ris.close();
                if (st != null)
                    st.close();
                if (con != null)
                	con.close();
            }catch(SQLException s){
                s.printStackTrace();
            }
        }
        return prodottiND;
	}
	
}
